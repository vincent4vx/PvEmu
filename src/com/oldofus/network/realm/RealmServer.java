package com.oldofus.network.realm;

import java.io.IOException;
import java.util.logging.Level;
import com.oldofus.jelly.Config;
import com.oldofus.jelly.Loggin;
import com.oldofus.jelly.Shell;
import com.oldofus.jelly.Shell.GraphicRenditionEnum;
import com.oldofus.network.MinaServer;

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
            Loggin.realm("Impossible de lancer le serveur de Realm (port : %d)", Level.SEVERE, Config.getInt("realm_port", 443));
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
