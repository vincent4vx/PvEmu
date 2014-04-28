/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class CreatableDAO<T extends Model> extends FindableDAO<T> {
    final protected Query delete = DatabaseHandler.instance().prepareQuery("DELETE FROM " + tableName() + " WHERE " + primaryKey() + " = ?");
    
    abstract public boolean create(T obj);
    
    public boolean delete(int pk) {
        ReservedQuery query = delete.reserveQuery();
        try {
            if (primaryKey().isEmpty()) {
                return false;
            }

            query.getStatement().setInt(1, pk);
            return query.getStatement().execute();
        } catch (SQLException e) {
            Loggin.error("Cannot execute query " + delete, e);
            return false;
        }finally{
            query.release();
        }
    }

    public boolean delete(T obj) {
        if (delete(obj.getPk())) {
            obj.clear();
            return true;
        } else {
            return false;
        }
    }
}
