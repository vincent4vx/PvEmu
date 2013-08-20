package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jelly.database.DAO;
import jelly.database.Database;
import models.Trigger;

public class TriggerDAO extends DAO<Trigger> {
    
    private PreparedStatement getByMapIDStatement = null;

    @Override
    protected String tableName() {
        return "triggers";
    }

    @Override
    protected Trigger createByResultSet(ResultSet RS) {
        try{
            Trigger t = new Trigger();
            
            t.mapID = RS.getInt("MapID");
            t.cellID = RS.getInt("CellID");
            t.actionID = RS.getInt("ActionID");
            t.actionArgs = RS.getString("ActionArgs");
            t.conditions = RS.getString("Conditions");
            
            return t;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<Trigger> getByMapID(int mapID){
        if(getByMapIDStatement == null){
            getByMapIDStatement = Database.prepare("SELECT * FROM triggers WHERE MapID = ?");
        }
        
        ArrayList<Trigger> triggers = new ArrayList<>();
        try {
            getByMapIDStatement.setInt(1, mapID);
            
            ResultSet RS = getByMapIDStatement.executeQuery();
            
            while(RS.next()){
                Trigger t = createByResultSet(RS);
                if(t != null){
                    triggers.add(t);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TriggerDAO.class.getName()).log(Level.WARNING, "Impossible de charger les triggers", ex);
        }       
        
        return triggers;
    }

    @Override
    public boolean update(Trigger obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean create(Trigger obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
