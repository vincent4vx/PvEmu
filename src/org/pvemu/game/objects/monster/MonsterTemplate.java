/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.monster;

import java.util.Map;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.models.Monster;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterTemplate {
    final private byte grade;
    final private Monster model;
    final private short level;
    final private Stats basicStats;
    final private Map<Integer, GameSpell> spells;
    final private int xp;

    MonsterTemplate(byte grade, Monster model, short level, Stats basicStats, Map<Integer, GameSpell> spells, int xp) {
        this.grade = grade;
        this.model = model;
        this.level = level;
        this.basicStats = basicStats;
        this.spells = spells;
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

    public byte getGrade() {
        return grade;
    }

    public Map<Integer, GameSpell> getSpells() {
        return spells;
    }
}
