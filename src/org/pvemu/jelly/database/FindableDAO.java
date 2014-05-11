/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.pvemu.jelly.Loggin;

/**
 * The DAO for findable objects (SELECT queries)
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 * @param <T> the generated model
 */
abstract public class FindableDAO<T extends Model> extends DAO<T> {
    
    final protected Query find = DatabaseHandler.instance().prepareQuery("SELECT * FROM " + tableName() + " WHERE " + primaryKey() + " = ?");

    /**
     * find an element by his primary key
     * @param pk the value of the primary key
     * @return the generated object or null if not found
     */
    public T find(int pk) {
        if (primaryKey().isEmpty()) {
            return null;
        }

        ReservedQuery query = find.reserveQuery();
        
        try {
                query.getStatement().setInt(1, pk);
                ResultSet RS = query.getStatement().executeQuery();

                if (!RS.next()) {
                    Loggin.debug("Impossible de trouver la pk %d dans la table %s", pk, tableName());
                    return null;
                }

                return createByResultSet(RS);
        } catch (SQLException e) {
            Loggin.error("Cannot execute query " + find, e);
            return null;
        }finally{
            query.release();
        }
    }

    /**
     * Get all the rows of the table
     * @return list contening of the rows
     */
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM " + tableName();
        
        try {
            ResultSet RS = DatabaseHandler.instance().executeQuery(query);
            while (RS.next()) {
                list.add(createByResultSet(RS));
            }
        } catch (SQLException ex) {
            Loggin.error("Cannot execute query " + query, ex);
        }

        return list;
    }
}
