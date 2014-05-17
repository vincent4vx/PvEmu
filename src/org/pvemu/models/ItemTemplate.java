package org.pvemu.models;

//import org.pvemu.game.objects.item.ItemStats;
import org.pvemu.common.database.Model;

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

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        id = 0;
    }

    @Override
    public String toString() {
        return "ItemTemplate{" + "id=" + id + ", type=" + type + ", name=" + name + ", level=" + level + ", statsTemplate=" + statsTemplate + ", pods=" + pods + ", itemset=" + itemset + ", cost=" + cost + ", condition=" + condition + ", weaponData=" + weaponData + '}';
    }
    
}
