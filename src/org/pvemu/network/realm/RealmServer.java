package org.pvemu.network.realm;

import java.io.IOException;
import java.util.logging.Level;
import org.pvemu.common.Config;
import org.pvemu.common.Loggin;
import org.pvemu.common.Shell;
import org.pvemu.common.Shell.GraphicRenditionEnum;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;
import org.pvemu.network.MinaServer;

public class RealmServer {

    protected MinaServer server;
    protected static RealmServer instance = null;

    private RealmServer() {
        try {
            Shell.print(I18n.tr(Commons.LAUNCHING_REALM), GraphicRenditionEnum.YELLOW);
            server = new MinaServer(Config.REALM_PORT.getValue(), new RealmIoHandler());
            Shell.print("Ok", GraphicRenditionEnum.GREEN);
            Shell.println(I18n.tr(Commons.PORT, Config.REALM_PORT.getValue()));
        } catch (IOException ex) {
            Loggin.realm(I18n.tr(Commons.CANT_LAUNCH, "realm", Config.REALM_PORT.getValue()), Level.SEVERE, ex);
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
        Shell.print(I18n.tr(Commons.STOPING_REALM), GraphicRenditionEnum.RED);
        instance.server.stop();
        Shell.println("ok", GraphicRenditionEnum.GREEN);
    }
}
