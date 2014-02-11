package org.pvemu.network.game;

import java.io.IOException;
import java.util.logging.Level;
import org.pvemu.jelly.Config;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Shell;
import org.pvemu.jelly.Shell.GraphicRenditionEnum;
import org.pvemu.jelly.utils.Crypt;
import org.pvemu.network.MinaServer;

public class GameServer {

    protected MinaServer _server;
    protected static GameServer instance = null;
    public static String CRYPT_IP;

    private GameServer() {
        try {
            Shell.print("Lancement du Game : ", Shell.GraphicRenditionEnum.YELLOW);
            _server = new MinaServer(Config.getInt("GAME_PORT", 5555), new GameIoHandler());
            Shell.print("Ok", Shell.GraphicRenditionEnum.GREEN);
            Shell.println(" (port " + Config.getInt("game_port", 443) + ")");

            if (Config.getBool("CRYPT_IP") || Constants.DOFUS_VER_ID < 1200) {
                String ip = Crypt.CryptIP(Config.getString("ip", "127.0.0.1"));
                String port = Crypt.CryptPort(Config.getInt("game_port", 5555));
                CRYPT_IP = ip + port;
                Shell.println("GameServer IP crypté en " + CRYPT_IP, GraphicRenditionEnum.BLUE);
            }
        } catch (IOException ex) {
            Loggin.game("Impossible de lancer le serveur de Game (port : " + Config.getInt("game_port", 5555) + ")", Level.SEVERE);
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
        instance._server.stop();
        Shell.println("ok", GraphicRenditionEnum.GREEN);
    }
}
