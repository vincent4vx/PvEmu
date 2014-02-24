/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Constants;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.events.BasicEvents;
import static org.pvemu.network.events.CharacterEvents.onStatsChange;
import org.pvemu.network.events.ChatEvents;
import org.pvemu.network.events.MapEvents;
import org.pvemu.network.events.ObjectEvents;
import org.pvemu.network.game.GamePacketEnum;

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
        Player p = SessionAttributes.PLAYER.getValue(session);

        if (p == null) {
            return;
        }

        GamePacketEnum.GAME_CREATE_OK.send(session, p.getName());
        onStatsChange(session, p);
        ObjectEvents.onWeightChange(session, p);
        if(Constants.DOFUS_VER_ID >= 1100){
            GamePacketEnum.CHAT_CHANEL_ADD.send(session, p.getChanels());
        }
        GamePacketEnum.CHARACTER_RESTRICTION.send(session, p.restriction);
        ChatEvents.onSendErrorMessage(session, 89);
        MapEvents.onArrivedInGame(session);
        
        if(Constants.DOFUS_VER_ID < 1100){
            BasicEvents.onDate(session);
            
            if(p.getAccount().level > 0){
                BasicEvents.onPrompt(session);
            }
        }

    }
    
}
