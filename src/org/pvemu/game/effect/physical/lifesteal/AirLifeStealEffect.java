package org.pvemu.game.effect.physical.lifesteal;

import org.pvemu.game.effect.physical.LifeStealEffect;
import org.pvemu.game.objects.dep.Stats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AirLifeStealEffect extends LifeStealEffect{

    @Override
    protected Stats.Element getActiveElement() {
        return Stats.Element.AGILITE;
    }

    @Override
    protected Stats.Element getResistanceElement() {
        return Stats.Element.RES_AIR;
    }

    @Override
    public short id() {
        return 93;
    }
    
}
