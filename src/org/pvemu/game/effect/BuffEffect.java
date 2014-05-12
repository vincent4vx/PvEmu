/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect;

import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class BuffEffect extends FighterEffect{

    @Override
    public void applyToFighter(int min, int max, Fighter caster, Fighter target) {
        throw new UnsupportedOperationException("Cannot apply directly a buff to fighter"); //To change body of generated methods, choose Tools | Templates.
    }
    
}
