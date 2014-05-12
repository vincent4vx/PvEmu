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
                + (buff.getParam1() == 0 ? "" : buff.getParam1()) + ";"
                + (buff.getParam2() == 0 ? "" : buff.getParam2()) + ";"
                + (buff.getParam3() == 0 ? "" : buff.getParam3()) + ";"
                + (buff.getParam4() == 0 ? "" : buff.getParam4()) + ";"
                + buff.getDuration() + ";"
                + buff.getData().getSpellID();
    }
}
