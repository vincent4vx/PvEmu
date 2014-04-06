/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.GameNpc;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.map.GMable;
import org.pvemu.models.NpcQuestion;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CreateDialogPacket implements InputPacket {

    @Override
    public String id() {
        return "DC";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        if(p.current_npc_question != null){
            GamePacketEnum.DIALOG_CREATE_ERROR.send(session);
            //onLeave(session);
            p.current_npc_question = null;
            GamePacketEnum.DIALOG_LEAVE.send(session);
            return;         
        }
        
        int id = 0;
        
        try{
            id = Integer.parseInt(extra);
        }catch(NumberFormatException e){
            GamePacketEnum.DIALOG_CREATE_ERROR.send(session);
            return;
        }
        
        GMable gma = p.getMap().getGMable(id);
        
        if(gma == null || !(gma instanceof GameNpc)){
            GamePacketEnum.DIALOG_CREATE_ERROR.send(session);
            return;
        }
        
        GameNpc GN = (GameNpc)gma;
        NpcQuestion Q = GN.getQuestion();
        
        if(Q == null){
            GamePacketEnum.DIALOG_CREATE_ERROR.send(session);
            return;
        }
        
        p.current_npc_question = Q;
        
        GamePacketEnum.DIALOG_CREATE.send(session, id);
        GamePacketEnum.DIALOG_QUESTION.send(session, new StringBuilder().append(Q.id).append('|').append(Q.responses).append(";4840").toString());
        
    }
    
}
