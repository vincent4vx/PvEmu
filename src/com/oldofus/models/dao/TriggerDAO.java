package com.oldofus.models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.oldofus.jelly.Loggin;
import com.oldofus.jelly.database.DAO;
import com.oldofus.jelly.database.Database;
import com.oldofus.models.Trigger;

public class TriggerDAO extends DAO<Trigger> {

    private PreparedStatement getByMapIDStatement = null;
    
    public TriggerDAO(){
        getByMapIDStatement = Database.prepare("SELECT * FROM triggers WHERE MapID = ?");
    }

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

    public ArrayList<Trigger> getByMapID(int mapID) {
        ArrayList<Trigger> triggers = new ArrayList<>();
        try {
            getByMapIDStatement.setInt(1, mapID);

            ResultSet RS = getByMapIDStatement.executeQuery();

            while (RS.next()) {
                Trigger t = createByResultSet(RS);
                if (t != null) {
                    triggers.add(t);
                }
            }
        } catch (SQLException ex) {
            Loggin.error("Erreur lors du chargement des triggers de la map " + mapID, ex);
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
