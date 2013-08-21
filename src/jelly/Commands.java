package jelly;

import game.World;
import game.objects.GameMap;
import game.objects.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.mina.core.session.IoSession;
import server.events.BasicEvents;
import server.game.GamePacketEnum;

public class Commands {

    /**
     * Analyse puis exécute la (ou les) commandes d'une console admin
     *
     * @param line
     * @param level
     * @param session
     */
    public static void exec(String line, byte level, IoSession session) {
        for (String data : line.split("&&")) {
            String msg = parseCommand(data, level, session);

            for (int i = 0; i < msg.length(); i += 150) {
                int endIndex = i + 150;
                if(msg.length() < endIndex){
                    endIndex = msg.length();
                }
                String submsg = msg.substring(i, endIndex);
                GamePacketEnum.BASIC_CONSOLE_WRITE.send(session, submsg);
            }
        }
    }

    /**
     * Analyse la (ou les) commandes du shell et execution args
     *
     * @param line
     */
    public static void exec(String line, IoSession session) {
        for (String data : line.split("&&")) {
            String msg = parseCommand(data, (byte) 256, session);
            System.out.println(msg);
        }
    }

    private static String parseCommand(String command_data, byte lvl, IoSession session) {
        String[] arr = command_data.trim().split(" +");
        String[] argv = new String[arr.length - 1];
        System.arraycopy(arr, 1, argv, 0, arr.length - 1);

        return process(arr[0].toLowerCase().trim(), argv.length, argv, lvl, session);
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
                StringBuilder msg = new StringBuilder();
                for (String s : argv) {
                    msg.append(" ").append(s);
                }
                return msg.toString();
            case "@":
            case "msg":
            case "pm":
                if(argc < 2){
                    return "Nombre d'arguments invalide !";
                }
                StringBuilder message = new StringBuilder();
                
                for(int i = 1; i < argc; i++){
                    message.append(" ").append(argv[i]);
                }
                
                AtomicReference<String> err = new AtomicReference<>();
                BasicEvents.onServerMessage(session, message.toString(), getPlayers(argv[0], session, err));
                return err.get();
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
                break;
            case "debug":
                if(argc < 1){
                    if(Jelly.DEBUG){
                        Jelly.DEBUG = false;
                        return "Mode débug désactivé !";
                    }else{
                        Jelly.DEBUG = true;
                        return "Mode débug activé !";
                    }
                }
                switch(argv[0].toLowerCase()){
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
        }
        return process_lvl3(command, argc, argv, session);
    }
    
    private static Collection<Player> getPlayers(String identifier, IoSession session, AtomicReference<String> errors){
        StringBuilder err = new StringBuilder();
        Collection<Player> players = new ArrayList<>();
        Player self = null;
        if(session != null){
            self = (Player)session.getAttribute("player");
        }
        
        String[] arr_id;
        if(!identifier.contains(",")){
            arr_id = new String[]{identifier};
        }else{
            arr_id = identifier.split(",");
        }
        
        for(String id : arr_id){
            if(id.charAt(0) == '#'){
                if(id.equalsIgnoreCase("#me") || id.equalsIgnoreCase("#self")){
                    if(self == null){
                        err.append("\nErreur : Vous ne pouvez pas utiliser l'identifieur #me ou #self");
                        continue;
                    }
                    players.add(self);
                }else if(id.equalsIgnoreCase("#map")){
                    if(self == null || self.curMap == null){
                        err.append("\nErreur : map introuvable !");
                        continue;
                    }
                    players.addAll(self.curMap.getPlayers().values());
                }else if(id.toLowerCase().startsWith("#map")){
                    short mapID = 0;
                    try{
                        mapID = Short.parseShort(id.substring(4));
                    }catch(NumberFormatException e){
                        err.append("\nErreur : mapID invalide : mapID doit être un nombre valide !");
                        continue;
                    }
                    GameMap map = models.dao.DAOFactory.map().getById(mapID).getGameMap();
                    if(map == null){
                        err.append("\nErreur : map introuvable !");
                        continue;
                    }
                    players.addAll(map.getPlayers().values());
                }else if(id.toLowerCase().startsWith("#gm")){
                    char c;
                    byte level;
                    if(id.length() == 3){
                        c = '+';
                        level = 0;
                    }else{
                        c = id.charAt(3);
                        if( c != '+' && c != '-' && c != '='){
                            err.append("\nErreur : l'identifieur #gm n'accepte que + - = comme comparateurs !");
                            continue;
                        }
                        try{
                            level = Byte.parseByte(id.substring(4));
                        }catch(Exception e){
                            err.append("\nErreur : level gm invalide !");
                            continue;
                        }
                    }
                    players.addAll(World.getOnlinePlayers(c, level));
                }else if(id.equalsIgnoreCase("#all")){
                    players.addAll(World.getOnlinePlayers());
                }
            }
        }
        
        errors.set(err.toString());
        
        return players;
    }
}
