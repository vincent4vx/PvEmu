package jelly;

import game.objects.dep.Stats;
import java.util.ArrayList;
import jelly.database.Database;
import models.dao.DAOFactory;
import server.game.GameServer;
import server.realm.RealmServer;

public class Jelly {
    public static boolean DEBUG=false;
    
    public static long start;

    public static boolean running = true;
    
    public static void main(String[] args) {
        System.out.println("======================================================");
        printAsciiLogo();
        System.out.println("\t\t\tJelly-emu by v4vx");
        System.out.println("Version " + Constants.VERSION);
        System.out.println("======================================================");
        
        if(args.length == 0)
            start();
        else{
            switch(args[0].toLowerCase()){
                case "?":
                case "help":
                    System.out.println("\nAide Jelly CLI :");
                    System.out.println("----------------\n");
                    System.out.println("HELP                  : affiche ce menu");
                    System.out.println("SET [param] [value]   : Ajoute / change la configuration de [param]");
                    System.out.println("DEBUG                 : lancer l'émulateur en mode débug");
                    break;
                case "debug":
                    System.out.println("Mode DEBUG actif");
                    DEBUG = true;
                    start();
                    break;
                case "set":
                    if(args.length < 2){
                        System.out.println("Arguments manquants");
                        return;
                    }
                    Config.set(args[1], args[2], true);
                    break;
            }
        }
    }
    
    private static void start(){
        start=System.currentTimeMillis();
        Config.load();
        Database.connect();
        Stats.loadElements();
        System.out.println("Lancement du Realm");
        RealmServer.start();
        GameServer.start();
        System.out.println("Serveur lancé en " + (System.currentTimeMillis() - start) + "ms");
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                Jelly.close();
            }
        });
    }

    private static void printAsciiLogo(){
        System.out.println(
                "   ___      _ _       _____\n" +
                "  |_  |    | | |     |  ___|\n" +
                "    | | ___| | |_   _| |__ _ __ ___  _   _\n" +
                "    | |/ _ \\ | | | | |  __| '_ ` _ \\| | | |\n" +
                "/\\__/ /  __/ | | |_| | |__| | | | | | |_| |\n" +
                "\\____/ \\___|_|_|\\__, \\____/_| |_| |_|\\__,_|\n" +
                "                 __/ |\n" +
                "                |___/\n"
        );
    }
    
    public static void close(){
        System.out.println("Arrêt du serveur en cours...");
        RealmServer.stop();
        GameServer.stop();
        Database.close();
        System.out.println("JellyEmu : arrêt");
    }
}
