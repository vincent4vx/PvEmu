/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.GameActionHandler;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.events.GameActionEvents;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.output.GameSendersRegistry;

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
        Player player = SessionAttributes.PLAYER.getValue(session);
        if (player == null) {
            return;
        }
        short actionID = 0;
        //GameActionHandler.GameAction GA;
        GameActionData data;
        try {
            actionID = Short.parseShort(extra.substring(0, 3));
            String[] args = Utils.split(extra.substring(3), ";");
            data = new GameActionData(player, actionID, args);
          //  GA = new GameActionHandler.GameAction(p.getActions(), actionID, Utils.split(args, ";"));
        } catch (Exception e) {
            //GamePacketEnum.GAME_ACTION_ERROR.send(session);
            GameSendersRegistry.getGameAction().error(session);
            return;
        }
        
        player.getActionsManager().startGameAction(data);
        

        /*switch (actionID) {
            case 1: //déplacement
                GameActionEvents.onMoveAction(session, GA);
                break;
            case 500: //action sur la map
                GameActionEvents.onMapAction(p, GA);
                break;
            default:
                Loggin.debug("GameAction non géré : %d", actionID);
                GamePacketEnum.GAME_ACTION_ERROR.send(session);
        }*/
        
    }
    
}
