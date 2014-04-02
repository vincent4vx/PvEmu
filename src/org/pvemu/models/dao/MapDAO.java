package org.pvemu.models.dao;

import java.sql.ResultSet;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.FindableDAO;
import org.pvemu.models.MapModel;

public class MapDAO extends FindableDAO<MapModel> {

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
            
            if(Constants.DOFUS_VER_ID >= 1100){
                map.key = RS.getString("key");
            }
            
            map.mapData = RS.getString("mapData");
            map.monsters = RS.getString("monsters");
            //map.capabilities = RS.getByte("capabilities");
            map.mappos = RS.getString("mappos");
            map.numgroup = RS.getByte("numgroup");
            map.groupmaxsize = RS.getByte("groupmaxsize");

            return map;
        } catch (Exception e) {
            Loggin.error("Impossible de charger la map !", e);
            return null;
        }
    }
}
