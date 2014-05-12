package org.pvemu.game.effect;

import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.buff.Buff;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class PhysicalEffect extends FighterEffect{

    @Override
    public void applyBuff(Buff buff) {
        applyToFighter(buff.getData(), buff.getCaster(), buff.getFighter());
    }

    @Override
    public void startBuff(EffectData data, Fighter caster, Fighter target) {
        Buff buff = new Buff(this, data, caster, target);
        target.getBuffList().addBuff(buff);
        GameSendersRegistry.getEffect().buffEffect(target.getFight(), buff);
    }

    
}
