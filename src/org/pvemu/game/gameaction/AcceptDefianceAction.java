/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

import org.pvemu.game.fight.FightFactory;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AcceptDefianceAction implements GameAction{

    @Override
    public short id() {
        return GameActionsRegistry.ACCEPT_DEFIANCE;
    }

    @Override
    public void start(GameActionData data) {
        Loggin.debug("%s : decianfe accepted !", data.getPlayer().getName());
        GameSendersRegistry.getGameAction().unidentifiedGameActionToMap(
                data.getPlayer().getMap(), 
                id(), 
                data.getPlayer().getID(),
                data.getPlayer().getActionsManager().getDefianceTarget()
        );
        
        Player other = data.getPlayer().getMap().getPlayers().get(data.getPlayer().getActionsManager().getDefianceTarget());
        data.getPlayer().getActionsManager().setDefianceTarget(GameActionsManager.NO_DEFIANCE_TARGET);
        
        if(other == null){
            data.getPlayer().getActionsManager().setBusy(false);
            return;
        }
        
        other.getActionsManager().setDefianceTarget(GameActionsManager.NO_DEFIANCE_TARGET);
        
        FightFactory.defiance(data.getPlayer(), other);
    }

    @Override
    public void end(GameActionData data, boolean success, String[] args) {
    }
    
}
