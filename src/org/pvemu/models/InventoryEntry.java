package org.pvemu.models;

import org.pvemu.jelly.database.Model;

public class InventoryEntry implements Model {
    
    public static final byte OWNER_PLAYER = 1;
    final static public int NOT_CREATED = -1;
    
    public int id = NOT_CREATED;
    public int item_id;
    public byte position;
    public int owner;
    public byte owner_type;
    public String stats;
    public int qu;
    
    public boolean isCreated(){
        return id != NOT_CREATED;
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
