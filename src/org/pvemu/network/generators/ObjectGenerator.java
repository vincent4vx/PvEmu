/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import java.util.Map;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.inventory.Inventory;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.models.InventoryEntry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ObjectGenerator {
    
    public String generateInventoryEntry(InventoryEntry entry){
        return new StringBuilder(12 + entry.stats.length())
                .append(Integer.toHexString(entry.id))
                .append('~').append(Integer.toHexString(entry.item_id))
                .append('~').append(Integer.toHexString(entry.qu))
                .append('~').append(ItemPosition.DEFAULT_POSITION == entry.position ? "" : Integer.toHexString(entry.position))
                .append('~').append(entry.stats)
                .toString();
    }
    
    public String generateStats(Stats stats){
        StringBuilder s = new StringBuilder(stats.getAll().size() * 16);
        
        for(Map.Entry<Stats.Element, Short> curElem : stats.getAll()){
            int jet = curElem.getValue();
            if(jet == 0){
                continue;
            }
            int elemID = curElem.getKey().getId(jet < 0);
            s.append(Integer.toHexString(elemID)).append('#').append(Integer.toHexString(jet)).append("#0#0#").append("0d0+").append(jet).append(',');
        }
        
        return s.toString();
    }
    
    public String generateInventory(Inventory inventory){
        StringBuilder s = new StringBuilder(32 * inventory.getItems().size());
        
        for(GameItem item : inventory.getItems()){
            s.append(generateInventoryEntry(item.getEntry())).append(';');
        }
        
        return s.toString();
    }
    
    /*public String generateMoveItem(GameItem item){
        return new StringBuilder(8).append(item.getID()).append('|').append(item.getEntry().position).toString();
    }
    
    public String generateQuantityChange(GameItem item){
        return new StringBuilder(8).append(item.getID()).append('|').append(item.getEntry().qu).toString();
    }*/
     
    public String generateMoveItem(InventoryEntry entry){
        return new StringBuilder(8).append(entry.id).append('|').append(entry.position).toString();
    }
    
    public String generateQuantityChange(InventoryEntry entry){
        return new StringBuilder(8).append(entry.id).append('|').append(entry.qu).toString();
    }
}
