/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class CreatableDAO<T extends Model> extends FindableDAO<T> {
    final protected PreparedStatement deleteStatement = Database.prepare("SELECT * FROM " + tableName() + " WHERE " + primaryKey() + " = ?");
    
    abstract public boolean create(T obj);
    
    public boolean delete(int pk) {
        try {
            if (primaryKey().isEmpty()) {
                return false;
            }

            synchronized (deleteStatement) {
                deleteStatement.setInt(1, pk);
                return deleteStatement.execute();
            }
        } catch (SQLException e) {
            return false;
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
