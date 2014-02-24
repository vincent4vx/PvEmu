/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.GameActionHandler;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.events.GameActionEvents;
import org.pvemu.network.events.MapEvents;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionPacket implements InputPacket {

    @Override
    public String id() {
        return "GA";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player p = SessionAttributes.PLAYER.getValue(session);
        if (p == null) {
            return;
        }
        int actionID = 0;
        GameActionHandler.GameAction GA;
        try {
            actionID = Integer.parseInt(extra.substring(0, 3));
            String args = extra.substring(3);

            GA = new GameActionHandler.GameAction(p.getActions(), actionID, Utils.split(args, ";"));
        } catch (Exception e) {
            GamePacketEnum.GAME_ACTION_ERROR.send(session);
            return;
        }

        switch (actionID) {
            case 1: //déplacement
                GameActionEvents.onMoveAction(session, GA);
                break;
            case 500: //action sur la map
                GameActionEvents.onMapAction(p, GA);
                break;
            default:
                Loggin.debug("GameAction non géré : %d", actionID);
                GamePacketEnum.GAME_ACTION_ERROR.send(session);
        }
        
    }
    
}
