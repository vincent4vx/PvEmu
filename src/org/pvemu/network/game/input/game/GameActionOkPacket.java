/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.game;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.gameaction.ActionPerformer;
//import org.pvemu.game.GameActionHandler;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionOkPacket implements InputPacket {

    @Override
    public String id() {
        return "GK";
    }

    @Override
    public void perform(String extra, IoSession session) {
        boolean ok = extra.charAt(0) == 'K';

        short actionID = 0;
        String[] args;

        try {
            args = Utils.split(extra.substring(1), "|");
            actionID = Short.parseShort(args[0]);
        } catch (Exception e) {
            return;
        }
        
        ActionPerformer performer = SessionAttributes.FIGHTER.exists(session) ?
                SessionAttributes.FIGHTER.getValue(session) :
                SessionAttributes.PLAYER.getValue(session);
        
        if(performer == null)
            return;
        
        performer.getActionsManager().endGameAction(actionID, ok, args);
        
    }
    
}
