/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.game.fight.buff.Buff;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class EffectGenerator {
    public String generateBuffEffect(Buff buff){
        return buff.getEffect().id() + ";"
                + buff.getFighter().getID() + ";"
                + buff.getData().getMin() + ";"
                + buff.getData().getMax() + ";"
                + ";" //TODO: current jet?
                + ";" //TODO: ?
                + buff.getDuration() + ";"
                + buff.getData().getSpellID();
    }
}
