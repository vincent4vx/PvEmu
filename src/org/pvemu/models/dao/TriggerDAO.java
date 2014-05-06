package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.database.DAO;
import org.pvemu.jelly.database.DatabaseHandler;
import org.pvemu.jelly.database.Query;
import org.pvemu.jelly.database.ReservedQuery;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.models.Trigger;

public class TriggerDAO extends DAO<Trigger> {

    final private Query getByCell = DatabaseHandler.instance().prepareQuery("SELECT * FROM triggers WHERE MapID = ? AND CellID = ?");

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
    
    public List<Trigger> getByCell(Pair<Short, Short> position){
        List<Trigger> triggers = new ArrayList<>();
        ReservedQuery query = getByCell.reserveQuery();
        try{
            query.getStatement().setShort(1, position.getFirst());
            query.getStatement().setShort(2, position.getSecond());
            
            ResultSet RS = query.getStatement().executeQuery();
            
            while(RS.next()){
                Trigger t = createByResultSet(RS);
                
                if(t != null){
                    triggers.add(t);
                }
            }
        }catch(SQLException e){
            Loggin.error("Cannot load trigger at " + position, e);
        }finally{
            query.release();
        }
        return triggers;
    }
    
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
}
