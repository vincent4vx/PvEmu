/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item.types;

import java.util.Set;
import org.pvemu.game.effect.EffectData;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract class Wearable extends GameItem {

    public Wearable(Stats stats, Set<EffectData> effects, InventoryEntry entry, ItemTemplate template) {
        super(stats, effects, entry, template);
    }

    @Override
    final public boolean isWearable(){
        return true;
    }
    
}
