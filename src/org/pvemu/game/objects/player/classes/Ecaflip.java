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
public class Ecaflip extends ClassData{

    public Ecaflip() {
        addSpell(1, 102, 'b');
        addSpell(1, 103, 'c');
        addSpell(1, 105, 'd');
        addSpell(3, 109);
        addSpell(6, 113);
        addSpell(9, 111);
        addSpell(13, 104);
        addSpell(17, 119);
        addSpell(21, 101);
        addSpell(26, 107);
        addSpell(31, 116);
        addSpell(36, 106);
        addSpell(42, 117);
        addSpell(48, 108);
        addSpell(54, 115);
        addSpell(60, 118);
        addSpell(70, 110);
        addSpell(80, 112);
        addSpell(90, 114);
        addSpell(100, 120);
        addSpell(200, 1906);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_ECAFLIP;
    }

    @Override
    public short getStartMap() {
        return 10276;
    }

    @Override
    public short getStartCell() {
        return 250;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7446;
    }

    @Override
    public short getAstrubStatueCell() {
        return 284;
    }
    
}
