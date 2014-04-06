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
abstract public class ClassData {
    abstract public byte id();
    
    public short getGfxID(byte sex){
        return (short)(id() * 10 + sex);
    }
    
    abstract public short getStartMap();
    abstract public short getStartCell();
    
    abstract public short getAstrubStatueMap();
    abstract public short getAstrubStatueCell();
    
    public Stats getClassStats(int level){
        Stats stats = new Stats();
        
        stats.add(Element.PA, ClassesHandler.BASE_PA);
        stats.add(Element.PM, ClassesHandler.BASE_PM);
        stats.add(Element.INIT, ClassesHandler.BASE_INIT);
        stats.add(Element.VITA, (short)(ClassesHandler.BASE_VITA + (level - 1) * ClassesHandler.VITA_PER_LVL));
        stats.add(Element.PODS, ClassesHandler.BASE_PODS);
        stats.add(Element.PROSPEC, ClassesHandler.BASE_PROS);
        stats.add(Element.INVOC, ClassesHandler.BASE_INVOC);
        
        if(level >= 100){
            stats.add(Element.PA, ClassesHandler.BONUS_PA_LVL100);
        }
        
        return stats;
    }
}
