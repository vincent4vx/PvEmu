package models;

import game.objects.GameItem;
import game.objects.dep.ItemStats;
import jelly.database.Model;

public class Inventory implements Model {
    public int id;
    public int item_id;
    public byte position;
    public int owner;
    public byte owner_type;
    public String stats;
    public int qu;
    
    public static final byte OWNER_PLAYER = 1;
    
    /**
     * génère l'item
     * @return 
     */
    public ItemStats getItemStats(){
        return new ItemStats(this);
    }
    
    /**
     * Génère l'item utilisable IG
     * @return 
     */
    public GameItem getGameItem(){
        return new GameItem(this);
    }
    
    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
