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
public class NeutralAttackEffect implements Effect{

    @Override
    public short id() {
        return 100;
    }

    @Override
    public void applyToFight(EffectData data, Fighter caster, Fight fight, short cell) {
        Fighter target = fight.getMap().getFighter(cell);
        
        if(target == null || !target.isAlive())
            return;
        
        short jet = (short)Utils.rand(data.getMin(), data.getMax());
        
        jet *= (1 + .01 * (double)(caster.getCurrentStats().get(Stats.Element.FORCE) + caster.getCurrentStats().get(Stats.Element.PERDOM)));
        jet += caster.getCurrentStats().get(Stats.Element.DOMMAGE);
        
        target.removeVita(jet);
    }
    
}
