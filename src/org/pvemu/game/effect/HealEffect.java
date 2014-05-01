/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class HealEffect extends FighterEffect{

    @Override
    public short id() {
        return 108;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.HEAL;
    }

    @Override
    public void applyToFighter(EffectData data, Fighter caster, Fighter target) {
        int jet = Utils.rand(data.getMin(), data.getMax());
        
        jet *= (1 + .01 * (double)(caster.getTotalStats().get(Stats.Element.INTEL)));
        jet += caster.getTotalStats().get(Stats.Element.SOIN);
        
        if(jet < 0)
            jet = 0;
        
        target.addVita((short)jet);
    }

    @Override
    protected int getEfficiencyForOneFighter(EffectData data, Fight fight, Fighter caster, Fighter target) {
        float avgJet = (data.getMin() + data.getMax()) / 2;
        int coef = 1 + caster.getTotalStats().get(Stats.Element.INTEL);
        
        if(coef < 1)
            coef = 1;
        
        int efficiency = (int)(10 * avgJet * coef);
        
        if(target.getTeam() != caster.getTeam())
            efficiency = -efficiency;
        
        return efficiency;
    }
    
}
