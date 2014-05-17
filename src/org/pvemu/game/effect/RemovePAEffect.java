package org.pvemu.game.effect;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.buff.Buff;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.common.utils.Utils;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class RemovePAEffect extends BuffEffect{

    @Override
    public void startBuff(EffectData data, Fighter caster, Fighter target) {
        int jet = Utils.rand(data.getMin(), data.getMax());
        Buff buff = new Buff(this, data, caster, target, jet);
        
        if(buff.apply()){
            target.getBuffList().addBuff(buff);
        }
        GameSendersRegistry.getEffect().buffEffect(target.getFight(), buff);
    }

    @Override
    public void applyBuff(Buff buff) {
        buff.getFighter().getBuffStats().add(Stats.Element.PA, -buff.getParam1());
        if(buff.getDuration() == buff.getData().getDuration()){ //first turn
            GameSendersRegistry.getEffect().removePA(
                    buff.getFighter().getFight(), 
                    buff.getFighter().getID(), 
                    buff.getParam1()
            );
            buff.getFighter().updatePoints();
        }
    }

    @Override
    protected int getEfficiencyForOneFighter(EffectData data, Fight fight, Fighter caster, Fighter target) {
        int avg = (data.getMin() + data.getMax()) / 2;
        
        int efficiency = avg * 100;
        
        if(target.getTeam() == caster.getTeam())
            return -efficiency;
        
        return efficiency;
    }

    @Override
    public short id() {
        return 101;
    }

    @Override
    public EffectType getEffectType() {
        return EffectType.DEBUFF;
    }
    
}
