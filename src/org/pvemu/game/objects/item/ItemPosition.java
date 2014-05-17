/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.item;

import java.util.HashMap;
import org.pvemu.game.objects.item.types.*;
import org.pvemu.common.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public enum ItemPosition {
    NOT_EQUIPED(new byte[]{-1}, new Class[]{GameItem.class}, true, false),
    AMULET(new byte[]{0}, new Class[]{Amulet.class}, false, true),
    WEAPON(new byte[]{1}, new Class[]{Weapon.class}, false, true),
    RING(new byte[]{2, 4}, new Class[]{Ring.class}, false, true),
    BELT(new byte[]{3}, new Class[]{Belt.class}, false, true),
    BOOTS(new byte[]{5}, new Class[]{Boots.class}, false, true),
    HELMET(new byte[]{6}, new Class[]{Helmet.class}, false, true),
    MANTLE(new byte[]{7}, new Class[]{Mantle.class}, false, true),
    PET(new byte[]{8}, new Class[]{Pet.class}, false, true),
    DOFUS(new byte[]{9, 10, 11, 12, 13, 14}, new Class[]{Dofus.class}, false, true),
    SHIELD(new byte[]{15}, new Class[]{Shield.class}, false, true),
    MOUNT(new byte[]{16}, new Class[]{}, false, true),
    CANDY(new byte[]{20, 21, 22, 23, 24, 25, 26, 27}, new Class[]{}, false, false),
    QUICKBAR(new byte[]{35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48}, new Class[]{}, false, false),
    ;
    
    final static private HashMap<Byte, ItemPosition> posById = new HashMap<>();
    final static public byte DEFAULT_POSITION = -1;
    
    static{
        for(ItemPosition pos : ItemPosition.values()){
            for(byte id : pos.posIds){
                posById.put(id, pos);
            }
        }
    }
    
    final private byte[] posIds;
    final private Class<? extends GameItem>[] itemsAvailable;
    final private boolean isMultiplePlace;
    final private boolean isWearPlace;

    private ItemPosition(byte[] posIds, Class<? extends GameItem>[] itemsAvailable, boolean isMultiplePlace, boolean isWearPlace) {
        this.posIds = posIds;
        this.itemsAvailable = itemsAvailable;
        this.isMultiplePlace = isMultiplePlace;
        this.isWearPlace = isWearPlace;
    }

    public byte[] getPosIds() {
        return posIds;
    }

    public Class<? extends GameItem>[] getItemsAvailable() {
        return itemsAvailable;
    }

    public boolean isMultiplePlace() {
        return isMultiplePlace;
    }

    public boolean isWearPlace() {
        return isWearPlace;
    }
    
    public boolean isValidPosition(GameItem item){
        for(Class c : itemsAvailable){
            if(c.isInstance(item))
                return true;
        }
        
        return false;
    }
    
    static public ItemPosition getByPosID(final byte posID){
        ItemPosition pos = posById.get(posID);
        
        if(pos == null){
            Loggin.debug("Position %d non trouvée", posID);
            return ItemPosition.NOT_EQUIPED;
        }
        
        return pos;
    }
    
    final static private ItemPosition[] accessoriePositions = new ItemPosition[]{
            ItemPosition.WEAPON,
            ItemPosition.HELMET,
            ItemPosition.MANTLE,
            ItemPosition.PET,
            ItemPosition.SHIELD
    };
    
    static public ItemPosition[] getAccessoriePositions(){
        return accessoriePositions;
    }
}
