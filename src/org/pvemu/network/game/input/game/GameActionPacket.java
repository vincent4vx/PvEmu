/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.game;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
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
        PlayerFighter fighter = SessionAttributes.FIGHTER.getValue(session);
        
        if (player == null) {
            return;
        }
        
        short actionID = 0;
        GameActionData data;
        
        try {
            actionID = Short.parseShort(extra.substring(0, 3));
            String[] args = Utils.split(extra.substring(3), ";");
            data = new GameActionData(player, fighter, actionID, args);
        } catch (Exception e) {
            GameSendersRegistry.getGameAction().error(session);
            return;
        }
        
        player.getActionsManager().startGameAction(data);
        
    }
    
}
