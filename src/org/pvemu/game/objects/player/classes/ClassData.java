/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player.classes;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.dep.Stats.Element;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.game.objects.spell.SpellFactory;
import org.pvemu.game.objects.spell.SpellLevels;
import org.pvemu.game.objects.player.SpellList;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class ClassData {
    static private class SpellByLevelData{
        final private int level;
        final private GameSpell spell;
        final private char position;

        SpellByLevelData(int level, GameSpell spell, char position) {
            this.level = level;
            this.spell = spell;
            this.position = position;
        }

        public int getLevel() {
            return level;
        }

        public GameSpell getSpell() {
            return spell;
        }

        public char getPosition() {
            return position;
        }
    }
    
    static private class BoostStatsCost implements Comparable<BoostStatsCost>{
        final private Stats.Element element;
        final private short maxStats;
        final private float cost;

        public BoostStatsCost(Element element, short maxStats, float cost) {
            this.element = element;
            this.maxStats = maxStats;
            this.cost = cost;
        }

        public Element getElement() {
            return element;
        }

        public short getMaxStats() {
            return maxStats;
        }

        public float getCost() {
            return cost;
        }

        @Override
        public int compareTo(BoostStatsCost t) {
            return maxStats - t.getMaxStats();
        }
        
    }
    
    final private Set<SpellByLevelData> spellsByLevel = new HashSet<>();
    final private Map<Stats.Element, Set<BoostStatsCost>> boostStatsCosts = new EnumMap<>(Stats.Element.class);
    
    public void addSpell(int level, int spellID, char position){
        SpellLevels spellLevels = SpellFactory.getSpellLevelsById(spellID);
        
        if(spellLevels == null)
            return;
        
        GameSpell spell = spellLevels.getSpellByLevel((byte)1);
        
        if(spell == null)
            return;
        
        spellsByLevel.add(new SpellByLevelData(level, spell, position));
    }
    
    public void setBoostStatsCost(Stats.Element element, short maxStats, float cost){
        if(!boostStatsCosts.containsKey(element)){
            boostStatsCosts.put(element, new TreeSet<BoostStatsCost>());
        }
        
        boostStatsCosts.get(element).add(new BoostStatsCost(element, maxStats, cost));
    }
    
    public float getBoostStatsCost(Player player, Stats.Element element){
        Set<BoostStatsCost> costs = boostStatsCosts.get(element);
        
        if(costs == null){
            Loggin.warning("Cannot found the boost stats cost for %s on class %d", element, id());
            return 1;
        }
        
        BoostStatsCost bsc;
        Iterator<BoostStatsCost> it = costs.iterator();
        
        do{
            bsc = it.next();
        }while(it.hasNext() && player.getBaseStats().get(element) >= bsc.getMaxStats());
        
        return bsc.getCost();
    }
    
    public void addSpell(int level, int spellID){
        addSpell(level, spellID, SpellList.DEFAULT_POS);
    }
    
    public void learnClassSpells(Player player){
        for(SpellByLevelData sld : spellsByLevel){
            if(player.getLevel() < sld.getLevel() 
               || player.getSpellList().hasAlreadyLearnedSpell(sld.getSpell().getModel().id))
                continue;
            
            player.getSpellList().learnSpell(sld.getSpell(), sld.getPosition());
        }
    }
    
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
