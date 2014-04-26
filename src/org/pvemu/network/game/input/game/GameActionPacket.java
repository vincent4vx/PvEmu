/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.game;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.gameaction.ActionPerformer;
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
        short actionID;
        String[] args;
        
        try {
            actionID = Short.parseShort(extra.substring(0, 3));
            args = Utils.split(extra.substring(3), ";");
        } catch (Exception e) {
            GameSendersRegistry.getGameAction().error(session);
            return;
        }
        
        ActionPerformer performer = SessionAttributes.FIGHTER.exists(session) ? 
                SessionAttributes.FIGHTER.getValue(session) :
                SessionAttributes.PLAYER.getValue(session);
        
        if(performer == null){
            GameSendersRegistry.getGameAction().error(session);
            return;
        }
        
        performer.getActionsManager().startGameAction(new GameActionData(performer, actionID, args));
    }
    
}
