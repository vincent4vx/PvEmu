/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item;

import org.pvemu.game.objects.dep.Stats;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public abstract class GameItem {
    final protected Stats stats;
    final protected InventoryEntry entry;
    final protected ItemTemplate template;

    protected GameItem(Stats stats, InventoryEntry entry, ItemTemplate template) {
        this.stats = stats;
        this.entry = entry;
        this.template = template;
    }
    
    public int getID(){
        return entry.id;
    }

    public Stats getStats() {
        return stats;
    }

    public InventoryEntry getEntry() {
        return entry;
    }

    public ItemTemplate getTemplate() {
        return template;
    }
    
    /**
     * Test if the current is an accessorie (i.e. is visible)
     * @return true if ok
     */
    public boolean isAccessorie(){
        return false;
    }
    
    /**
     * Test if the current item can be weared, or not
     * @return true if ok, fase in other cases
     */
    abstract public boolean isWearable();
    
    /**
     * Test if the other item is the same (stats + pos + template id)
     * @param other the other item to test
     * @return true if are same, false in other cases
     */
    final public boolean isSameItem(GameItem other){
        if(other == null)
            return false;
        
        return other.template.id == template.id
                && other.stats.equals(stats)
                && other.entry.position == entry.position;
    }
    
    /**
     * Get the total number of pods
     * @return the number of used pods
     */
    final public int getPods(){
        return entry.qu * template.pods;
    }
}
