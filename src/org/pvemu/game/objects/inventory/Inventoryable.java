package org.pvemu.game.objects.inventory;



public interface Inventoryable {
    /**
     * Retourne l'inventaire du GameObject
     * @return 
     */
    public Inventory getInventory();
    
    /**
     * Retourne le type du propriétaire (1 = player)
     * @return 
     */
    public byte getOwnerType();
    public int getID();
}
