package org.pvemu.network.game;

import java.io.IOException;
import java.util.logging.Level;
import org.pvemu.jelly.Config;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Shell;
import org.pvemu.jelly.Shell.GraphicRenditionEnum;
import org.pvemu.jelly.utils.Crypt;
import org.pvemu.jelly.utils.Utils;
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
            
            Shell.print("Lancement du Game : ", Shell.GraphicRenditionEnum.YELLOW);
            server = new MinaServer(port, new GameIoHandler());
            Shell.print("Ok", Shell.GraphicRenditionEnum.GREEN);
            Shell.println(" (port " + port + ")");

            if (Config.CRYPT_IP.getValue() || Constants.DOFUS_VER_ID < 1200) {
                CRYPT_IP = Crypt.CryptIP(Config.IP.getValue()) + Crypt.CryptPort(port);
                Shell.println("GameServer IP crypté en " + CRYPT_IP, GraphicRenditionEnum.BLUE);
            }
        } catch (IOException ex) {
            Loggin.game("Impossible de lancer le serveur de Game (port : " + port + ")", Level.SEVERE);
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
        Shell.print("Arrêt du Game : ", GraphicRenditionEnum.RED);
        instance.server.stop();
        Shell.println("ok", GraphicRenditionEnum.GREEN);
    }
}
