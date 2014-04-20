/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.fight;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightReadyPacket implements InputPacket{

    @Override
    public String id() {
        return "GR";
    }

    @Override
    public void perform(String extra, IoSession session) {
        PlayerFighter fighter = SessionAttributes.FIGHTER.getValue(session);
        
        if(fighter == null)
            return;
        
        fighter.setReady(extra.equals("1"));
        GameSendersRegistry.getFight().ready(fighter);
        fighter.getFight().startIfAllReady();
    }
    
}
