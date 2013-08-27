package jelly;

import game.World;
import game.objects.GameMap;
import game.objects.Player;
import game.objects.dep.ItemStats;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import jelly.Shell.GraphicRenditionEnum;
import models.ItemTemplate;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.events.BasicEvents;
import server.game.GamePacketEnum;

public class Commands {
    private final static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Analyse puis exécute la (ou les) commandes d'une console admin
     *
     * @param line
     * @param level
     * @param session
     */
    public static void exec(String line, final byte level, final IoSession session) {
        for (final String data : line.split("&&")) {
            pool.execute(new Runnable(){
                @Override
                public void run(){
                    parseCommand(data, level, session);
                }
            });
        }
    }

    /**
     * Analyse la (ou les) commandes du shell et execution args
     *
     * @param line
     */
    public static void exec(String line) {
        for (String data : line.split("&&")) {
            parseCommand(data, (byte) 256, null);
        }
    }
    
    private static void parseCommand(String command_data, byte lvl, IoSession session) {
        String[] arr = command_data.trim().split(" +", 2);
        String[] arr2 = {};
        if (arr.length > 1) {
            arr2 = arr[1].split(" +");
        }
        String[] argv = new String[arr2.length + 1];
        argv[0] = arr.length > 1 ? arr[1] : "";
        if (arr2.length > 0) {
            System.arraycopy(arr2, 0, argv, 1, arr2.length);
        }

        process(arr[0].toLowerCase().trim(), argv.length - 1, argv, lvl, session);
    }

    private static void process(String command, int argc, String[] argv, byte lvl, IoSession session) {
        if ((lvl >= 4)) {
            process_lvl4(command, argc, argv, session);
        } else if (lvl == 3) {
            process_lvl3(command, argc, argv, session);
        } else if (lvl == 2) {
            process_lvl2(command, argc, argv, session);
        } else if (lvl == 1) {
            process_lvl1(command, argc, argv, session);
        }
    }

    private static void process_lvl1(String command, int argc, String[] argv, IoSession session) {
        switch (command) {
            case "echo":
                display(argv[0], session);
                break;
            case "tic":
                int turn = 1;
                if(argc > 0){
                    turn = Integer.parseInt(argv[1]);
                }
                for(int i = 0; i < turn; i++){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {}
                    if(i % 2 == 0){
                        display("tac (" + i + ")", session);
                    }else{
                        display("tic (" + i + ")", session);
                    }
                }
                break;
            case "@":
            case "msg":
            case "pm":
                if (argc < 2) {
                    display("Nombre d'arguments invalide !", session);
                    return;
                }
                StringBuilder message = new StringBuilder();

                for (int i = 2; i <= argc; i++) {
                    message.append(" ").append(argv[i]);
                }

                AtomicReference<String> err = new AtomicReference<>();
                BasicEvents.onServerMessage(session, message.toString(), getPlayers(argv[1], session, err));
                display(err.get(), session);
                break;
            case "map": //alias de @ #map msg
                parseCommand("@ #map " + argv[0], (byte) 1, session);
                break;
            case "all": //alias de @ #all msg
                parseCommand("@ #all " + argv[0], (byte) 1, session);
                break;
            default:
                display("Commande invalide !", session);
        }
    }

    private static void process_lvl2(String command, int argc, String[] argv, IoSession session) {
        switch (command) {
            case "save":
                display("Début de la sauvegarde...", session);
                World.save();
                display("Sauvegarde terminé !", session);
                break;
            case "!getitem":
            case "item":
                if(argc < 1){
                    display("Nombre d'arguments incorrect !", session);
                    return;
                }
                int id = 0;
                int qu = 1;
                boolean useMax = false;
                String identifier = "#me";
                try{
                    id = Integer.parseInt(argv[1]);   
                    if(argc > 1){
                        qu = Integer.parseInt(argv[2]);
                    }
                    if(argc > 2){
                        identifier = argv[3];
                    }
                    if(argc > 3 && argv[4].equalsIgnoreCase("max")){
                        useMax = true;
                    }
                }catch(NumberFormatException e){
                    display("L'id ou la quantité de l'item doivent être un nombre valide !", session);
                    return;
                }
                ItemTemplate T = DAOFactory.item().getById(id);
                
                if(T == null){
                    display("L'item d'id " + id + " n'existe pas !", session);
                    return;
                }
                
                display("Création de l'item...", session);
                ItemStats IS = new ItemStats(T, useMax);
                AtomicReference<String> errors = new AtomicReference<>();
                StringBuilder msg = new StringBuilder();
                for(Player P : getPlayers(identifier, session, errors)){
                    msg.append("Ajout de l'item ").append(id).append(" au joueur ").append(P.getName());
                    if(useMax){
                        msg.append(" Avec les stats maximum !");
                    }
                    msg.append('\n');
                    P.addItem(IS, qu);
                    GamePacketEnum.INFORMATION_MESSAGE.send(P.getSession(), "21;" + qu + "~" + id);
                }
                display(errors.get(), session);
                display(msg.toString(), session);
                break;
            default:
                process_lvl1(command, argc, argv, session);
        }
    }

    private static void process_lvl3(String command, int argc, String[] argv, IoSession session) {
        switch (command) {
            default:
                process_lvl2(command, argc, argv, session);
        }
    }

    private static void process_lvl4(String command, int argc, String[] argv, IoSession session) {
        switch (command) {
            case "set":
            case "configure":
                if (argc < 2) {
                    display("Nombre d'arguments invalides !", session);
                    return;
                }
                Config.set(argv[1], argv[2]);
                display("Configuration sauvegardé. Pour appliquer la modification, veuillez lancer <b>reload config</b>", session);
                break;
            case "debug":
                switch (argv[0].toLowerCase()) {
                    case "on":
                    case "true":
                    case "yes":
                    case "oui":
                        Jelly.DEBUG = true;
                    case "off":
                    case "no":
                    case "false":
                    case "non":
                        Jelly.DEBUG = false;
                    case "":
                        Jelly.DEBUG = !Jelly.DEBUG;
                        break;
                    default:
                        display("argument invalide : Il doit être un booléen !", session);
                }
                String msg = "Mode DEBUG activé !";
                if(!Jelly.DEBUG){
                    msg = "Mode DEBUG désactivé !";
                }
                display(msg, session);
                Shell.println(msg, GraphicRenditionEnum.GREEN);
                break;
            case "exit": //arrêt forcé du serveur
            case "quit":
                if(session != null){
                    session.close(true);
                }
                System.exit(0);
                break;
            case ">>":
            case "send":
                if (argc < 2) {
                    display("Nombre d'arguments invalides !", session);
                    return;
                }
                AtomicReference<String> errors = new AtomicReference<>();
                StringBuilder ret = new StringBuilder();
                for (Player P : getPlayers(argv[1], session, errors)) {
                    ret.append("\n").append(P.getName()).append(" : Send >> ").append(argv[2]);
                    if (P.getSession() != null) {
                        P.getSession().write(argv[2].replace("&nbsp;", " "));
                    }
                }
                display(errors.get(), session);
                display(ret.toString(), session);
                break;
            default:
        process_lvl3(command, argc, argv, session);
        }
    }

    private static Collection<Player> getPlayers(String identifier, IoSession session, AtomicReference<String> errors) {
        StringBuilder err = new StringBuilder();
        Collection<Player> players = new ArrayList<>();
        Player self = null;
        if (session != null) {
            self = (Player) session.getAttribute("player");
        }

        String[] arr_id;
        if (!identifier.contains(",")) {
            arr_id = new String[]{identifier};
        } else {
            arr_id = identifier.split(",");
        }

        for (String id : arr_id) {
            if (id.charAt(0) == '#') {
                if (id.equalsIgnoreCase("#me") || id.equalsIgnoreCase("#self")) {
                    if (self == null) {
                        err.append("\nErreur : Vous ne pouvez pas utiliser l'identifieur #me ou #self");
                        continue;
                    }
                    players.add(self);
                } else if (id.equalsIgnoreCase("#map")) {
                    if (self == null || self.getMap() == null) {
                        err.append("\nErreur : map introuvable !");
                        continue;
                    }
                    players.addAll(self.getMap().getPlayers().values());
                } else if (id.toLowerCase().startsWith("#map")) {
                    short mapID = 0;
                    try {
                        mapID = Short.parseShort(id.substring(4));
                    } catch (NumberFormatException e) {
                        err.append("\nErreur : mapID invalide : mapID doit être un nombre valide !");
                        continue;
                    }
                    GameMap map = models.dao.DAOFactory.map().getById(mapID).getGameMap();
                    if (map == null) {
                        err.append("\nErreur : map introuvable !");
                        continue;
                    }
                    players.addAll(map.getPlayers().values());
                } else if (id.toLowerCase().startsWith("#gm")) {
                    char c;
                    byte level;
                    if (id.length() == 3) {
                        c = '+';
                        level = 0;
                    } else {
                        c = id.charAt(3);
                        if (c != '+' && c != '-' && c != '=') {
                            err.append("\nErreur : l'identifieur #gm n'accepte que + - = comme comparateurs !");
                            continue;
                        }
                        try {
                            level = Byte.parseByte(id.substring(4));
                        } catch (Exception e) {
                            err.append("\nErreur : level gm invalide !");
                            continue;
                        }
                    }
                    players.addAll(World.getOnlinePlayers(c, level));
                } else if (id.equalsIgnoreCase("#all")) {
                    players.addAll(World.getOnlinePlayers());
                } else {
                    err.append("\nErreur : identifieur invalide : ").append(id);
                }
            } else {
                Player p = World.getOnlinePlayer(id.trim());
                if (p == null) {
                    err.append("Erreur : le joueur ").append(id).append(" n'existe pas, ou n'est pas connecté !");
                    continue;
                }
                players.add(p);
            }
        }

        errors.set(err.toString());

        return players;
    }
    
    private static void display(String msg, IoSession session){
        if(session != null && session.containsAttribute("player")){
            BasicEvents.onWriteConsole(session, msg);
        }else{
            Shell.println(msg, GraphicRenditionEnum.GREEN);
        }
    }
}
