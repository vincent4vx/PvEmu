package models;

import game.objects.dep.ItemStats;
import jelly.database.Model;

public class ItemTemplate implements Model {
    public int id;
    public byte type;
    public String name;
    public int level;
    public String statsTemplate;
    public int pods;
    public int itemset;
    public int cost;
    public String condition;
    public String weaponData;
    
    public ItemStats createItem(){
        return new ItemStats(this);
    }
    
    public ItemStats createItem(boolean max){
        return new ItemStats(this, max);
    }

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        id = 0;
    }
    
}
