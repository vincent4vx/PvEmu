package org.pvemu.network.realm;

import java.io.IOException;
import java.util.logging.Level;
import org.pvemu.common.Config;
import org.pvemu.common.Loggin;
import org.pvemu.common.Shell;
import org.pvemu.common.Shell.GraphicRenditionEnum;
import org.pvemu.network.MinaServer;

public class RealmServer {

    protected MinaServer _server;
    protected static RealmServer instance = null;

    private RealmServer() {
        try {
            Shell.print("Lancement du Realm : ", GraphicRenditionEnum.YELLOW);
            _server = new MinaServer(Config.REALM_PORT.getValue(), new RealmIoHandler());
            Shell.print("Ok", GraphicRenditionEnum.GREEN);
            Shell.println(" (port " + Config.REALM_PORT.getValue() + ")");
        } catch (IOException ex) {
            Loggin.realm("Impossible de lancer le serveur de Realm (port : %d)", Level.SEVERE, Config.REALM_PORT.getValue());
            System.exit(1);
        }
    }

    public static void start() {
        if (instance != null) {
            Loggin.realm("Serveur de Realm déjà lancé !", Level.WARNING);
        }
        instance = new RealmServer();
    }

    public static void stop() {
        Shell.print("Arrêt du realm : ", GraphicRenditionEnum.RED);
        instance._server.stop();
        Shell.println("ok", GraphicRenditionEnum.GREEN);
    }
}
