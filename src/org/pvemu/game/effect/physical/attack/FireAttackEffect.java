/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect.physical.attack;

import org.pvemu.game.effect.physical.BasicAttackEffect;
import org.pvemu.game.objects.dep.Stats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FireAttackEffect extends BasicAttackEffect{

    @Override
    protected Stats.Element getActiveElement() {
        return Stats.Element.INTEL;
    }

    @Override
    protected Stats.Element getResistanceElement() {
        return Stats.Element.RES_FIRE;
    }

    @Override
    public short id() {
        return 99;
    }
    
}
