package org.pvemu.game.fight.buff;

import org.pvemu.game.effect.EffectData;
import org.pvemu.game.effect.FighterEffect;
import org.pvemu.game.fight.Fighter;
import org.pvemu.common.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class Buff {
    final private FighterEffect effect;
    final private EffectData data;
    final private Fighter caster;
    final private Fighter fighter;
    final private int param1, param2, param3, param4;
    private int duration;

    public Buff(FighterEffect effect, EffectData data, Fighter caster, Fighter fighter, int param1, int param2, int param3, int param4) {
        this.effect = effect;
        this.data = data;
        this.caster = caster;
        this.fighter = fighter;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
        this.duration = data.getDuration();
    }

    public Buff(FighterEffect effect, EffectData data, Fighter caster, Fighter fighter, int param1, int param2, int param3) {
        this.effect = effect;
        this.data = data;
        this.caster = caster;
        this.fighter = fighter;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = 0;
        this.duration = data.getDuration();
    }

    public Buff(FighterEffect effect, EffectData data, Fighter caster, Fighter fighter, int param1, int param2) {
        this.effect = effect;
        this.data = data;
        this.caster = caster;
        this.fighter = fighter;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = 0;
        this.param4 = 0;
        this.duration = data.getDuration();
    }

    public Buff(FighterEffect effect, EffectData data, Fighter caster, Fighter fighter, int param1) {
        this.effect = effect;
        this.data = data;
        this.caster = caster;
        this.fighter = fighter;
        this.param1 = param1;
        this.param2 = 0;
        this.param3 = 0;
        this.param4 = 0;
        this.duration = data.getDuration();
    }

    public Buff(FighterEffect effect, EffectData data, Fighter caster, Fighter fighter) {
        this.effect = effect;
        this.data = data;
        this.caster = caster;
        this.fighter = fighter;
        this.param1 = data.getMin();
        this.param2 = data.getMax();
        this.param3 = 0;
        this.param4 = 0;
        this.duration = data.getDuration();
    }

    
    /**
     * apply the buff on fighter and decrement the duration count
     * @return true if the duration count is > 0
     */
    public boolean apply(){
        if(!fighter.isAlive())
            return false;
        
        Loggin.debug("applying %s", this);
        effect.applyBuff(this);
        
        return --duration > 0;
    }

    public FighterEffect getEffect() {
        return effect;
    }

    public EffectData getData() {
        return data;
    }

    public int getDuration() {
        return duration;
    }

    public Fighter getFighter() {
        return fighter;
    }

    public Fighter getCaster() {
        return caster;
    }

    public int getParam1() {
        return param1;
    }

    public int getParam2() {
        return param2;
    }

    public int getParam3() {
        return param3;
    }

    public int getParam4() {
        return param4;
    }

    @Override
    public String toString() {
        return "Buff{" + "effect=" + effect + ", data=" + data + ", caster=" + caster + ", fighter=" + fighter + ", param1=" + param1 + ", param2=" + param2 + ", param3=" + param3 + ", param4=" + param4 + ", duration=" + duration + '}';
    }
    
}
