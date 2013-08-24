package models.dao;

import java.sql.ResultSet;
import java.util.HashMap;
import jelly.database.DAO;
import models.MapModel;

public class MapDAO extends DAO<MapModel> {

    private HashMap<Short, MapModel> mapById = new HashMap<>();

    @Override
    protected String tableName() {
        return "maps";
    }

    @Override
    protected MapModel createByResultSet(ResultSet RS) {
        try {
            MapModel map = new MapModel();

            map.id = RS.getShort("id");
            map.date = RS.getString("date");
            map.width = RS.getByte("width");
            map.heigth = RS.getByte("heigth");
            map.places = RS.getString("places");
            map.key = RS.getString("key");
            map.mapData = RS.getString("mapData");
            map.monsters = RS.getString("monsters");
            //map.capabilities = RS.getByte("capabilities");
            map.mappos = RS.getString("mappos");
            map.numgroup = RS.getByte("numgroup");
            map.groupmaxsize = RS.getByte("groupmaxsize");

            mapById.put(map.id, map);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(MapModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(MapModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * A utiliser à la place de find
     *
     * @param id
     * @return
     */
    public MapModel getById(short id) {
        if (!mapById.containsKey(id)) {
            return find(id);
        }
        return mapById.get(id);
    }
}
