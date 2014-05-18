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
public class Osamodas extends ClassData{

    public Osamodas() {
        addSpell(1, 34, 'b');
        addSpell(1, 21, 'c');
        addSpell(1, 23, 'd');
        addSpell(3, 26);
        addSpell(6, 22);
        addSpell(9, 35);
        addSpell(13, 28);
        addSpell(17, 37);
        addSpell(21, 30);
        addSpell(26, 27);
        addSpell(31, 24);
        addSpell(36, 33);
        addSpell(42, 25);
        addSpell(48, 38);
        addSpell(54, 36);
        addSpell(60, 32);
        addSpell(70, 29);
        addSpell(80, 39);
        addSpell(90, 40);
        addSpell(100, 31);
        addSpell(200, 1902);
        
        setBoostStatsCost(Stats.Element.VITA, Stats.MAX_VALUE, 1);
        setBoostStatsCost(Stats.Element.SAGESSE, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.FORCE, (short)50, 2);
        setBoostStatsCost(Stats.Element.FORCE, (short)150, 3);
        setBoostStatsCost(Stats.Element.FORCE, (short)250, 4);
        
        setBoostStatsCost(Stats.Element.CHANCE, (short)100, 1);
        setBoostStatsCost(Stats.Element.CHANCE, (short)200, 2);
        setBoostStatsCost(Stats.Element.CHANCE, (short)300, 3);
        setBoostStatsCost(Stats.Element.CHANCE, (short)400, 4);
        
        setBoostStatsCost(Stats.Element.AGILITE, (short)20, 1);
        setBoostStatsCost(Stats.Element.AGILITE, (short)40, 2);
        setBoostStatsCost(Stats.Element.AGILITE, (short)60, 3);
        setBoostStatsCost(Stats.Element.AGILITE, (short)80, 4);
        
        setBoostStatsCost(Stats.Element.INTEL, (short)100, 1);
        setBoostStatsCost(Stats.Element.INTEL, (short)200, 2);
        setBoostStatsCost(Stats.Element.INTEL, (short)300, 3);
        setBoostStatsCost(Stats.Element.INTEL, (short)400, 4);
        
        setBoostStatsCost(Stats.Element.FORCE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.CHANCE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.AGILITE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.INTEL, Stats.MAX_VALUE, 5);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_OSAMODAS;
    }

    @Override
    public short getStartMap() {
        //return 10284; //FIXME: inexistant start map
        return 10298;
    }

    @Override
    public short getStartCell() {
        //return 372; //FIXME: inexistant start map
        return 300;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7545;
    }

    @Override
    public short getAstrubStatueCell() {
        return 297;
    }
    
}
