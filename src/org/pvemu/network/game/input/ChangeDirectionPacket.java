/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ChangeDirectionPacket implements InputPacket {

    @Override
    public String id() {
        return "eD";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);
        
        if(p == null){
            return;
        }
        
        byte dir = 0;
        
        try{
            dir = Byte.parseByte(extra);
            
            if(dir < 0){
                dir = 0;
            }else if(dir > 7){
                dir = 7;
            }
        }catch(NumberFormatException e){
            return;
        }
        
        p.orientation = dir;
        
        GamePacketEnum.EMOTE_DIRECTION.sendToMap(p.getMap(), new StringBuilder().append(p.getID()).append('|').append(dir).toString());
        
    }
    
}
