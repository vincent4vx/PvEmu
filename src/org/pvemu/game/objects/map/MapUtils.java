package org.pvemu.game.objects.map;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.jelly.utils.Pathfinding;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class MapUtils {
    /**
     * test if the destination is valid
     *
     * @param mapID
     * @param cellID
     * @return
     */
    public static boolean isValidDest(short mapID, short cellID) {
        GameMap map = MapFactory.getById(mapID);

        if (map == null) { //map inexistante
            return false;
        }
        
        MapCell cell = map.getCellById(cellID);

        return cell != null && cell.isWalkable();
    }
    
    /**
     * Get a random valid (i.e. walkable) cell in a map
     * @param map
     * @return 
     */
    static public MapCell getRandomValidCell(GameMap map){
        MapCell cell;
        int size = map.getCells().size();
        
        do{
            cell = map.getCellById((short)Utils.rand(size));
        }while(!cell.isWalkable());
        
        return cell;
    }
    
    static public List<Short> parseCellList(String cellList, GameMap map){
        List<Short> list = new ArrayList<>(cellList.length() / 2);
        
        for(int i = 0; i < cellList.length(); i += 2){
            short cell = Pathfinding.cellCode_To_ID(cellList.substring(i, i + 2));
            
            if(map.getCellById(cell) != null && map.getCellById(cell).isWalkable())
                list.add(cell);
        }
        
        return list;
    }
}
