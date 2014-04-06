/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.spell;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SpellList {
    final static private char DEFAULT_POS = 'a';
    
    final private Map<Integer, GameSpell> spells = new HashMap<>();
    final private Map<Integer, Character> spellsPositions = new HashMap<>();
    
    public void learnSpell(GameSpell spell){
        spells.put(spell.getModel().id, spell);
    }
    
    public Collection<GameSpell> getSpells(){
        return spells.values();
    }
    
    public char getSpellPosition(GameSpell spell){
        if(!spellsPositions.containsKey(spell.getModel().id))
            return DEFAULT_POS;
        return spellsPositions.get(spell.getModel().id);
    }
    
    public boolean hasAlreadyLearnedSpell(int spellID){
        return spells.containsKey(spellID);
    }
}
