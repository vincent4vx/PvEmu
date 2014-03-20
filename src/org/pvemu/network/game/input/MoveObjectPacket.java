/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.inventory.Inventory.MoveState;
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
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        String[] data = Utils.split(extra, "|");//packet.split("\\|");
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
        
        //boolean result = p.getInventory().moveItem(id, qu, target);
        GameItem item = p.getInventory().getItemById(id);
        
        if(item == null){
            Loggin.debug("objet %d introuvable", id);
            return;
        }
        
        MoveState state = p.getInventory().moveItem(item, qu, target);
        
        if(state == MoveState.ERROR){
            Loggin.debug("Impossible de déplacer l'item %d", id);
            return;
        }
        
        /*if(state == MoveState.ADD){
            GameSendersRegistry.getObject().addItem(item, session);
        }else if(state == MoveState.STACK){
            GameSendersRegistry.getObject().quantityChange(item, session);
        }else if(state == MoveState.MOVE){
            GameSendersRegistry.getObject().moveItem(item, session);
        }*/
        
        ActionsRegistry.getObject().itemStateChange(item, state, session);
        
        if(item.isWearable()){
            p.loadStuffStats();
            GameSendersRegistry.getPlayer().weightUsed(p, session);
            
            if(item.isAccessorie())
                GameSendersRegistry.getPlayer().accessories(p);
        }
        
        Loggin.debug("Objet %d déplacé avec succès !", id);
        
    }
    
}
