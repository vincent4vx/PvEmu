/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player.classes;

import org.pvemu.game.objects.dep.Stats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Sram extends ClassData{

    public Sram() {
        addSpell(1, 61, 'b');
        addSpell(1, 72, 'c');
        addSpell(1, 65, 'd');
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
        
        setBoostStatsCost(Stats.Element.VITA, Stats.MAX_VALUE, 1);
        setBoostStatsCost(Stats.Element.SAGESSE, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.FORCE, (short)100, 1);
        setBoostStatsCost(Stats.Element.FORCE, (short)200, 2);
        setBoostStatsCost(Stats.Element.FORCE, (short)300, 3);
        setBoostStatsCost(Stats.Element.FORCE, (short)400, 4);
        
        setBoostStatsCost(Stats.Element.CHANCE, (short)20, 1);
        setBoostStatsCost(Stats.Element.CHANCE, (short)40, 2);
        setBoostStatsCost(Stats.Element.CHANCE, (short)60, 3);
        setBoostStatsCost(Stats.Element.CHANCE, (short)80, 4);
        
        setBoostStatsCost(Stats.Element.AGILITE, (short)100, 1);
        setBoostStatsCost(Stats.Element.AGILITE, (short)200, 2);
        setBoostStatsCost(Stats.Element.AGILITE, (short)300, 3);
        setBoostStatsCost(Stats.Element.AGILITE, (short)400, 4);
        
        setBoostStatsCost(Stats.Element.INTEL, (short)50, 2);
        setBoostStatsCost(Stats.Element.INTEL, (short)150, 3);
        setBoostStatsCost(Stats.Element.INTEL, (short)250, 4);
        
        setBoostStatsCost(Stats.Element.FORCE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.CHANCE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.AGILITE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.INTEL, Stats.MAX_VALUE, 5);
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
