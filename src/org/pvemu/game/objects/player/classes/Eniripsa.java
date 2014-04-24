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
public class Eniripsa extends ClassData{

    public Eniripsa() {
        addSpell(1, 125, 'b');
        addSpell(1, 128, 'c');
        addSpell(1, 121, 'd');
        addSpell(3, 124);
        addSpell(6, 122);
        addSpell(9, 126);
        addSpell(13, 127);
        addSpell(17, 123);
        addSpell(21, 130);
        addSpell(26, 131);
        addSpell(31, 132);
        addSpell(36, 133);
        addSpell(42, 134);
        addSpell(48, 135);
        addSpell(54, 129);
        addSpell(60, 136);
        addSpell(70, 137);
        addSpell(80, 138);
        addSpell(90, 139);
        addSpell(100, 140);
        addSpell(200, 1907);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_ENIRIPSA;
    }

    @Override
    public short getStartMap() {
        return 10283;
    }

    @Override
    public short getStartCell() {
        return 270;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7361;
    }

    @Override
    public short getAstrubStatueCell() {
        return 192;
    }
    
}
