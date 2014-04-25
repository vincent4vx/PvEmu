package org.pvemu.models;

import org.pvemu.jelly.database.Model;

public class Experience implements Model{
    public short lvl;
    public long player;
    public long job;
    public long honnor;

    @Override
    public int getPk() {
        return lvl;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
