package org.pvemu.models;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.Model;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.dao.DAOFactory;

public class MapModel implements Model {

    public short id;
    public String date;
    public byte width, heigth;
    public String places;
    public String key;
    public String mapData;
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
