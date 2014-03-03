/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.Player;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class LeaveDialogPacket implements InputPacket {

    @Override
    public String id() {
        return "DV";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);
        
        if(p == null){
                return;
        }
        
        p.current_npc_question = null;
        
        GamePacketEnum.DIALOG_LEAVE.send(session);
       
    }
    
}
