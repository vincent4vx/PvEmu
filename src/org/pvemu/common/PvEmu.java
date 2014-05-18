package org.pvemu.common;

import org.pvemu.game.World;
import org.pvemu.common.Shell.GraphicRenditionEnum;
import org.pvemu.common.database.DatabaseHandler;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;
import org.pvemu.common.plugin.PluginsHandler;
import org.pvemu.common.scripting.API;
import org.pvemu.common.systats.SystemStats;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.game.GameServer;
import org.pvemu.network.realm.RealmServer;

public class PvEmu {

    public static boolean DEBUG = false;
    public static long start;
    public static boolean running = true;

    public static void main(String[] args) {
        Shell.setTitle(Constants.NAME);
        Shell.println("=======================================", GraphicRenditionEnum.YELLOW);
        printAsciiLogo();
        Shell.print("\t\t\t" + Constants.NAME + I18n.tr(Commons.BY), GraphicRenditionEnum.YELLOW);
        Shell.println("v4vx", GraphicRenditionEnum.YELLOW, GraphicRenditionEnum.BOLD);
        Shell.println(I18n.tr(Commons.VERSION, Constants.VERSION), GraphicRenditionEnum.YELLOW);
        Shell.print(I18n.tr(Commons.FOR_DOFUS), GraphicRenditionEnum.YELLOW);
        Shell.println(Constants.DOFUS_VER.toString(), GraphicRenditionEnum.YELLOW, GraphicRenditionEnum.BOLD);
        Shell.println("=======================================", GraphicRenditionEnum.YELLOW);

        if (args.length == 0) {
            start();
        } else {
            switch (args[0].toLowerCase()) {
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
                    if (args.length < 2) {
                        System.out.println("Arguments manquants");
                        return;
                    }
                    //OldConfig.set(args[1], args[2]);
                    break;
            }
        }
    }

    private static void start() {
        start = System.currentTimeMillis();
        Config.load();
        API.initialise();
        DatabaseHandler.init();
        DAOFactory.init();
        World.instance().create(Config.PRELOAD.getValue());
        PluginsHandler.instance().loadPlugins();
        RealmServer.start();
        GameServer.start();
        Shell.println(I18n.tr(Commons.LOADED_IN, I18n.tr(Commons.SERVER), System.currentTimeMillis() - start), GraphicRenditionEnum.GREEN, GraphicRenditionEnum.BOLD);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                PvEmu.close();
            }
        });
        SystemStats.init();
    }

    private static void printAsciiLogo() {
        String logo = 
                  "______       _____\n"
                + "| ___ \\     |  ___|\n"
                + "| |_/ /_   _| |__ _ __ ___  _   _ \n"
                + "|  __/\\ \\ / /  __| '_ ` _ \\| | | |\n"
                + "| |    \\ V /| |__| | | | | | |_| |\n"
                + "\\_|     \\_/ \\____/_| |_| |_|\\__,_|\n";
        
        Shell.println(logo, GraphicRenditionEnum.YELLOW, GraphicRenditionEnum.BOLD);
    }

    private static void close() {
        if (running) {
            running = false;
            Shell.println(I18n.tr(Commons.STOPING), GraphicRenditionEnum.RED);
            World.instance().destroy();
            DatabaseHandler.instance().close();
            RealmServer.stop();
            GameServer.stop();
            Shell.println(I18n.tr(Commons.STOPED), GraphicRenditionEnum.RED);
            Shell.flush();
        }
    }
}
