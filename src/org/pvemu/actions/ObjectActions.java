/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.actions;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.itemlist.Inventory;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ObjectActions {
    public void addItem(GameItem item, Player player){
        player.getInventory().addOrStackItem(item);
        
        if(player.getInventory().commitStates(player.getSession()))
            GameSendersRegistry.getPlayer().weightUsed(player, player.getSession());
    }
    
    public void deleteItem(GameItem item, int quantity, Player player){
        player.getInventory().delete(item, quantity);
        
        if(player.getInventory().commitStates(player.getSession()))
            GameSendersRegistry.getPlayer().weightUsed(player, player.getSession());
    }
}
