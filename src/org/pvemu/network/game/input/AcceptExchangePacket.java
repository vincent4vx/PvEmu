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
public class AcceptExchangePacket implements InputPacket {

    @Override
    public String id() {
        return "EA";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        if(p.getExchange() == null){
            GamePacketEnum.EXCHANGE_CREATE_ERROR.send(session);
            return;
        }
        
        GamePacketEnum.EXCHANGE_CREATE_OK.send(session, 1);
        GamePacketEnum.EXCHANGE_CREATE_OK.send(p.getExchange().getTarget().getSession(), 1);
        
    }
    
}
