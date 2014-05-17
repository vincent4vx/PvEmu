/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.pvemu.common.Loggin;

/**
 * @see Query#reserveQuery() 
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ReservedQuery {
    final private Query query;
    final private PreparedStatement statement;
    final private DatabaseConnection connection;
    final private long startTime = System.currentTimeMillis();

    public ReservedQuery(Query query, PreparedStatement statement, DatabaseConnection connection) {
        this.query = query;
        this.statement = statement;
        this.connection = connection;
    }

    /**
     * Get the query object
     * @return 
     */
    public Query getQuery() {
        return query;
    }

    /**
     * Get the current prepared statement
     * @return 
     */
    public PreparedStatement getStatement() {
        return statement;
    }

    /**
     * Get the current connexion
     * @return 
     */
    public DatabaseConnection getConnection() {
        return connection;
    }
    
    /**
     * release this statement
     * @see DatabaseHandler#returnConnection(org.pvemu.jelly.database.DatabaseConnection) 
     */
    public void release(){
        try {
            statement.clearParameters();
        } catch (SQLException ex) {}
        query.getHandler().returnConnection(connection);
        Loggin.debug("%s releasing after %d ms", query, System.currentTimeMillis() - startTime);
    }
}