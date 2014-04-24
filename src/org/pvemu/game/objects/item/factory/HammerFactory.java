package org.pvemu.game.objects.item.factory;

import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.WeaponData;
import org.pvemu.game.objects.item.types.Hammer;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class HammerFactory implements ItemFactoryInterface{

    @Override
    public GameItem newItem(Stats stats, InventoryEntry entry, ItemTemplate template) {
        return new Hammer(
                stats, 
                ItemsFactory.getEffects(template, "Cb"), 
                WeaponData.getWeaponDataByTemplate(template), 
                entry, 
                template
        );
    }
    
}
