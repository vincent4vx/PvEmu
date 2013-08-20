package jelly.database;

import jelly.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import jelly.Loggin;

public class Database {
    public Connection db;
    private static Database self = null;
    private Timer _commitTimer;
    private boolean _autocommit = false;

    private Database(){
        try {
            System.out.print("Connexion à la base de données : ");
            StringBuilder dsn = new StringBuilder();

            dsn.append("jdbc:mysql://");
            dsn.append(Config.getString("db_host"));
            dsn.append("/").append(Config.getString("db_name"));
            
            db = DriverManager.getConnection(
                    dsn.toString(),
                    Config.getString("db_user"),
                    Config.getString("db_pass")
            );
   
            _commitTimer = new Timer();
            System.out.println("Ok");
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Connexion impossible", ex);
            System.exit(1);
        }
    }
    
    public static void setAutocommit(boolean state){
        self._autocommit = state;
        try {
            self.db.setAutoCommit(state);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(state){
            self._commitTimer.cancel();
        }else{    
            self._commitTimer.schedule(new TimerTask(){
                @Override
                public void run(){
                    if(!self._autocommit){
                        try {
                            self.db.commit();
                            Loggin.debug("Commit Database");
                        } catch (SQLException ex) {
                            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }, Config.getInt("db_commit_time", 60) * 1000, Config.getInt("db_commit_time", 60) * 1000);
        }
    }

    public static ResultSet query(String query){
        try{
            ResultSet RS;
            synchronized(self){
                RS = self.db.createStatement().executeQuery(query);
                Loggin.debug("Execution de la requête : %s", new Object[]{query});
            }
            return RS;
        }catch(SQLException e){
            return null;
        }
    }

    public static PreparedStatement prepare(String query){
        try{
            PreparedStatement stmt = self.db.prepareStatement(query);
            Loggin.debug("Préparation de la requête : %s", new Object[]{query});
            return stmt;
        }catch(SQLException e){
            return null;
        }
    }
    
    /**
     * Prépare une requête d'insertion (retourne l'id généré)
     * @param query
     * @return 
     */
    public static PreparedStatement prepareInsert(String query){
        try{
            PreparedStatement stmt = self.db.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            return stmt;
        }catch(SQLException e){
            return null;
        }    
    }
    
    public static void connect(){
        if(self == null){
            self = new Database();
            setAutocommit(false);
        }
    }
    
    public static void close(){
        try {
            System.out.print("Arrêt de database : ");
            setAutocommit(false);
            self.db.commit();
            self._commitTimer.cancel();
            self._commitTimer = null;
            self.db.close();
            System.out.println("ok");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
