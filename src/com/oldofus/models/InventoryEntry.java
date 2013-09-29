package com.oldofus.models;

import com.oldofus.game.objects.inventory.GameItem;
import com.oldofus.game.objects.inventory.ItemStats;
import com.oldofus.jelly.database.Model;

public class InventoryEntry implements Model {
    public int id;
    public int item_id;
    public byte position;
    public int owner;
    public byte owner_type;
    public String stats;
    public int qu;
    
    public static final byte OWNER_PLAYER = 1;
    
    private GameItem gameItem = null;
    private ItemStats itemStats = null;
    
    /**
     * génère l'item
     * @return 
     */
    public ItemStats getItemStats(){
        if(itemStats == null){
            itemStats = new ItemStats(this);
        }
        return itemStats;
    }
    
    /**
     * Génère l'item utilisable IG
     * @return 
     */
    public GameItem getGameItem(){
        if(gameItem == null){
            gameItem = new GameItem(this);
        }
        return gameItem;
    }
    
    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        id = 0;
        item_id = 0;
        position = -1;
        owner = 0;
        owner_type = 0;
        stats = null;
        qu = 0;
    }
    
}
