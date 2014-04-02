/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.game.objects.GameNpc;
import org.pvemu.jelly.utils.Crypt;
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
        List<MapCell> cells = new ArrayList<>(model.mapData.length() / 10);

        for (short f = 0, i = 0; f < model.mapData.length(); f += 10, ++i) {
            String cellData = model.mapData.substring(f, f + 10);
            cells.add(parseCellData(id, i, cellData));
        }
        
        GameMap map = new GameMap(id, model, cells);
        
        for(MapNpcs MN : DAOFactory.mapNpcs().getByMapId(id)){
            map.addGMable(new GameNpc(MN, map.getNextGmId()));
        }
        
        model.mapData = null;

        for (Trigger T : DAOFactory.trigger().getByMapID(id)) {
            MapCell cell = map.getCellById(T.cellID);

            if (cell != null) {
                cell.addTrigger(T);
            }
        }
        
        return map;
    }
    
    static private MapCell parseCellData(short mapID, short cellID, String cellData){
        boolean walkable = ((Crypt.getHashIndex(cellData.charAt(2)) & 56) >> 3) != 0;
        boolean canSight = (Crypt.getHashIndex(cellData.charAt(0)) & 1) != 0;
        int layerObject2 = ((Crypt.getHashIndex(cellData.charAt(0)) & 2) << 12) + ((Crypt.getHashIndex(cellData.charAt(7)) & 1) << 12) + (Crypt.getHashIndex(cellData.charAt(8)) << 6) + Crypt.getHashIndex(cellData.charAt(9));
        boolean layerObject2Interactive = ((Crypt.getHashIndex(cellData.charAt(7)) & 2) >> 1) != 0;
        InteractiveObject obj = (layerObject2Interactive ? new InteractiveObject(layerObject2, cellID, mapID) : null);
        
        return new MapCell(cellID, mapID, walkable, canSight, obj);
    }
}
