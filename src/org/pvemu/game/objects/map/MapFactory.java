/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map;

import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import org.pvemu.game.objects.npc.GameNpc;
import org.pvemu.game.objects.map.interactiveobject.InteractiveObjectFactory;
import org.pvemu.game.objects.monster.MonsterFactory;
import org.pvemu.game.objects.npc.NpcFactory;
import org.pvemu.common.Shell;
import org.pvemu.common.utils.Crypt;
import org.pvemu.common.utils.Utils;
import org.pvemu.models.MapModel;
import org.pvemu.models.MapNpcs;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class MapFactory {
    final static private Map<Short, GameMap> mapsById = new ConcurrentHashMap<>(2000);
    
    static public GameMap getById(short mapID){
        if(!mapsById.containsKey(mapID)){
            MapModel model = DAOFactory.map().find(mapID);
            mapsById.put(mapID, getByModel(
                    model,
                    DAOFactory.mapNpcs().getByMapId(mapID)
            ));
        }
        
        return mapsById.get(mapID);
    }
    
    static private GameMap getByModel(MapModel model, List<MapNpcs> npcs){
        if(model == null)
            return null;
        
        short id = model.id;
        List<MapCell> cells;
        
        if(model.mapData == null || model.mapData.isEmpty()){
            cells = getCellsFromCellsString(model.cells, id);
        }else{
            cells = getCellsFromMapData(model.mapData, id);
        }
        
        String[] tmp = Utils.split(model.places, "|");
        
        List<Short>[] places = new List[tmp.length];
        
        for(int i = 0; i < tmp.length; ++i){
            places[i] = MapUtils.parseCellList(tmp[i].trim(), cells);
        }
        
        GameMap map = new GameMap(
                id, 
                model,
                Collections.unmodifiableList(cells),
                MonsterFactory.parseMonsterList(model.monsters),
                places
        );
        
        for(MapNpcs MN : npcs){
            GameNpc npc = NpcFactory.getNpcByMapNpc(MN, map.getNextGmId());
            if(npc != null)
                map.addGMable(npc);
        }
        
        //free memory
        model.mapData = null;
        model.cells = null;
        
        return map;
    }
    
    static private List<MapCell> getCellsFromMapData(String mapData, short mapID){
        List<MapCell> cells = new ArrayList<>(mapData.length() / 10);

        for (short f = 0, i = 0; f < mapData.length(); f += 10, ++i) {
            String cellData = mapData.substring(f, f + 10);
            
            cells.add(parseCellData(mapID, i, cellData));
        }
        
        return cells;
    }
    
    static private List<MapCell> getCellsFromCellsString(String strCells, short mapID){
        List<MapCell> cells = new ArrayList<>();
        
        for(String str : Utils.split(strCells, "|")){
            String[] data = Utils.split(str, ",");
            
            boolean walkable = true;
            boolean canSight = true;
            short id = -1;
            int IO = -1;
            
            try{
                id = Short.parseShort(data[0].trim());
                canSight = data[1].trim().equals("1");
                walkable = data[2].trim().equals("1");
                
                if(data.length > 3 && !data[3].trim().isEmpty()){
                    IO = Integer.parseInt(data[3].trim());
                }
            }catch(Exception e){}
            
            InteractiveObject obj = IO == -1 ? null : InteractiveObjectFactory.getInteractiveObject(IO, id, mapID);
            cells.add(new MapCell(id, mapID, walkable, canSight, obj));
        }
        
        return cells;
    }
    
    static private MapCell parseCellData(short mapID, short cellID, String cellData){
        boolean walkable = ((Crypt.getHashIndex(cellData.charAt(2)) & 56) >> 3) != 0;
        boolean canSight = (Crypt.getHashIndex(cellData.charAt(0)) & 1) != 0;
        int layerObject2 = ((Crypt.getHashIndex(cellData.charAt(0)) & 2) << 12) + ((Crypt.getHashIndex(cellData.charAt(7)) & 1) << 12) + (Crypt.getHashIndex(cellData.charAt(8)) << 6) + Crypt.getHashIndex(cellData.charAt(9));
        boolean layerObject2Interactive = ((Crypt.getHashIndex(cellData.charAt(7)) & 2) >> 1) != 0;
        InteractiveObject obj = (layerObject2Interactive ? InteractiveObjectFactory.getInteractiveObject(layerObject2, cellID, mapID) : null);
        
        return new MapCell(cellID, mapID, walkable, canSight, obj);
    }
    
    static public void preloadMaps(){
        Shell.print("Loading maps : ", Shell.GraphicRenditionEnum.YELLOW);
        List<MapModel> models = DAOFactory.map().getAll();
        Map<Short, List<MapNpcs>> npcs = DAOFactory.mapNpcs().getAll();
        
        /*for(MapModel model : models){
            List<MapNpcs> list = npcs.get(model.id);
            
            if(list == null)
                list = new ArrayList<>();
            
            mapsById.put(model.id, getByModel(model, list));
        }*/
        
        new ForkJoinPool().invoke(new MapLoader(models, 0, models.size(), npcs));
        
        Shell.println(models.size() + " maps loaded", Shell.GraphicRenditionEnum.GREEN);
    }
    
    static private class MapLoader extends RecursiveAction{
        final private static int MAX_MAP_PER_THREAD = 500;
        
        final private List<MapModel> models;
        final private int start;
        final private int end;
        final private Map<Short, List<MapNpcs>> npcs;

        public MapLoader(List<MapModel> models, int start, int end, Map<Short, List<MapNpcs>> npcs) {
            this.models = models;
            this.start = start;
            this.end = end;
            this.npcs = npcs;
        }

        @Override
        protected void compute() {
            int count = end - start;
            if(count < MAX_MAP_PER_THREAD){
                load();
                return;
            }
            
            int middle = count / 2;
            
            MapLoader loader1 = new MapLoader(models, start, start + middle, npcs);
            MapLoader loader2 = new MapLoader(models, start + middle, end, npcs);
            loader2.fork();
            loader1.compute();
            loader2.join();
        }
        
        private void load(){
            for(int i = start; i < end; ++i){
                MapModel model = models.get(i);
                List<MapNpcs> list = npcs.get(model.id);

                if(list == null)
                    list = new ArrayList<>();

                mapsById.put(model.id, getByModel(model, list));
            }
        }
        
    }
}
