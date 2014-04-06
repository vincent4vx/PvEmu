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
public class Sram extends ClassData{

    public Sram() {
        addSpell(1, 61);
        addSpell(1, 72);
        addSpell(1, 65);
        addSpell(3, 66);
        addSpell(6, 68);
        addSpell(9, 63);
        addSpell(13, 74);
        addSpell(17, 64);
        addSpell(21, 79);
        addSpell(26, 78);
        addSpell(31, 71);
        addSpell(36, 62);
        addSpell(42, 69);
        addSpell(48, 77);
        addSpell(54, 73);
        addSpell(60, 67);
        addSpell(70, 70);
        addSpell(80, 75);
        addSpell(90, 76);
        addSpell(100, 80);
        addSpell(200, 1904);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_SRAM;
    }

    @Override
    public short getStartMap() {
        return 10285;
    }

    @Override
    public short getStartCell() {
        return 234;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7392;
    }

    @Override
    public short getAstrubStatueCell() {
        return 282;
    }
    
}
