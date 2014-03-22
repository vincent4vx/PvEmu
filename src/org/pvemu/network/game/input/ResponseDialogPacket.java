/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import java.util.ArrayList;
import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.NpcResponseAction;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ResponseDialogPacket implements InputPacket {

    @Override
    public String id() {
        return "DR";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        if(p.current_npc_question == null){
            //onLeave(session);
            GamePacketEnum.DIALOG_LEAVE.send(session);
            return;
        }
        
        try{
            String params[] = Utils.split(extra, "|");//packet.split("\\|");
            int qID = Integer.parseInt(params[0]);
            int rID = Integer.parseInt(params[1]);
            
            if(qID != p.current_npc_question.id){
                //onLeave(session);
                p.current_npc_question = null;
                GamePacketEnum.DIALOG_LEAVE.send(session);
                return;
            }
            
            ArrayList<NpcResponseAction> NRA_l = p.current_npc_question.getResponseActions(rID);
            
            if(NRA_l == null){
                //onLeave(session);
                GamePacketEnum.DIALOG_LEAVE.send(session);
                p.current_npc_question = null;
                return;
            }
            
            boolean close = true;
            
            for(NpcResponseAction NRA : NRA_l){
                NRA.getAction().performAction(p);
                if(NRA.action_id == 1){
                    close = false;
                }
            }
            
            if(close){
                //onLeave(session);
                p.current_npc_question = null;
                GamePacketEnum.DIALOG_LEAVE.send(session);
            }
        }catch(Exception e){
            //onLeave(session);
            p.current_npc_question = null;
            GamePacketEnum.DIALOG_LEAVE.send(session);
        }
        
    }
    
}
