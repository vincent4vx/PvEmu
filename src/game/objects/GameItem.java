package game.objects;

import game.objects.dep.ItemStats;
import models.Inventory;

public class GameItem {
    private Inventory _inventory;
    private ItemStats _itemStats;
    private int id;
    
    public GameItem(Inventory I){
        _inventory = I;
        _itemStats = I.getItemStats();
        id = I.id;
    }
    
    public GameItem(Player owner, ItemStats stats, int qu){
        _inventory = new Inventory();
        _inventory.item_id = stats.getID();
        _inventory.owner = owner.getID();
        _inventory.owner_type = 1;
        _inventory.position = -1;
        _inventory.stats = stats.statsToString();
    }
    
    public ItemStats getItemStats(){
        return _itemStats;
    }
    
    public Inventory getInventory(){
        return _inventory;
    }
    
    /**
     * Retourne de l'id DANS l'inventaire.
     * Utilisez getItemStats().getID(); pour avoir l'id de l'item
     * @return 
     */
    public int getID(){
        return id;
    }
    
    @Override
    public String toString(){
        StringBuilder ret = new StringBuilder();
        
        ret.append(Integer.toHexString(id)).append('~').append(Integer.toHexString(_inventory.item_id)).append('~')
                .append(Integer.toHexString(_inventory.qu)).append('~').append(_inventory.position == -1 ? "" : Integer.toHexString(_inventory.position))
                .append('~').append(_inventory.stats);
        
        return ret.toString();
    }
}
