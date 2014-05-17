package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.common.Loggin;
import org.pvemu.common.database.DAO;
import org.pvemu.common.database.DatabaseHandler;
import org.pvemu.common.database.Query;
import org.pvemu.common.database.ReservedQuery;
import org.pvemu.common.utils.Pair;
import org.pvemu.models.Trigger;

public class TriggerDAO extends DAO<Trigger> {

    final private Query getByMap = DatabaseHandler.instance().prepareQuery("SELECT * FROM triggers WHERE MapID = ?");

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
    
    /**
     * Load all triggers
     * @return 
     */
    public Map<Pair<Short, Short>, List<Trigger>> getAll(){
        try {
            ResultSet RS = DatabaseHandler.instance().executeQuery("SELECT * FROM triggers");
            Map<Pair<Short, Short>, List<Trigger>> triggers = new HashMap<>();
            
            while(RS.next()){
                Trigger t = createByResultSet(RS);
                if (t != null) {
                    
                    List<Trigger> cell = triggers.get(new Pair<>(t.mapID, t.cellID));
                    
                    if(cell == null){
                        cell = new ArrayList<>();
                        triggers.put(new Pair<>(t.mapID, t.cellID), cell);
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
    
    /**
     * get trigger list by map
     * @param map
     * @return 
     */
    public Map<Short, List<Trigger>> getByMap(short map){
        ReservedQuery query = getByMap.reserveQuery();
        try {
            query.getStatement().setShort(1, map);
            ResultSet RS = query.getStatement().executeQuery();
            Map<Short, List<Trigger>> triggers = new HashMap<>();
            
            while(RS.next()){
                Trigger t = createByResultSet(RS);
                if (t != null) {
                    
                    List<Trigger> cell = triggers.get(t.cellID);
                    
                    if(cell == null){
                        cell = new ArrayList<>();
                        triggers.put(t.cellID, cell);
                    }
                    
                    cell.add(t);
                }
            }
            
            return triggers;
        } catch (SQLException ex) {
            Loggin.error("Cannot load triggers", ex);
            return null;
        }finally{
            query.release();
        }
    }
}
