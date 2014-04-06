/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.spell;

import java.util.Collections;
import java.util.List;
import org.pvemu.models.Spell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SpellLevels {
    final private Spell model;
    final private List<GameSpell> spells;

    public SpellLevels(Spell model, List<GameSpell> spells) {
        this.model = model;
        this.spells = Collections.unmodifiableList(spells);
    }

    public Spell getModel() {
        return model;
    }
    
    public GameSpell getSpellByLevel(byte level){
        return spells.get(level - 1);
    }
    
    public GameSpell getNext(GameSpell spell){
        if(spells.size() == spell.getLevel())
            return spell;
        
        return spells.get(spell.getLevel());
    }
}
