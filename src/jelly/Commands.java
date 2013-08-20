package jelly;

import org.apache.mina.core.session.IoSession;
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
            String msg = parseCommand(data, level);

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
    public static void exec(String line) {
        for (String data : line.split("&&")) {
            String msg = parseCommand(data, (byte) 256);
            System.out.println(msg);
        }
    }

    private static String parseCommand(String command_data, byte lvl) {
        String[] arr = command_data.trim().split(" +");
        String[] argv = new String[arr.length - 1];
        System.arraycopy(arr, 1, argv, 0, arr.length - 1);

        return process(arr[0].toLowerCase().trim(), argv.length, argv, lvl);
    }

    private static String process(String command, int argc, String[] argv, byte lvl) {
        if ((lvl >= 4)) {
            return process_lvl4(command, argc, argv);
        } else if (lvl == 3) {
            return process_lvl3(command, argc, argv);
        } else if (lvl == 2) {
            return process_lvl2(command, argc, argv);
        } else if (lvl == 1) {
            return process_lvl1(command, argc, argv);
        }
        return "";
    }

    private static String process_lvl1(String command, int argc, String[] argv) {
        switch (command) {
            case "echo":
                StringBuilder msg = new StringBuilder();
                for (String s : argv) {
                    msg.append(" ").append(s);
                }
                return msg.toString();
        }
        return "Commande invalide !";
    }

    private static String process_lvl2(String command, int argc, String[] argv) {
        switch (command) {
        }
        return process_lvl1(command, argc, argv);
    }

    private static String process_lvl3(String command, int argc, String[] argv) {
        switch (command) {
        }
        return process_lvl2(command, argc, argv);
    }

    private static String process_lvl4(String command, int argc, String[] argv) {
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
                    default:
                        return "Argument invalide !";
                }
        }
        return process_lvl3(command, argc, argv);
    }
}
