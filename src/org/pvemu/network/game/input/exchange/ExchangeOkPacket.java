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
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ExchangeOkPacket implements InputPacket {

    @Override
    public String id() {
        return "EK";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player player = SessionAttributes.PLAYER.getValue(session);
        
        if(player == null || player.getExchange() == null)
            return;
            
        Player target = player.getExchange().getTarget();
        
        player.getExchange().setState(true);
        GameSendersRegistry.getExchange().exchangeOk(player, target, true);
        
        if(target.getExchange().getState()){ //end of exchange
            player.getExchange().accept();
            target.getExchange().accept();

            player.stopExchange();
            target.stopExchange();

            GamePacketEnum.EXCHANGE_ACCEPT.send(session);
            GamePacketEnum.EXCHANGE_ACCEPT.send(target.getSession());
        }
    }
    
}
