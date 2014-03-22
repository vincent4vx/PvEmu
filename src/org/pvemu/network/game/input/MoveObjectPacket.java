/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.inventory.entrystate.EntryState;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MoveObjectPacket implements InputPacket {

    @Override
    public String id() {
        return "OM";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player player = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(player == null){
            return;
        }
        
        String[] data = Utils.split(extra, "|");
        int id;
        byte target;
        int qu = 1;
        try{
            id = Integer.parseInt(data[0]);
            target = Byte.parseByte(data[1]);
            if(data.length > 2){
                qu = Integer.parseInt(data[2]);
            }
        }catch(Exception e){
            return;
        }
        
        GameItem item = player.getInventory().getItemById(id);
        
        if(item == null){
            Loggin.debug("objet %d introuvable", id);
            return;
        }
        
        player.getInventory().moveItem(item, qu, target);
        
        if(!player.getInventory().commitStates(session)){
            Loggin.debug("Rien à faire");
            return;
        }
        
        if(item.isWearable()){
            player.loadStuffStats();
            GameSendersRegistry.getPlayer().weightUsed(player, session);
            
            if(item.isAccessorie())
                GameSendersRegistry.getPlayer().accessories(player);
        }
        
    }
    
}
