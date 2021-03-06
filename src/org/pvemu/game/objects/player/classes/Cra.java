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
public class Cra extends ClassData{

    public Cra() {
        addSpell(1, 161, 'b');
        addSpell(1, 169, 'c');
        addSpell(1, 164, 'd');
        addSpell(3, 163);
        addSpell(6, 165);
        addSpell(9, 172);
        addSpell(13, 167);
        addSpell(17, 168);
        addSpell(21, 162);
        addSpell(26, 170);
        addSpell(31, 171);
        addSpell(36, 166);
        addSpell(42, 173);
        addSpell(48, 174);
        addSpell(54, 176);
        addSpell(60, 175);
        addSpell(70, 178);
        addSpell(80, 177);
        addSpell(90, 179);
        addSpell(100, 180);
        addSpell(200, 1909);
        
        setBoostStatsCost(Stats.Element.VITA, Stats.MAX_VALUE, 1);
        setBoostStatsCost(Stats.Element.SAGESSE, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.CHANCE, (short)20, 1);
        setBoostStatsCost(Stats.Element.CHANCE, (short)40, 2);
        setBoostStatsCost(Stats.Element.CHANCE, (short)60, 3);
        setBoostStatsCost(Stats.Element.CHANCE, (short)80, 4);
        
        setBoostStatsCost(Stats.Element.AGILITE, (short)50, 1);
        setBoostStatsCost(Stats.Element.AGILITE, (short)100, 2);
        setBoostStatsCost(Stats.Element.AGILITE, (short)150, 3);
        setBoostStatsCost(Stats.Element.AGILITE, (short)200, 4);
        
        setBoostStatsCost(Stats.Element.INTEL, (short)50, 1);
        setBoostStatsCost(Stats.Element.INTEL, (short)150, 2);
        setBoostStatsCost(Stats.Element.INTEL, (short)250, 3);
        setBoostStatsCost(Stats.Element.INTEL, (short)350, 4);
        
        setBoostStatsCost(Stats.Element.FORCE, (short)50, 1);
        setBoostStatsCost(Stats.Element.FORCE, (short)150, 2);
        setBoostStatsCost(Stats.Element.FORCE, (short)250, 3);
        setBoostStatsCost(Stats.Element.FORCE, (short)350, 4);
        
        setBoostStatsCost(Stats.Element.FORCE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.CHANCE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.AGILITE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.INTEL, Stats.MAX_VALUE, 5);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_CRA;
    }

    @Override
    public short getStartMap() {
        return 10292;
    }

    @Override
    public short getStartCell() {
        return 300;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7378;
    }

    @Override
    public short getAstrubStatueCell() {
        return 324;
    }
    
}
