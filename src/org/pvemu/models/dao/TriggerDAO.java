package org.pvemu.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public ArrayList<Trigger> getByMapID(int mapID) {
        ArrayList<Trigger> triggers = new ArrayList<>();
        
        ReservedQuery query = getByMapID.reserveQuery();
        try {
            query.getStatement().setInt(1, mapID);

            ResultSet RS = query.getStatement().executeQuery();

            while (RS.next()) {
                Trigger t = createByResultSet(RS);
                if (t != null) {
                    triggers.add(t);
                }
            }
        } catch (SQLException ex) {
            Loggin.error("Cannot load triggers on map " + mapID, ex);
        }finally{
            query.release();
        }

        return triggers;
    }
}
