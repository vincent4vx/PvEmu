/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.filters;

import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.comparators.Comparator;
import org.pvemu.common.filters.comparators.YesComparator;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerFilter extends Filter<Player>{
    private Comparator<Short> level = new YesComparator<>();
    private Comparator<Boolean> inFight = new YesComparator<>();

    public Comparator<Boolean> getInFight() {
        return inFight;
    }

    public void setInFight(Comparator<Boolean> inFight) {
        this.inFight = inFight;
    }

    public Comparator<Short> getLevel() {
        return level;
    }

    public void setLevel(Comparator<Short> level) {
        this.level = level;
    }
    
    @Override
    public boolean corresponds(Player obj) {
        return inFight.compare(obj.getActionsManager().isInFight())
                && level.compare(obj.getLevel());
    }
    
}
