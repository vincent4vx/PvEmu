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
    public short id() {
        return 98;
    }
    
}
