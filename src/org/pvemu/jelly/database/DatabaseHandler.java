package org.pvemu.jelly.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pvemu.jelly.Config;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Shell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DatabaseHandler {
    final private Set<DatabaseConnection> connexions = new HashSet<>();
    final private ArrayBlockingQueue<DatabaseConnection> pool;
    final private int POOL_SIZE;
    
    static private DatabaseHandler instance = null;

    private DatabaseHandler(String host, String user, String pass, String dbname, int poolSize) throws SQLException {
        POOL_SIZE = poolSize;
        pool = new ArrayBlockingQueue<>(POOL_SIZE);
        for(int i = 0; i < POOL_SIZE; ++i){
            DatabaseConnection connexion = new DatabaseConnection(host, user, pass, dbname);
            pool.add(connexion);
            connexions.add(connexion);
        }
    }
    
    DatabaseConnection getFreeConnection(){
        try {
            return pool.take();
        } catch (InterruptedException ex) {
            return null;
        }
    }
    
    void returnConnection(DatabaseConnection connexion){
        pool.add(connexion);
    }
    
    synchronized public Query prepareQuery(String sql){
         Query query = new Query(sql, this);
         
         for(DatabaseConnection connexion : connexions){
             try {
                 connexion.prepare(query);
             } catch (SQLException ex) {
                 Loggin.error("Cannot prepare query " + query, ex);
                 System.exit(1);
             }
         }
         
         return query;
    }
    
    synchronized public Query prepareInsert(String sql){
         Query query = new Query(sql, this);
         
         for(DatabaseConnection connexion : connexions){
             try {
                 connexion.prepareInsert(query);
             } catch (SQLException ex) {
                 Loggin.error("Cannot prepare query " + query, ex);
                 System.exit(1);
             }
         }
         
         return query;
    }
    
    public ResultSet executeQuery(String sql) throws SQLException{
        try {
            DatabaseConnection connection = pool.take();
            try{
                return connection.query(sql);
            }finally{
                pool.add(connection);
            }
        } catch (InterruptedException ex) {
            return null;
        }
    }
        
    static public void init(){
        if(instance != null){
            Loggin.warning("DatabaseHandler already initialized !");
            return;
        }
        Shell.print("Database initialization : ", Shell.GraphicRenditionEnum.YELLOW);
        try {
            instance = new DatabaseHandler(
                    Config.DB_HOST.getValue(),
                    Config.DB_USER.getValue(),
                    Config.DB_PASS.getValue(),
                    Config.DB_NAME.getValue(),
                    Config.DB_POOL_SIZE.getValue()
            );
        } catch (SQLException ex) {
            Loggin.error("Cannot connect to database", ex);
            System.exit(1);
        }
        Shell.println("Ok !", Shell.GraphicRenditionEnum.GREEN);
    }
    
    static public DatabaseHandler instance(){
        return instance;
    }
    
    synchronized public void close(){
        for(DatabaseConnection connection : connexions){
            connection.close();
        }
    }
}
