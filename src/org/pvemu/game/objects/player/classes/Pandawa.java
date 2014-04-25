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
public class Pandawa extends ClassData{

    public Pandawa() {
        addSpell(1, 686, 'b');
        addSpell(1, 692, 'c');
        addSpell(1, 687, 'd');
        addSpell(3, 689);
        addSpell(6, 690);
        addSpell(9, 691);
        addSpell(13, 688);
        addSpell(17, 693);
        addSpell(21, 694);
        addSpell(26, 695);
        addSpell(31, 696);
        addSpell(36, 697);
        addSpell(42, 698);
        addSpell(48, 699);
        addSpell(54, 700);
        addSpell(60, 701);
        addSpell(70, 702);
        addSpell(80, 703);
        addSpell(90, 704);
        addSpell(100, 705);
        addSpell(200, 1912);
        
        setBoostStatsCost(Stats.Element.VITA, Stats.MAX_VALUE, 1);
        setBoostStatsCost(Stats.Element.SAGESSE, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.FORCE, (short)50, 1);
        setBoostStatsCost(Stats.Element.FORCE, (short)200, 2);
        setBoostStatsCost(Stats.Element.FORCE, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.CHANCE, (short)50, 1);
        setBoostStatsCost(Stats.Element.CHANCE, (short)200, 2);
        setBoostStatsCost(Stats.Element.CHANCE, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.INTEL, (short)50, 1);
        setBoostStatsCost(Stats.Element.INTEL, (short)200, 2);
        setBoostStatsCost(Stats.Element.INTEL, Stats.MAX_VALUE, 3);
        
        setBoostStatsCost(Stats.Element.AGILITE, (short)50, 1);
        setBoostStatsCost(Stats.Element.AGILITE, (short)200, 2);
        setBoostStatsCost(Stats.Element.AGILITE, Stats.MAX_VALUE, 3);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_PANDAWA;
    }

    @Override
    public short getStartMap() {
        return 10289;
    }

    @Override
    public short getStartCell() {
        return 263;
    }

    @Override
    public short getAstrubStatueMap() {
        return 8035;
    }

    @Override
    public short getAstrubStatueCell() {
        return 340;
    }
    
}
