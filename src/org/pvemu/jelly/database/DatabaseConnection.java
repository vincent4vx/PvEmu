/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Handle one connexion
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DatabaseConnection {
    final private Connection connection;
    final private Map<Query, PreparedStatement> statements = new HashMap<>();

    public DatabaseConnection(String host, String user, String pass, String dbname) throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + "/" + dbname,
                user,
                pass
        );
        connection.setAutoCommit(true);
    }
    
    /**
     * Execute a query
     * @param query the SQL query
     * @return the result
     * @throws SQLException 
     */
    synchronized public ResultSet query(String query) throws SQLException{
        return connection.createStatement().executeQuery(query);
    }
    
    /**
     * Prepare a query
     * @param query the SQL query
     * @throws SQLException 
     */
    synchronized public void prepare(Query query) throws SQLException{
        statements.put(query, connection.prepareStatement(query.getSql()));
    }
    
    /**
     * Prepare a query for insert (and get generated keys)
     * @param query the SQL query
     * @throws SQLException 
     */
    synchronized public void prepareInsert(Query query) throws SQLException{
        statements.put(query, connection.prepareStatement(query.getSql(), PreparedStatement.RETURN_GENERATED_KEYS));
    }
    
    /**
     * Get the prepared statement for a query
     * @param query the query
     * @return the prepared statement if exists
     */
    synchronized public PreparedStatement getPreparedStatement(Query query){
        return statements.get(query);
    }
    
    /**
     * Close the connexion to DB
     */
    synchronized public void close(){
        try{
            for(PreparedStatement statement : statements.values()){
                statement.close();
            }
            statements.clear();

            connection.close();
        }catch(SQLException e){}
    }
}
