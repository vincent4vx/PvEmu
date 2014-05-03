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
import org.pvemu.game.objects.GameNpc;
import org.pvemu.game.objects.map.interactiveobject.InteractiveObjectFactory;
import org.pvemu.game.objects.monster.MonsterFactory;
import org.pvemu.jelly.utils.Crypt;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.MapModel;
import org.pvemu.models.MapNpcs;
import org.pvemu.models.Trigger;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class MapFactory {
    final static private Map<Short, GameMap> mapsById = new HashMap<>(2000);
    
    static public GameMap getById(short mapID){
        if(!mapsById.containsKey(mapID)){
            MapModel model = DAOFactory.map().find(mapID);
            mapsById.put(mapID, getByModel(model));
        }
        
        return mapsById.get(mapID);
    }
    
    static public GameMap getByModel(MapModel model){
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
        
        for(MapNpcs MN : DAOFactory.mapNpcs().getByMapId(id)){
            map.addGMable(new GameNpc(MN, map.getNextGmId()));
        }
        
        //free memory
        model.mapData = null;
        model.cells = null;

        for (Trigger T : DAOFactory.trigger().getByMapID(id)) {
            MapCell cell = map.getCellById(T.cellID);

            if (cell != null) {
                cell.addTrigger(T);
            }
        }
        
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
}
