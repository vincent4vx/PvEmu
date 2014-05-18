/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect.physical.lifesteal;

import org.pvemu.game.effect.physical.LifeStealEffect;
import org.pvemu.game.objects.dep.Stats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class WaterLifeStealEffect extends LifeStealEffect{

    @Override
    protected Stats.Element getActiveElement() {
        return Stats.Element.CHANCE;
    }

    @Override
    protected Stats.Element getResistanceElement() {
        return Stats.Element.RES_WATER;
    }

    @Override
    public short id() {
        return 91;
    }
    
}
