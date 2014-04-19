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
public class FightChangePlacePacket implements InputPacket{

    @Override
    public String id() {
        return "Gp";
    }

    @Override
    public void perform(String extra, IoSession session) {
        PlayerFighter fighter = SessionAttributes.FIGHTER.getValue(session);
        
        if(fighter == null){
            GameSendersRegistry.getFight().changePlaceError(session);
            return;
        }
        
        short cellID;
        
        try{
            cellID = Short.parseShort(extra);
        }catch(NumberFormatException e){
            GameSendersRegistry.getFight().changePlaceError(session);
            return;
        }
        
        if(!fighter.getFight().getMap().isValidPlace(fighter, cellID)){
            GameSendersRegistry.getFight().changePlaceError(session);
            return;
        }
        
        fighter.getFight().getMap().moveFighter(fighter, cellID);
        GameSendersRegistry.getFight().changePlace(fighter.getFight(), fighter);
    }
    
}
