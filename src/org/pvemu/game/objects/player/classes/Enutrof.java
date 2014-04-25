/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player.classes;

import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.dep.Stats.Element;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Enutrof extends ClassData {

    public Enutrof() {
        addSpell(1, 51, 'b');
        addSpell(1, 43, 'c');
        addSpell(1, 41, 'd');
        addSpell(3, 49);
        addSpell(6, 42);
        addSpell(9, 47);
        addSpell(13, 48);
        addSpell(17, 45);
        addSpell(21, 53);
        addSpell(26, 46);
        addSpell(31, 52);
        addSpell(36, 44);
        addSpell(42, 50);
        addSpell(48, 54);
        addSpell(54, 55);
        addSpell(60, 56);
        addSpell(70, 58);
        addSpell(80, 59);
        addSpell(90, 57);
        addSpell(100, 60);
        addSpell(200, 1903);
        
        setBoostStatsCost(Stats.Element.VITA, Stats.MAX_VALUE, 1);
        setBoostStatsCost(Stats.Element.SAGESSE, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.FORCE, (short)50, 1);
        setBoostStatsCost(Stats.Element.FORCE, (short)150, 2);
        setBoostStatsCost(Stats.Element.FORCE, (short)250, 3);
        setBoostStatsCost(Stats.Element.FORCE, (short)350, 4);
        
        setBoostStatsCost(Stats.Element.AGILITE, (short)20, 1);
        setBoostStatsCost(Stats.Element.AGILITE, (short)40, 2);
        setBoostStatsCost(Stats.Element.AGILITE, (short)60, 3);
        setBoostStatsCost(Stats.Element.AGILITE, (short)80, 4);
        
        setBoostStatsCost(Element.CHANCE, (short)100, 1);
        setBoostStatsCost(Element.CHANCE, (short)150, 2);
        setBoostStatsCost(Element.CHANCE, (short)230, 3);
        setBoostStatsCost(Element.CHANCE, (short)330, 4);
        
        setBoostStatsCost(Element.INTEL, (short)20, 1);
        setBoostStatsCost(Element.INTEL, (short)60, 2);
        setBoostStatsCost(Element.INTEL, (short)100, 3);
        setBoostStatsCost(Element.INTEL, (short)140, 4);
        
        setBoostStatsCost(Stats.Element.FORCE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.CHANCE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.AGILITE, Stats.MAX_VALUE, 5);
        setBoostStatsCost(Stats.Element.INTEL, Stats.MAX_VALUE, 5);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_ENUTROF;
    }

    @Override
    public short getStartMap() {
        return 10299;
    }

    @Override
    public short getStartCell() {
        return 286;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7442;
    }

    @Override
    public short getAstrubStatueCell() {
        return 255;
    }

    @Override
    public Stats getClassStats(int level) {
        Stats stats = super.getClassStats(level);
        
        stats.add(Element.PROSPEC, ClassesHandler.PROS_BONUS_ENU);
        
        return stats;
    }
    
    
    
}
