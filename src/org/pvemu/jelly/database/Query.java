/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.database;

import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Query {
    final private String sql;
    final private DatabaseHandler handler;

    public Query(String sql, DatabaseHandler handler) {
        this.sql = sql;
        this.handler = handler;
    }

    public String getSql() {
        return sql;
    }

    public DatabaseHandler getHandler() {
        return handler;
    }
    
    public ReservedQuery reserveQuery(){
        Loggin.debug("Reserving query for %s", toString());
        DatabaseConnection connexion = handler.getFreeConnection();
        return new ReservedQuery(this, connexion.getPreparedStatement(this), connexion);
    }

    @Override
    public String toString() {
        return "Query{" + "sql=" + sql + '}';
    }
    
}
