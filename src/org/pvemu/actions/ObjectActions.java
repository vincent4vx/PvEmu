/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.actions;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.inventory.Inventory;
import org.pvemu.game.objects.inventory.Inventory.MoveState;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ObjectActions {
    public void addItem(GameItem item, Player player){
        MoveState state = player.getInventory().addOrStackItem(item);
        
        if(state == MoveState.ERROR){
            Loggin.debug("Impossible d'ajouter l'item %d", item.getID());
            return;
        }
        
        itemStateChange(item, state, player.getSession());
        GameSendersRegistry.getPlayer().weightUsed(player, player.getSession());
    }
    
    public void deleteItem(GameItem item, int quantity, Player player){
        MoveState state = player.getInventory().delete(item, quantity);
        itemStateChange(item, state, player.getSession());
    }
    
    public void itemStateChange(GameItem item, MoveState state, IoSession session){
        if(state == MoveState.ADD){
            GameSendersRegistry.getObject().addItem(item, session);
        }else if(state == MoveState.STACK){
            GameSendersRegistry.getObject().quantityChange(item, session);
        }else if(state == MoveState.MOVE){
            GameSendersRegistry.getObject().moveItem(item, session);
        } else if(state == MoveState.DELETE){
            GameSendersRegistry.getObject().removeItem(item, session);
        }
    }
}
