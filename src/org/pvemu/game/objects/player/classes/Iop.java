/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player.classes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Iop extends ClassData {

    public Iop() {
        addSpell(1, 141, 'b');
        addSpell(1, 142, 'c');
        addSpell(1, 143, 'd');
        addSpell(3, 144);
        addSpell(6, 145);
        addSpell(9, 146);
        addSpell(13, 147);
        addSpell(17, 148);
        addSpell(21, 154);
        addSpell(26, 150);
        addSpell(31, 151);
        addSpell(36, 155);
        addSpell(42, 152);
        addSpell(48, 153);
        addSpell(54, 149);
        addSpell(60, 156);
        addSpell(70, 157);
        addSpell(80, 158);
        addSpell(90, 160);
        addSpell(100, 159);
        addSpell(200, 1908);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_IOP;
    }

    @Override
    public short getStartMap() {
        return 10294;
    }

    @Override
    public short getStartCell() {
        return 278;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7427;
    }

    @Override
    public short getAstrubStatueCell() {
        return 267;
    }
    
}
