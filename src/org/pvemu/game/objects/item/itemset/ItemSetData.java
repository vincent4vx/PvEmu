/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item.itemset;

import java.util.List;
import java.util.Set;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.models.ItemSet;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class ItemSetData {
    final private ItemSet model;
    final private Set<Integer> items;
    final private List<Stats> stats;

    public ItemSetData(ItemSet model, Set<Integer> items, List<Stats> stats) {
        this.model = model;
        this.items = items;
        this.stats = stats;
    }

    public ItemSet getModel() {
        return model;
    }

    public Set<Integer> getItems() {
        return items;
    }

    public List<Stats> getStats() {
        return stats;
    }
    
    /**
     * Get the stats with the current number of item of the same item set
     * @param num number of weared items
     * @return 
     */
    public Stats getStatsByItemsNumber(int num){
        num -= 2; //passing item count to index (bonus of itemset start with 2 items !)
        
        if(num < 0)
            return new Stats();
        
        if(num > stats.size() - 1) //if there are more item than stats
            num = stats.size() - 1; //get the last stats
        
        return stats.get(num);
    }
}
