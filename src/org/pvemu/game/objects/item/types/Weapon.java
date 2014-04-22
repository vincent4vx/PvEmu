/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item.types;

import java.util.Set;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.spell.effect.SpellEffectData;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Weapon extends Accessorie{
    final private Set<SpellEffectData> effects;
    
    public Weapon(Stats stats, Set<SpellEffectData> effects, InventoryEntry entry, ItemTemplate template) {
        super(stats, entry, template);
        this.effects = effects;
    }

    public Set<SpellEffectData> getEffects() {
        return effects;
    }
    
}
