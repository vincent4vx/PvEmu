/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightMap {
    final private GameMap map;
    final private Map<Short, Fighter> fighters = new HashMap<>();

    FightMap(GameMap map) {
        this.map = map;
    }
    
    public void addFighter(Fighter fighter){
        fighters.put(fighter.getCellId(), fighter);
    }
    
    /**
     * Get a free cell into a cell list
     * @warning don't test if all cells of list are taken
     * @param cellList the list of cells
     * @return the free cell
     */
    public short getFreeRandomCell(List<Short> cellList){
        short cell = 0;
        
        do{
            cell = cellList.get(Utils.rand(cellList.size()));
        }while(fighters.containsKey(cell));
        
        return cell;
    }

    public GameMap getMap() {
        return map;
    }
    
    public boolean isValidPlace(Fighter fighter, short cell){
        if(fighters.containsKey(cell))
            return false;
        
        if(map.getCellById(cell) == null)
            return false;
        
        if(!map.getCellById(cell).isWalkable())
            return false;
        
        return Utils.contains(fighter.getTeam().getPlaces(), cell);
    }
    
    public void moveFighter(Fighter fighter, short dest){
        fighters.remove(fighter.getCellId());
        fighter.setCell(dest);
        fighters.put(dest, fighter);
    }
    
    public boolean isFreeCell(short cell){
        return !fighters.containsKey(cell) && map.getCellById(cell).isWalkable();
    }
}
