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
public class Feca extends ClassData{

    public Feca() {
        addSpell(1, 3);
        addSpell(1, 6);
        addSpell(1, 17);
        addSpell(3, 4);
        addSpell(6, 2);
        addSpell(9, 1);
        addSpell(13, 9);
        addSpell(17, 18);
        addSpell(21, 20);
        addSpell(26, 14);
        addSpell(31, 19);
        addSpell(36, 5);
        addSpell(42, 16);
        addSpell(48, 8);
        addSpell(54, 12);
        addSpell(60, 11);
        addSpell(70, 10);
        addSpell(80, 7);
        addSpell(90, 15);
        addSpell(100, 13);
        addSpell(200, 1901);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_FECA;
    }

    @Override
    public short getStartMap() {
        return 10300;
    }

    @Override
    public short getStartCell() {
        return 308;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7398;
    }

    @Override
    public short getAstrubStatueCell() {
        return 284;
    }
    
}
