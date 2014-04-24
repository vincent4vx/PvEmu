/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item.types;

import java.util.Set;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.effect.EffectData;
import org.pvemu.game.effect.Effectsable;
import org.pvemu.game.objects.item.WeaponData;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Weapon extends Accessorie implements Effectsable{
    private final Set<EffectData> effects;
    private final WeaponData weaponData;
    

    public Weapon(Stats stats, Set<EffectData> effects, WeaponData weaponData, InventoryEntry entry, ItemTemplate template) {
        super(stats, entry, template);
        this.effects = effects;
        this.weaponData = weaponData;
    }

    /**
     * Get the value of weaponData
     *
     * @return the value of weaponData
     */
    public WeaponData getWeaponData() {
        return weaponData;
    }

    @Override
    public Set<EffectData> getEffects() {
        return effects;
    }
    
}
