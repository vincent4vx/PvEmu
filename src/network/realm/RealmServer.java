package network.realm;

import java.io.IOException;
import java.util.logging.Level;
import jelly.Config;
import jelly.Loggin;
import jelly.Shell;
import jelly.Shell.GraphicRenditionEnum;
import network.MinaServer;

public class RealmServer {

    protected MinaServer _server;
    protected static RealmServer instance = null;

    private RealmServer() {
        try {
            Shell.print("Lancement du Realm : ", GraphicRenditionEnum.YELLOW);
            _server = new MinaServer(Config.getInt("REALM_PORT", 443), new RealmIoHandler());
            Shell.print("Ok", GraphicRenditionEnum.GREEN);
            Shell.println(" (port " + Config.getInt("realm_port", 443) + ")");
        } catch (IOException ex) {
            Loggin.realm("Impossible de lancer le serveur de Realm (port : " + Config.getInt("realm_port", 443) + ")", Level.SEVERE, null);
            System.exit(1);
        }
    }

    public static void start() {
        if (instance != null) {
            Loggin.realm("Serveur de Realm déjà lancé !", Level.WARNING, null);
        }
        instance = new RealmServer();
    }

    public static void stop() {
        Shell.print("Arrêt du realm : ", GraphicRenditionEnum.RED);
        instance._server.stop();
        Shell.println("ok", GraphicRenditionEnum.GREEN);
    }
}
