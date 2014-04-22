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

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightEndTurnPacket implements InputPacket{

    @Override
    public String id() {
        return "Gt";
    }

    @Override
    public void perform(String extra, IoSession session) {
        PlayerFighter fighter = SessionAttributes.FIGHTER.getValue(session);
        
        if(fighter == null)
            return;
        
        if(fighter.canPlay())
            fighter.getFight().nextFighter();
    }
    
}
