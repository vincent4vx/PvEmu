/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item.types;

import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Resource extends GameItem {
    
    public Resource(Stats stats, InventoryEntry entry, ItemTemplate template) {
        super(stats, entry, template);
    }

    @Override
    public boolean isWearable() {
        return false;
    }
    
}
