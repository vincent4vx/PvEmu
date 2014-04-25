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
public class Sadida extends ClassData{

    public Sadida() {
        addSpell(1, 183, 'b');
        addSpell(1, 200, 'c');
        addSpell(1, 193, 'd');
        addSpell(3, 198);
        addSpell(6, 195);
        addSpell(9, 182);
        addSpell(13, 192);
        addSpell(17, 197);
        addSpell(21, 189);
        addSpell(26, 181);
        addSpell(31, 199);
        addSpell(36, 191);
        addSpell(42, 186);
        addSpell(48, 196);
        addSpell(54, 190);
        addSpell(60, 194);
        addSpell(70, 185);
        addSpell(80, 184);
        addSpell(90, 188);
        addSpell(100, 187);
        addSpell(200, 1910);
        
        setBoostStatsCost(Stats.Element.VITA, Stats.MAX_VALUE, 1);
        setBoostStatsCost(Stats.Element.SAGESSE, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.FORCE, (short)50, 1);
        setBoostStatsCost(Stats.Element.FORCE, (short)250, 2);
        setBoostStatsCost(Stats.Element.FORCE, (short)300, 3);
        setBoostStatsCost(Stats.Element.FORCE, (short)400, 4);
        
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
        return ClassesHandler.CLASS_SADIDA;
    }

    @Override
    public short getStartMap() {
        return 10279;
    }

    @Override
    public short getStartCell() {
        return 270;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7395;
    }

    @Override
    public short getAstrubStatueCell() {
        return 357;
    }
    
}
