/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item.types;

import org.pvemu.game.objects.dep.Stats;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Amulet extends Wearable {

    public Amulet(Stats stats, InventoryEntry entry, ItemTemplate template) {
        super(stats, entry, template);
    }
    
}
