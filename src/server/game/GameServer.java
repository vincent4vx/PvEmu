package server.game;

import java.io.IOException;
import java.util.logging.Level;
import jelly.Config;
import jelly.Loggin;
import server.MinaServer;

public class GameServer {
    protected MinaServer _server;
    protected static GameServer instance = null;
    
    private GameServer(){
        try {
            _server = new MinaServer(Config.getInt("GAME_PORT", 5555), new GameIoHandler());
            System.out.println("Serveur de Game lancé sur port " + Config.getInt("game_port", 5555));
        } catch (IOException ex) {
            Loggin.realm("Impossible de lancer le serveur de Game (port : " + Config.getInt("game_port", 5555) + ")", Level.SEVERE, ex);
            System.exit(1);
        }
    }
    
    public static void start(){
        if(instance != null){
            Loggin.realm("Serveur de Game déjà lancé !", Level.WARNING, null);
        }
        instance = new GameServer();
    }
    
    public static void stop(){
        System.out.print("Arrêt du Game : ");
        instance._server.stop();
        System.out.println("ok");
    }
}
