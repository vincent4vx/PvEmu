package org.pvemu.game.effect;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.jelly.utils.Utils;

/**
 * simple physical attacks
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract class BasicAttackEffect extends FighterEffect{

    abstract protected Stats.Element getActiveElement();
    abstract protected Stats.Element getResistanceElement();

    @Override
    public void applyToFighter(EffectData data, Fighter caster, Fighter target) {
        int jet = Utils.rand(data.getMin(), data.getMax());
        
        jet *= (1 + .01 * (double)(caster.getTotalStats().get(getActiveElement()) + caster.getTotalStats().get(Stats.Element.PERDOM)));
        jet += caster.getTotalStats().get(Stats.Element.DOMMAGE);
        
        jet -= jet * (.01 * (double)(target.getTotalStats().get(getResistanceElement())));
        
        if(jet < 0)
            jet = 0;
        
        target.removeVita((short)jet);
    }

    @Override
    protected int getEfficiencyForOneFighter(EffectData data, Fight fight, Fighter caster, Fighter target) {
        float avgJet = (data.getMin() + data.getMax()) / 2;
        int coef = 1 + caster.getTotalStats().get(getActiveElement());
        
        if(coef < 1)
            coef = 1;
        
        float res = .01f * target.getTotalStats().get(getResistanceElement());
        
        if(res > 1)
            res = 1;
        
        int efficiency = (int)(10 * avgJet * coef * (1 - res));
        
        if(target.getTeam() == caster.getTeam())
            efficiency = -efficiency;
        
        return efficiency;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.ATTACK;
    }
}
