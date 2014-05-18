/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.fightertype;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.monster.MonsterTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InvocationFighter extends MonsterFighter{
    final private Fighter invocator;

    public InvocationFighter(int id, MonsterTemplate monster, Stats baseStats, Fight fight, Fighter invocator) {
        super(id, monster, baseStats, fight);
        this.invocator = invocator;
    }

    public Fighter getInvocator() {
        return invocator;
    }

    @Override
    public FightTeam getTeam() {
        return invocator.getTeam();
    }
}
