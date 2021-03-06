/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item.itemset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.itemlist.Inventory;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.common.Loggin;

/**
 * handle item sets
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class ItemSetHandler {
    final private Inventory inventory;
    
    final private Map<Integer, Set<GameItem>> itemsByItemSet = new HashMap<>();
    final private Map<Integer, Stats> statsByItemSet = new HashMap<>();

    public ItemSetHandler(Inventory inventory) {
        this.inventory = inventory;
    }
    
    public void loadItemSets(){
        for(GameItem item : inventory.getItems()){
            addItemInItemSet(item);
        }
        
        for(int itemset : itemsByItemSet.keySet()){
            statsByItemSet.put(itemset, generateStatsByItemSet(itemset));
        }
    }
    
    /**
     * Test if the item is already managed
     * @param item
     * @return 
     */
    public boolean exists(GameItem item){
        
        for(Set<GameItem> items : itemsByItemSet.values()){
            for(GameItem item2 : items){
                if(item2 == item)
                    return true;
            }
        }
        
        return false;
    }
    
    /**
     * Update item sets
     * @return 
     */
    public Set<Integer> updateItemSets(){
        Set<Integer> updated = new HashSet<>();
        
        for(GameItem item : inventory.getItems()){
            if(addItemInItemSet(item))
                updated.add(item.getTemplate().itemset);
        }
        
        for(Set<GameItem> items : itemsByItemSet.values()){
            if(items.isEmpty()){
                continue;
            }
            Set<GameItem> toRemove = new HashSet<>();
            for(GameItem item : items){
                if(!(inventory.isItemExists(item) && ItemPosition.getByPosID(item.getEntry().position).isWearPlace())){
                    updated.add(item.getTemplate().itemset);
                    toRemove.add(item);
                }
            }
            items.removeAll(toRemove);
        }
        
        for(int itemset : updated){
            statsByItemSet.put(itemset, generateStatsByItemSet(itemset));
        }
        
        return updated;
    }
    
    /**
     * try to add an item into corresponding item set
     * @param item item to handle
     * @return true if the itemset is found
     */
    private boolean addItemInItemSet(GameItem item){
        if(!ItemPosition.getByPosID(item.getEntry().position).isWearPlace())
            return false;
        
        int set = item.getTemplate().itemset;
        
        if(set == -1)
            return false;
        
        Set<GameItem> items = itemsByItemSet.get(set);
        
        if(items == null){
            items = new HashSet<>();
            itemsByItemSet.put(set, items);
        }
        
        return items.add(item);
    }
    
    private Stats generateStatsByItemSet(int itemset){
        ItemSetData isd = ItemSetFactory.getById(itemset);
        
        if(isd == null){
            Loggin.debug("Not itemset found %d", itemset);
            return new Stats();
        }
        
        Set items = itemsByItemSet.get(itemset);
        
        if(items == null){
            Loggin.debug("There is no items stored !");
            return new Stats();
        }
        
        Loggin.debug("Generating stats for itemset %d with %d items", itemset, items.size());
        
        return isd.getStatsByItemsNumber(items.size());
    }
    
    /**
     * Get all weared items of an itemset
     * @param itemset
     * @return 
     */
    public Set<GameItem> getItemsInItemSet(int itemset){
        return itemsByItemSet.get(itemset);
    }
    
    /**
     * Get the stats for one itemset
     * @param itemset
     * @return 
     */
    public Stats getStatsByItemSet(int itemset){
        return statsByItemSet.get(itemset);
    }
    
    /**
     * Get total stats of the itemsets
     * @return 
     */
    public Stats getAllStats(){
        Stats stats = new Stats();
        
        for(Stats s : statsByItemSet.values()){
            stats.addAll(s);
        }
        
        return stats;
    }
    
    /**
     * Get list of current itemsets
     * @return 
     */
    public Set<Integer> getItemSets(){
        return itemsByItemSet.keySet();
    }
}
