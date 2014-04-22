/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.game;

import org.pvemu.game.fight.FightFactory;
import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;
import org.pvemu.game.gameaction.GameActionsManager;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AcceptDefianceAction implements GameAction<Player>{

    @Override
    public short id() {
        return GameActionsRegistry.ACCEPT_DEFIANCE;
    }

    @Override
    public void start(GameActionData<Player> data) {
        Loggin.debug("%s : decianfe accepted !", data.getPerformer().getName());
        GameSendersRegistry.getGameAction().unidentifiedGameActionToMap(
                data.getPerformer().getMap(), 
                id(), 
                data.getPerformer().getID(),
                data.getPerformer().getActionsManager().getDefianceTarget()
        );
        
        Player other = data.getPerformer().getMap().getPlayers().get(data.getPerformer().getActionsManager().getDefianceTarget());
        data.getPerformer().getActionsManager().setDefianceTarget(GameActionsManager.NO_DEFIANCE_TARGET);
        
        if(other == null){
            data.getPerformer().getActionsManager().setBusy(false);
            return;
        }
        
        other.getActionsManager().setDefianceTarget(GameActionsManager.NO_DEFIANCE_TARGET);
        
        FightFactory.defiance(data.getPerformer(), other);
    }

    @Override
    public void end(GameActionData<Player> data, boolean success, String[] args) {
    }
    
}
