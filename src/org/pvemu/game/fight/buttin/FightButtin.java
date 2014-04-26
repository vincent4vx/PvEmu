/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin;

import java.util.HashSet;
import java.util.Set;
import org.pvemu.jelly.utils.Pair;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightButtin {
    final static private FightButtin emptyButtin = new FightButtin(0, 0, new HashSet<Pair<Integer, Integer>>());
    
    final private int kamas;
    final private long experience;
    final private Set<Pair<Integer, Integer>> items;

    public FightButtin(int kamas, long experience, Set<Pair<Integer, Integer>> items) {
        this.kamas = kamas;
        this.experience = experience;
        this.items = items;
    }

    public int getKamas() {
        return kamas;
    }

    public long getExperience() {
        return experience;
    }

    public Set<Pair<Integer, Integer>> getItems() {
        return items;
    }

    public static FightButtin emptyButtin() {
        return emptyButtin;
    }
    
}
