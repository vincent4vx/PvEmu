/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.exchange;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class LeaveExchangePacket implements InputPacket {

    @Override
    public String id() {
        return "EV";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);//(Player)session.getAttribute("player");
        
        if(p == null){
            return;
        }
        
        if(p.getExchange() == null){
            return;
        }
        
        Player T = p.getExchange().getTarget();
        
        p.stopExchange();
        T.stopExchange();
        
        GamePacketEnum.EXCHANGE_LEAVE.send(session);
        GamePacketEnum.EXCHANGE_LEAVE.send(T.getSession());
        
    }
    
}
