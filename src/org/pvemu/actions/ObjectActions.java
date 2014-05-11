package org.pvemu.actions;

import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ObjectActions {
    /**
     * Add an item into the player's inventory
     * @param item item to add
     * @param player the target
     * @see org.pvemu.game.objects.itemlist.Inventory#addOrStackItem(org.pvemu.game.objects.item.GameItem) 
     */
    public void addItem(GameItem item, Player player){
        player.getInventory().addOrStackItem(item);
        
        if(player.getInventory().commitStates(player.getSession()))
            GameSendersRegistry.getPlayer().weightUsed(player, player.getSession());
    }
    
    /**
     * Delete one or more items from player's inventory
     * @param item the item to delete
     * @param quantity the quantity of this item to delete
     * @param player the target
     * @see org.pvemu.game.objects.itemlist.Inventory#delete(org.pvemu.game.objects.item.GameItem, int) 
     */
    public void deleteItem(GameItem item, int quantity, Player player){
        player.getInventory().delete(item, quantity);
        
        if(player.getInventory().commitStates(player.getSession()))
            GameSendersRegistry.getPlayer().weightUsed(player, player.getSession());
    }
}
