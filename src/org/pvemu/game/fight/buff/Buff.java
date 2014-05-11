/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buff;

import org.pvemu.game.effect.EffectData;
import org.pvemu.game.effect.FighterEffect;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class Buff {
    final private FighterEffect effect;
    final private EffectData data;
    final private Fighter caster;
    private int duration;

    public Buff(FighterEffect effect, EffectData data, Fighter caster) {
        this.effect = effect;
        this.data = data;
        this.caster = caster;
        this.duration = data.getDuration();
    }
    
    /**
     * apply the buff on fighter and decrement the duration count
     * @param fight
     * @param fighter
     * @return true if the duration count is > 0
     */
    public boolean apply(Fight fight, Fighter fighter){
        if(!fighter.isAlive())
            return false;
        
        effect.applyToFighter(data, caster, fighter);
        
        return --duration > 0;
    }
}
