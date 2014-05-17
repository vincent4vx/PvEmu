/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.player;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.Loggin;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class BoostStatsPacket implements InputPacket{

    @Override
    public String id() {
        return "AB";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Player player = SessionAttributes.PLAYER.getValue(session);
        
        if(player == null)
            return;
        
        int statsID;
        
        try{
            statsID = Integer.parseInt(extra);
        }catch(NumberFormatException e){
            Loggin.debug("cannot parse AB packet : %s", e);
            return;
        }
        
        if(player.boostStats(statsID)){
            GameSendersRegistry.getPlayer().stats(player, session);
        }
    }
    
}
