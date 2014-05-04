package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.DAO;
import org.pvemu.jelly.database.DatabaseHandler;
import org.pvemu.jelly.database.Query;
import org.pvemu.jelly.database.ReservedQuery;
import org.pvemu.models.Trigger;

public class TriggerDAO extends DAO<Trigger> {

    final private Query getByMapID = DatabaseHandler.instance().prepareQuery("SELECT * FROM triggers WHERE MapID = ?");

    @Override
    protected String tableName() {
        return "triggers";
    }

    @Override
    protected Trigger createByResultSet(ResultSet RS) {
        try {
            Trigger t = new Trigger();

            t.mapID = RS.getShort("MapID");
            t.cellID = RS.getShort("CellID");
            t.actionID = RS.getShort("ActionID");
            t.actionArgs = RS.getString("ActionArgs");
            t.conditions = RS.getString("Conditions");

            return t;
        } catch (Exception e) {
            Loggin.error("Impossible de charger le trigger !", e);
            return null;
        }
    }

    public Map<Short, List<Trigger>> getByMapID(int mapID) {
        Map<Short, List<Trigger>> triggers = new HashMap<>();
        
        ReservedQuery query = getByMapID.reserveQuery();
        try {
            query.getStatement().setInt(1, mapID);

            ResultSet RS = query.getStatement().executeQuery();

            while (RS.next()) {
                Trigger t = createByResultSet(RS);
                if (t != null) {
                    List<Trigger> list = triggers.get(t.cellID);
                    
                    if(list == null){
                        list = new ArrayList<>();
                        triggers.put(t.cellID, list);
                    }
                    
                    list.add(t);
                }
            }
        } catch (SQLException ex) {
            Loggin.error("Cannot load triggers on map " + mapID, ex);
        }finally{
            query.release();
        }

        return triggers;
    }
    
    public Map<Short, Map<Short, List<Trigger>>> getAll(){
        try {
            ResultSet RS = DatabaseHandler.instance().executeQuery("SELECT * FROM triggers");
            Map<Short, Map<Short, List<Trigger>>> triggers = new HashMap<>();
            
            while(RS.next()){
                Trigger t = createByResultSet(RS);
                if (t != null) {
                    Map<Short, List<Trigger>> map = triggers.get(t.mapID);
                    
                    if(map == null){
                        map = new HashMap<>();
                        triggers.put(t.mapID, map);
                    }
                    
                    List<Trigger> cell = map.get(t.cellID);
                    
                    if(cell == null){
                        cell = new ArrayList<>();
                        map.put(t.cellID, cell);
                    }
                    
                    cell.add(t);
                }
            }
            
            return triggers;
        } catch (SQLException ex) {
            Loggin.error("Cannot load triggers", ex);
            return null;
        }
    }
}
