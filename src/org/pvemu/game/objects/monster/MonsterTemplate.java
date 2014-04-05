/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.monster;

import org.pvemu.game.objects.dep.Stats;
import org.pvemu.models.Monster;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterTemplate {
    final private Monster model;
    final private short level;
    final private Stats basicStats;
    final private int xp;

    MonsterTemplate(Monster model, short level, Stats basicStats, int xp) {
        this.model = model;
        this.level = level;
        this.basicStats = basicStats;
        this.xp = xp;
    }

    public Monster getModel() {
        return model;
    }

    public short getLevel() {
        return level;
    }

    public Stats getBasicStats() {
        return basicStats;
    }

    public int getXp() {
        return xp;
    }
    
    
}
