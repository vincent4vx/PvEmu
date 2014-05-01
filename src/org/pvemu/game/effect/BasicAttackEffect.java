package org.pvemu.game.effect;

import org.pvemu.game.fight.Fighter;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract class BasicAttackEffect extends FighterEffect{

    abstract protected Stats.Element getActiveElement();

    @Override
    public void applyToFighter(EffectData data, Fighter caster, Fighter target) {
        int jet = Utils.rand(data.getMin(), data.getMax());
        
        jet *= (1 + .01 * (double)(caster.getTotalStats().get(getActiveElement()) + caster.getTotalStats().get(Stats.Element.PERDOM)));
        jet += caster.getTotalStats().get(Stats.Element.DOMMAGE);
        
        target.removeVita((short)jet);
    }
    
}
