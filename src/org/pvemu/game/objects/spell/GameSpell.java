/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.spell;

import org.pvemu.game.objects.spell.effect.SpellEffectData;
import java.util.Collections;
import java.util.Set;
import org.pvemu.models.Spell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameSpell {
    final private Spell model;
    final private byte level;
    final private Set<SpellEffectData> effects;
    final private Set<SpellEffectData> critical;

    public GameSpell(Spell model, byte level, Set<SpellEffectData> effects, Set<SpellEffectData> critical) {
        this.model = model;
        this.level = level;
        this.effects = Collections.unmodifiableSet(effects);
        this.critical = Collections.unmodifiableSet(critical);
    }

    public Spell getModel() {
        return model;
    }
    
    public SpellLevels getSpellLevels(){
        return SpellFactory.getSpellLevelsById(model.id);
    }

    public Set<SpellEffectData> getEffects() {
        return effects;
    }

    public Set<SpellEffectData> getCritical() {
        return critical;
    }

    public byte getLevel() {
        return level;
    }
}
