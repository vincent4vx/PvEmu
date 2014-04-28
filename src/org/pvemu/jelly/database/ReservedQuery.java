/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pvemu.jelly.Loggin;

/**
 *
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

    public Query getQuery() {
        return query;
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    public DatabaseConnection getConnection() {
        return connection;
    }
    
    public void release(){
        try {
            statement.clearParameters();
        } catch (SQLException ex) {}
        query.getHandler().returnConnection(connection);
        Loggin.debug("%s releasing after %d ms", query, System.currentTimeMillis() - startTime);
    }
}
