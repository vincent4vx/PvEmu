/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
    
    /**
     * Test if the place is valid to go on
     * @param fighter
     * @param cell
     * @return 
     */
    public boolean isValidPlace(Fighter fighter, short cell){
        if(fighters.containsKey(cell))
            return false;
        
        if(map.getCellById(cell) == null)
            return false;
        
        if(!map.getCellById(cell).isWalkable())
            return false;
        
        return Utils.contains(fighter.getTeam().getPlaces(), cell);
    }
    
    /**
     * Change the place of a fighter
     * @param fighter
     * @param dest 
     */
    public void moveFighter(Fighter fighter, short dest){
        fighters.remove(fighter.getCellId());
        fighter.setCell(dest);
        fighters.put(dest, fighter);
    }
    
    /**
     * Test if the cell is free (can move on)
     * @param cell
     * @return 
     */
    public boolean isFreeCell(short cell){
        return !fighters.containsKey(cell)
                && map.getCellById(cell) != null
                && map.getCellById(cell).isWalkable();
    }
    
    /**
     * Get a fighter by its position
     * @param position
     * @return 
     */
    public Fighter getFighter(short position){
        return fighters.get(position);
    }
    
    /**
     * Get a fighter list on cell list
     * @param cells the cell list (area)
     * @return 
     */
    public Collection<Fighter> getFightersByCells(Collection<Short> cells){
        Collection<Fighter> list = new HashSet<>(cells.size());
        for(short cell : cells){
            Fighter fighter = fighters.get(cell);
            
            if(fighter != null)
                list.add(fighter);
        }
        return list;
    }
    
    /**
     * remove a fighter from map
     * @param fighter 
     */
    public void removeFighter(Fighter fighter){
        fighters.remove(fighter.getCellId());
    }
}
