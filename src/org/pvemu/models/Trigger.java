package org.pvemu.models;

import org.pvemu.common.database.Model;

public class Trigger implements Model {

    public short mapID, cellID, actionID;
    public String actionArgs, conditions;
    
    @Override
    public int getPk() {
        return 0;
    }

    @Override
    public void clear() {
    }
}
