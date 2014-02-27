package org.pvemu.jelly.database;

import org.pvemu.jelly.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Shell;
import org.pvemu.jelly.Shell.GraphicRenditionEnum;

public class Database {

    public Connection db;
    private static Database self = null;
    private ScheduledExecutorService scheduledCommit;
    private boolean _autocommit = false;

    private Database() {
        try {
            Shell.print("Connexion à la base de données : ", GraphicRenditionEnum.YELLOW);
            StringBuilder dsn = new StringBuilder();

            dsn.append("jdbc:mysql://");
            dsn.append(Config.DB_HOST.getValue());
            dsn.append("/").append(Config.DB_NAME.getValue());

            db = DriverManager.getConnection(
                    dsn.toString(),
                    Config.DB_USER.getValue(),
                    Config.DB_PASS.getValue());

            scheduledCommit = Executors.newSingleThreadScheduledExecutor();
            scheduledCommit.scheduleAtFixedRate(
                    new Runnable() {
                        @Override
                        public void run() {
                            if (!self._autocommit) {
                                try {
                                    synchronized(self.db){
                                        self.db.commit();
                                    }
                                    Loggin.debug("Commit Database");
                                } catch (SQLException ex) {
                                    Loggin.error("Commit impossible !", ex);
                                }
                            }
                        }
                    },
                    Config.DB_COMMIT_TIME.getValue(),
                    Config.DB_COMMIT_TIME.getValue(), TimeUnit.SECONDS
            );
            Shell.println("Ok", GraphicRenditionEnum.GREEN);
        } catch (SQLException ex) {
            Loggin.error("Connexion à la base de donnée impossible.", ex);
            System.exit(1);
        }
    }

    public static void setAutocommit(boolean state) {
        self._autocommit = state;
        try {
            synchronized(self.db){
                if (state) {
                    Loggin.debug("Commit Database");
                    self.db.commit();
                }
                self.db.setAutoCommit(state);
            }
        } catch (SQLException ex) {
            Loggin.error("Impossible de changer le mode autoCommit !", ex);
            System.exit(1);
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
            Loggin.error("exécution de la requête '" + query + "' impossible !" , e);
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
            self.scheduledCommit.shutdown();
            self.scheduledCommit = null;
            self.db.close();
            Shell.println("ok", GraphicRenditionEnum.GREEN);
        } catch (SQLException ex) {
            Loggin.error("Unable to close database", ex);
        }
    }
}
