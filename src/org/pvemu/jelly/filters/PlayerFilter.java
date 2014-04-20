/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.filters;

import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.filters.comparators.Comparator;
import org.pvemu.jelly.filters.comparators.YesComparator;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerFilter extends Filter<Player>{
    private Comparator<Boolean> inFight = new YesComparator<>();

    public Comparator<Boolean> getInFight() {
        return inFight;
    }

    public void setInFight(Comparator<Boolean> inFight) {
        this.inFight = inFight;
    }
    
    @Override
    public boolean corresponds(Player obj) {
        return inFight.compare(obj.getActionsManager().isInFight());
    }
    
}
