package server.game;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jelly.Config;
import jelly.Loggin;
import jelly.Shell;
import jelly.Shell.GraphicRenditionEnum;
import server.MinaServer;

public class GameServer {

    protected MinaServer _server;
    protected static GameServer instance = null;

    private GameServer() {
        try {
            Shell.print("Lancement du Game : ", Shell.GraphicRenditionEnum.YELLOW);
            _server = new MinaServer(Config.getInt("GAME_PORT", 5555), new GameIoHandler());
            Shell.print("Ok", Shell.GraphicRenditionEnum.GREEN);
            Shell.println(" (port " + Config.getInt("game_port", 443) + ")");
        } catch (IOException ex) {
            Loggin.game("Impossible de lancer le serveur de Game (port : " + Config.getInt("game_port", 5555) + ")", Level.SEVERE, null);
            System.exit(1);
        }
    }

    public static void start() {
        if (instance != null) {
            Loggin.realm("Serveur de Game déjà lancé !", Level.WARNING, null);
        }
        instance = new GameServer();
    }

    public static void stop() {
        Shell.print("Arrêt du Game : ", GraphicRenditionEnum.RED);
        instance._server.stop();
        Shell.println("ok", GraphicRenditionEnum.GREEN);
    }
}
