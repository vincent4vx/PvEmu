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
public class WaterAttackEffect extends BasicAttackEffect{

    @Override
    protected Stats.Element getActiveElement() {
        return Stats.Element.CHANCE;
    }

    @Override
    public short id() {
        return 96;
    }
    
}
