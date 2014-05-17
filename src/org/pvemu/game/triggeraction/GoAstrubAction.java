/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.triggeraction;

import org.pvemu.game.cinematic.CinematicsHandler;
import org.pvemu.game.gameaction.GameActionFactory;
import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.YesFilter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GoAstrubAction implements TriggerAction {

    @Override
    public short actionId() {
        return 69;
    }

    @Override
    public void perform(Trigger trigger, Player player) {
        //GameActionEvents.onCreateGameAction(player.getSession(), 2, player.getID(), 7);
        player.getActionsManager().startGameAction(
                GameActionFactory.newCinematic(player, CinematicsHandler.GO_TO_ASTRUB)
        );
    }

    @Override
    public Filter condition() {
        return new YesFilter();
    }
    
}
