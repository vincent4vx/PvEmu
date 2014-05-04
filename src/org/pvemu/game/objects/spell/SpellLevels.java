package org.pvemu.game.objects.spell;

import java.util.Collections;
import java.util.List;
import org.pvemu.models.Spell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SpellLevels {
    final static public byte LEVEL_MAX = 6;
    
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
        if(level < 1 || level > spells.size())
            return null;
        
        return spells.get(level - 1);
    }
    
    public GameSpell getNext(GameSpell spell){
        if(spells.size() == spell.getLevel())
            return spell;
        
        return spells.get(spell.getLevel());
    }
}
