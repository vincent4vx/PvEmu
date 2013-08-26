package jelly.database;

import game.World;
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
import jelly.Shell;
import jelly.Shell.GraphicRenditionEnum;

public class Database {

    public Connection db;
    private static Database self = null;
    private Timer _commitTimer;
    private boolean _autocommit = false;

    private Database() {
        try {
            Shell.print("Connexion à la base de données : ", GraphicRenditionEnum.YELLOW);
            StringBuilder dsn = new StringBuilder();

            dsn.append("jdbc:mysql://");
            dsn.append(Config.getString("db_host"));
            dsn.append("/").append(Config.getString("db_name"));

            db = DriverManager.getConnection(
                    dsn.toString(),
                    Config.getString("db_user"),
                    Config.getString("db_pass"));

            _commitTimer = new Timer();
            Shell.println("Ok", GraphicRenditionEnum.GREEN);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, "Connexion impossible", ex);
            System.exit(1);
        }
    }

    public static void setAutocommit(boolean state) {
        self._autocommit = state;
        try {
            if (state) {
                Loggin.debug("Commit Database");
                self.db.commit();
            }
            self.db.setAutoCommit(state);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (self._commitTimer == null) {
            self._commitTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!self._autocommit) {
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

    public static ResultSet query(String query) {
        try {
            ResultSet RS;
            synchronized (self) {
                RS = self.db.createStatement().executeQuery(query);
                Loggin.debug("Execution de la requête : %s", new Object[]{query});
            }
            return RS;
        } catch (SQLException e) {
            return null;
        }
    }

    public static PreparedStatement prepare(String query) {
        try {
            PreparedStatement stmt = self.db.prepareStatement(query);
            Loggin.debug("Préparation de la requête : %s", query);
            return stmt;
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Prépare une requête d'insertion (retourne l'id généré)
     *
     * @param query
     * @return
     */
    public static PreparedStatement prepareInsert(String query) {
        try {
            PreparedStatement stmt = self.db.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            return stmt;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void connect() {
        if (self == null) {
            self = new Database();
            setAutocommit(false);
        }
    }

    public static void close() {
        try {
            Shell.print("Arrêt de database : ", GraphicRenditionEnum.RED);
            self._commitTimer.cancel();
            self._commitTimer = null;
            self.db.close();
            Shell.println("ok", GraphicRenditionEnum.GREEN);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
