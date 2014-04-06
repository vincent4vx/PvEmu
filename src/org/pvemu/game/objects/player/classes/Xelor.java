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
public class Xelor extends ClassData{

    public Xelor() {
        addSpell(1, 81);
        addSpell(1, 82);
        addSpell(1, 83);
        addSpell(3, 84);
        addSpell(6, 100);
        addSpell(9, 92);
        addSpell(13, 88);
        addSpell(17, 93);
        addSpell(21, 85);
        addSpell(26, 96);
        addSpell(31, 98);
        addSpell(36, 86);
        addSpell(42, 89);
        addSpell(48, 90);
        addSpell(54, 87);
        addSpell(60, 94);
        addSpell(70, 99);
        addSpell(80, 95);
        addSpell(90, 91);
        addSpell(100, 97);
        addSpell(200, 1905);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_XELOR;
    }

    @Override
    public short getStartMap() {
        return 10298;
    }

    @Override
    public short getStartCell() {
        return 300;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7332;
    }

    @Override
    public short getAstrubStatueCell() {
        return 312;
    }
    
}
