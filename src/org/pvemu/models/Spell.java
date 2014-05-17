package org.pvemu.models;

import org.pvemu.common.database.Model;

public class Spell implements Model {

    public int id;
    public String name;
    public int sprite;
    public String spriteInfos;
    public String lvl1, lvl2, lvl3, lvl4, lvl5, lvl6;
    public String effectTarget;

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        id = 0;
    }
}
