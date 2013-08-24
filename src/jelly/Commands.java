package jelly;

import game.World;
import game.objects.GameMap;
import game.objects.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.mina.core.session.IoSession;
import server.events.BasicEvents;

public class Commands {

    /**
     * Analyse puis exécute la (ou les) commandes d'une console admin
     *
     * @param line
     * @param level
     * @param session
     */
    public static String exec(String line, byte level, IoSession session) {
        StringBuilder ret = new StringBuilder();
        for (String data : line.split("&&")) {


            /*for (String msg : parseCommand(data, level, session).split("\n")) {
                if (msg.isEmpty()) {
                    break;
                }

                for (int i = 0; i < msg.length(); i += 150) {
                    int endIndex = i + 150;
                    if (msg.length() < endIndex) {
                        endIndex = msg.length();
                    }
                    String submsg = msg.substring(i, endIndex).trim();
                    GamePacketEnum.BASIC_CONSOLE_WRITE.send(session, submsg);
                }
            }*/
            ret.append(parseCommand(data, level, session));
        }
        return ret.toString();
    }

//    /**
//     * Analyse la (ou les) commandes du shell et execution args
//     *
//     * @param line
//     */
//    public static void exec(String line, IoSession session) {
//        for (String data : line.split("&&")) {
//            String msg = parseCommand(data, (byte) 256, session);
//            System.out.println(msg);
//        }
//    }
    private static String parseCommand(String command_data, byte lvl, IoSession session) {
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

        return process(arr[0].toLowerCase().trim(), argv.length - 1, argv, lvl, session);
    }

    private static String process(String command, int argc, String[] argv, byte lvl, IoSession session) {
        if ((lvl >= 4)) {
            return process_lvl4(command, argc, argv, session);
        } else if (lvl == 3) {
            return process_lvl3(command, argc, argv, session);
        } else if (lvl == 2) {
            return process_lvl2(command, argc, argv, session);
        } else if (lvl == 1) {
            return process_lvl1(command, argc, argv, session);
        }
        return "";
    }

    private static String process_lvl1(String command, int argc, String[] argv, IoSession session) {
        switch (command) {
            case "echo":
                return argv[0];
            case "@":
            case "msg":
            case "pm":
                if (argc < 2) {
                    return "Nombre d'arguments invalide !";
                }
                StringBuilder message = new StringBuilder();

                for (int i = 2; i <= argc; i++) {
                    message.append(" ").append(argv[i]);
                }

                AtomicReference<String> err = new AtomicReference<>();
                BasicEvents.onServerMessage(session, message.toString(), getPlayers(argv[1], session, err));
                return err.get();
            case "map": //alias de @ #map msg
                return parseCommand("@ #map " + argv[0], (byte) 1, session);
            case "all": //alias de @ #all msg
                return parseCommand("@ #all " + argv[0], (byte) 1, session);
        }
        return "Commande invalide !";
    }

    private static String process_lvl2(String command, int argc, String[] argv, IoSession session) {
        switch (command) {
        }
        return process_lvl1(command, argc, argv, session);
    }

    private static String process_lvl3(String command, int argc, String[] argv, IoSession session) {
        switch (command) {
        }
        return process_lvl2(command, argc, argv, session);
    }

    private static String process_lvl4(String command, int argc, String[] argv, IoSession session) {
        switch (command) {
            case "set":
            case "configure":
                if (argc < 2) {
                    return "Nombre d'arguments invalides !";
                }
                Config.set(argv[1], argv[2]);
                return "Configuration sauvegardé. Pour appliquer la modification, veuillez lancer <b>reload config</b>";
            case "debug":
                if (argc < 1) {
                    if (Jelly.DEBUG) {
                        Jelly.DEBUG = false;
                        return "Mode débug désactivé !";
                    } else {
                        Jelly.DEBUG = true;
                        return "Mode débug activé !";
                    }
                }
                switch (argv[0].toLowerCase()) {
                    case "on":
                    case "true":
                    case "yes":
                    case "oui":
                        Jelly.DEBUG = true;
                        return "Mode débug activé !";
                    case "off":
                    case "no":
                    case "false":
                    case "non":
                        Jelly.DEBUG = false;
                        return "Mode débug désactivé !";
                }
                break;
            case "exit": //arrêt forcé du serveur
            case "quit":
                if(session != null){
                    session.close(true);
                }
                System.exit(0);
                return "";
            case "send":
                if (argc < 2) {
                    return "Nombre d'arguments invalides !";
                }
                AtomicReference<String> errors = new AtomicReference<>();
                StringBuilder ret = new StringBuilder();
                for (Player P : getPlayers(argv[1], session, errors)) {
                    ret.append("\n").append(P.getName()).append(" : Send >> ").append(argv[2]);
                    if (P.getSession() != null) {
                        P.getSession().write(argv[2].replace("&nbsp;", " "));
                    }
                }
                return errors + ret.toString();
        }
        return process_lvl3(command, argc, argv, session);
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
}
