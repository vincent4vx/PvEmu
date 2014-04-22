/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item.factory;

import java.util.Set;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.types.Weapon;
import org.pvemu.game.effect.EffectData;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class WeaponFactory implements ItemFactoryInterface{

    @Override
    public GameItem newItem(Stats stats, InventoryEntry entry, ItemTemplate template) {
        Set<EffectData> effects = ItemsFactory.parseEffects(template.statsTemplate);
        return new Weapon(stats, effects, entry, template);
    }
    
}