/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Constants;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CreateGamePacket implements InputPacket {

    @Override
    public String id() {
        return "GC";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player player = SessionAttributes.PLAYER.getValue(session);

        if (player == null) {
            return;
        }

        GamePacketEnum.GAME_CREATE_OK.send(session, player.getName());
        
        GameSendersRegistry.getPlayer().stats(player, session);
        GameSendersRegistry.getPlayer().weightUsed(player, session);
        if(Constants.DOFUS_VER_ID >= 1100){
            GamePacketEnum.CHAT_CHANEL_ADD.send(session, player.getChanels());
        }
        GamePacketEnum.CHARACTER_RESTRICTION.send(session, player.restriction);
        GameSendersRegistry.getInformativeMessage().error(session, 89);
        //MapEvents.onArrivedInGame(session);
        ActionsRegistry.getMap().addPlayer(player.getMap(), player);
        
        if(Constants.DOFUS_VER_ID < 1100){
            GameSendersRegistry.getBasic().date(session);
            GameSendersRegistry.getBasic().time(session);
        }

    }
    
}
