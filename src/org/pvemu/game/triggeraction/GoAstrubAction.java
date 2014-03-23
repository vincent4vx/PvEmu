/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.triggeraction;

import org.pvemu.game.objects.Player;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.YesFilter;
import org.pvemu.network.events.GameActionEvents;

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
        GameActionEvents.onCreateGameAction(player.getSession(), 2, player.getID(), 7);
    }

    @Override
    public Filter condition() {
        return new YesFilter();
    }
    
}
