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
