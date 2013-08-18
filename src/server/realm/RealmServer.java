package server.realm;

import java.io.IOException;
import java.util.logging.Level;
import jelly.Config;
import jelly.Loggin;
import server.MinaServer;
public class RealmServer {
    protected MinaServer _server;
    protected static RealmServer instance = null;
    
    private RealmServer(){
        try {
            _server = new MinaServer(Config.getInt("REALM_PORT", 443), new RealmIoHandler());
            System.out.println("Serveur de Realm lancé sur port " + Config.getInt("realm_port", 443));
        } catch (IOException ex) {
            Loggin.realm("Impossible de lancer le serveur de Realm (port : " + Config.getInt("realm_port", 443) + ")", Level.SEVERE, ex);
            System.exit(1);
        }
    }
    
    public static void start(){
        if(instance != null){
            Loggin.realm("Serveur de Realm déjà lancé !", Level.WARNING, null);
        }
        instance = new RealmServer();
    }
    
    public static void stop(){
        System.out.print("Arrêt du realm : ");
        instance._server.stop();
        System.out.println("ok");
    }
}
