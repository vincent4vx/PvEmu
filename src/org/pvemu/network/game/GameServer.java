package org.pvemu.network.game;

import java.io.IOException;
import java.util.logging.Level;
import org.pvemu.common.Config;
import org.pvemu.common.Constants;
import org.pvemu.common.Loggin;
import org.pvemu.common.Shell;
import org.pvemu.common.Shell.GraphicRenditionEnum;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;
import org.pvemu.common.utils.Crypt;
import org.pvemu.common.utils.Utils;
import org.pvemu.network.MinaServer;

public class GameServer {

    private MinaServer server;
    static private GameServer instance = null;
    public static String CRYPT_IP;

    private GameServer() {
        short port = Config.GAME_PORT.getValue().shortValue();

        if(port <= 0){
            port = Utils.getFreePort();
        }
        try {
            
            Shell.print(I18n.tr(Commons.LAUNCHING_GAME), Shell.GraphicRenditionEnum.YELLOW);
            server = new MinaServer(port, new GameIoHandler());
            Shell.print("Ok", Shell.GraphicRenditionEnum.GREEN);
            Shell.println(I18n.tr(Commons.PORT, port));

            if (Config.CRYPT_IP.getValue() || Constants.DOFUS_VER_ID < 1200) {
                CRYPT_IP = Crypt.CryptIP(Config.IP.getValue()) + Crypt.CryptPort(port);
                Shell.println("GameServer IP crypté en " + CRYPT_IP, GraphicRenditionEnum.BLUE);
            }
        } catch (IOException ex) {
            Loggin.game(I18n.tr(Commons.CANT_LAUNCH, "game", port), Level.SEVERE, ex);
            System.exit(1);
        }
    }

    public static void start() {
        if (instance != null) {
            Loggin.game("Serveur de Game déjà lancé !", Level.WARNING);
        }
        instance = new GameServer();
    }

    public static void stop() {
        Shell.print(I18n.tr(Commons.STOPING_GAME), GraphicRenditionEnum.RED);
        instance.server.stop();
        Shell.println("ok", GraphicRenditionEnum.GREEN);
    }
}
