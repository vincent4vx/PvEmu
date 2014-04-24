package org.pvemu.game.objects.item.factory;

import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.WeaponData;
import org.pvemu.game.objects.item.types.Wand;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class WandFactory implements ItemFactoryInterface{

    @Override
    public GameItem newItem(Stats stats, InventoryEntry entry, ItemTemplate template) {
        return new Wand(
                stats, 
                ItemsFactory.getEffects(template, "Pa"), 
                WeaponData.getWeaponDataByTemplate(template), 
                entry, 
                template
        );
    }
    
}
