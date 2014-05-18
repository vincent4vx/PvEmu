/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect.physical;

import org.pvemu.game.effect.EffectData;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class LifeStealEffect extends BasicAttackEffect{

    @Override
    public void applyToFighter(int min, int max, Fighter caster, Fighter target) {
        int jet = getJet(min, max, caster, target);
        target.removeVita(jet);
        caster.addVita(jet / 2);
    }

    @Override
    protected int getEfficiencyForOneFighter(EffectData data, Fight fight, Fighter caster, Fighter target) {
        return 2 * super.getEfficiencyForOneFighter(data, fight, caster, target);
    }
}
