/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect;

import org.pvemu.game.fight.Fighter;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract class BasicAttackEffect implements Effect{

    abstract protected Stats.Element getActiveElement();

    @Override
    public void applyToFighter(EffectData data, Fighter caster, Fighter target) {
        int jet = Utils.rand(data.getMin(), data.getMax());
        
        jet *= (1 + .01 * (double)(caster.getCurrentStats().get(getActiveElement()) + caster.getCurrentStats().get(Stats.Element.PERDOM)));
        jet += caster.getCurrentStats().get(Stats.Element.DOMMAGE);
        
        target.removeVita((short)jet);
    }
    
}
