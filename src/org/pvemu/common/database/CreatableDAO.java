/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.database;

import java.sql.SQLException;
import org.pvemu.common.Loggin;

/**
 * a DAO witch can create and delete a record
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 * @param <T> the generated model
 */
abstract public class CreatableDAO<T extends Model> extends FindableDAO<T> {
    final protected Query delete = DatabaseHandler.instance().prepareQuery("DELETE FROM " + tableName() + " WHERE " + primaryKey() + " = ?");
    
    /**
     * Insert into db the record
     * @param obj the record to insert
     * @return 
     */
    abstract public boolean create(T obj);
    
    /**
     * Delete a record from db
     * @param pk the the primary key of the record
     * @return true on success
     */
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

    /**
     * Delete a model object and his record into db
     * @param obj the model to delete
     * @return true on success
     */
    public boolean delete(T obj) {
        if (delete(obj.getPk())) {
            obj.clear();
            return true;
        } else {
            return false;
        }
    }
}
