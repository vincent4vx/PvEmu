/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.game.objects.spell.SpellFactory;
import org.pvemu.game.objects.spell.SpellLevels;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.LearnedSpell;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SpellList {
    final static public char DEFAULT_POS = 'a';
    
    final private int owner;
    final private Map<Integer, LearnedSpell> learnedSpells = new HashMap<>();
    final private Map<Integer, GameSpell> spells = new HashMap<>();

    public SpellList(int owner, Collection<LearnedSpell> spellList) {
        this.owner = owner;
        for(LearnedSpell spell : spellList){
            learnedSpells.put(spell.spell, spell);
            GameSpell gs = SpellFactory.getSpellLevelsById(spell.spell).getSpellByLevel(spell.level);
            spells.put(spell.spell, gs);
        }
    }
    
    public void learnSpell(GameSpell spell, char position){
        if(hasAlreadyLearnedSpell(spell.getModel().id))
            return;
        
        LearnedSpell ls = new LearnedSpell();
        
        ls.player = owner;
        ls.spell = spell.getModel().id;
        ls.level = spell.getLevel();
        ls.position = position;
        
        learnedSpells.put(ls.spell, ls);
        spells.put(spell.getModel().id, spell);
        DAOFactory.learnedSpell().create(ls);
    }
    
    public Collection<GameSpell> getSpells(){
        return spells.values();
    }
    
    public char getSpellPosition(GameSpell spell){
        return learnedSpells.get(spell.getModel().id).position;
    }
    
    public boolean hasAlreadyLearnedSpell(int spellID){
        return spells.containsKey(spellID);
    }
    
    public GameSpell getSpell(int id){
        return spells.get(id);
    }
    
    public void save(){
        for(LearnedSpell spell : learnedSpells.values()){
            DAOFactory.learnedSpell().update(spell);
        }
    }
    
    public void moveSpell(int spellID, char dest){
        LearnedSpell ls = learnedSpells.get(spellID);
        
        if(ls == null)
            return;
        
        for(LearnedSpell ls2 : learnedSpells.values()){
            if(ls2.position == dest)
                ls2.position = DEFAULT_POS;
        }
        
        ls.position = dest;
    }
    
    private boolean canBoostSpell(Player player, GameSpell spell){
        if(spell.getLevel() >= SpellLevels.LEVEL_MAX){
            Loggin.debug("spell already on max level");
            return false;
        }
        
        GameSpell next = spell.getSpellLevels().getNext(spell);
        
        if(player.getCharacter().spellPoints < spell.getLevel()){
            Loggin.debug("Player doesn't have enought spell points (%d/%d)", player.getCharacter().spellPoints, spell.getLevel());
            return false;
        }
        
        if(player.getLevel() < next.getMinLevel()){
            Loggin.debug("Player is to low to boost this spell (%d/%d)", player.getLevel(), next.getMinLevel());
            return false;
        }
        
        return true;
    }
    
    public byte boostSpell(Player player, int spellID){
        GameSpell spell = spells.get(spellID);
        
        if(spell == null || !canBoostSpell(player, spell))
            return -1;
        
        player.getCharacter().spellPoints -= spell.getLevel();
        GameSpell next = spell.getSpellLevels().getNext(spell);
        spells.put(spellID, next);
        LearnedSpell ls = learnedSpells.get(spellID);
        ls.level = next.getLevel();
        
        return next.getLevel();
    }
}
