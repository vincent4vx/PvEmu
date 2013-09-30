package com.oldofus.game.objects.inventory;


public interface InventoryAble {
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
    public Integer getID();
    
    public void onQuantityChange(int id, int qu);
    public void onAddItem(GameItem GI);
    public void onDeleteItem(int id);
    public void onMoveItemSuccess(GameItem GI, byte pos);
    public void onMoveItem(int id, byte pos);
    public boolean canMoveItem(GameItem GI, int qu, byte pos);
}
