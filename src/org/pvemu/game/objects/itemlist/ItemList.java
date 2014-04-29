/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.itemlist;

import java.util.Collection;
import java.util.List;
import org.pvemu.game.objects.item.GameItem;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface ItemList {
    /**
     * Get the item list type
     * @return 
     */
    public byte type();
    
    /**
     * Get the item list id
     * @return 
     */
    public int id();
    
    /**
     * Add an item to the item list
     * @param item 
     */
    public void addItem(GameItem item);
    
    /**
     * Test if can add item to the item list
     * @param item the item to test
     * @return true if can add
     */
    public boolean canAddItem(GameItem item);
    
    /**
     * Delete an item of item list
     * @param item the item to delete
     * @param quantity quantity to delete
     */
    public void delete(GameItem item, int quantity);
    
    /**
     * Get all items stored into the item list
     * @return 
     */
    public Collection<GameItem> getItems();
    
    /**
     * Get a stored item by his id
     * @param id  the id of the item
     * @return 
     */
    public GameItem getItemById(int id);
}
