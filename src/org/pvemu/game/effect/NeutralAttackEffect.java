/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect;

import org.pvemu.game.objects.dep.Stats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class NeutralAttackEffect extends BasicAttackEffect{

    @Override
    protected Stats.Element getActiveElement() {
        return Stats.Element.FORCE;
    }

    @Override
    protected Stats.Element getResistanceElement() {
        return Stats.Element.RES_NEUTRAL;
    }

    @Override
    public short id() {
        return 100;
    }
    
}
