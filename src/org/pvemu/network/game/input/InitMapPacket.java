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
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InitMapPacket implements InputPacket {

    @Override
    public String id() {
        return "GI";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player player = SessionAttributes.PLAYER.getValue(session);
        
        if(player == null)
            return;
        
        GameSendersRegistry.getMap().addGMable(player.getMap(), player);
        GameSendersRegistry.getMap().getAllGMable(player.getMap(), session);
        GamePacketEnum.MAP_LOADED.send(session);
    }
    
}
