package org.pvemu.models;

import org.pvemu.jelly.database.Model;

public class MapModel implements Model {

    public short id;
    public String date;
    public byte width, heigth;
    public String places;
    public String key;
    public String mapData;
    public String cells;
    public String monsters;
    public byte capabilities;
    public String mappos;
    public byte numgroup, groupmaxsize;

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
    }
}
