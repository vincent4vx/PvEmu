package org.pvemu.game.effect;

import org.pvemu.game.objects.dep.Stats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AirAttackEffect extends BasicAttackEffect{

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
        return 98;
    }
    
}
