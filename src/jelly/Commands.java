package jelly;

import org.apache.mina.core.session.IoSession;

public class Commands {
    /**
     * Analyse puis exécute la (ou les) commandes d'une console admin
     * @param line
     * @param level
     * @param session 
     */
    public static void exec(String line, byte level, IoSession session){
        for(String data : line.split(" +")){
            String msg = parseCommand(data, level);
            session.write(msg);
        }        
    }
    
    /**
     * Analyse la (ou les) commandes du shell et execution args
     * @param line 
     */
    public static void exec(String line){
        for(String data : line.split(" +")){
            String msg = parseCommand(data, (byte)256);
            System.out.println(msg);
        }
    }
    
    private static String parseCommand(String command_data, byte lvl){
        String[] arr = command_data.split(" +");
        String[] argv = new String[arr.length - 2];
        System.arraycopy(arr, 1, argv, 0, arr.length - 2);
        
        return process(arr[0].toLowerCase().trim(), argv.length, argv, lvl);
    }
    
    private static String process(String command, int argc, String[] argv, byte lvl){
        if((lvl >= 4)){
            return process_lvl4(command, argc, argv);
        }else if(lvl == 3){
            return "";
        }else if(lvl >= 2){
            return "";
        }else if(lvl == 1){
            return "";
        }
        return "";
    }
    
    private static String process_lvl4(String command, int argc, String[] argv){
        switch(command){
            case "set":
            case "configure":
                if(argc < 2){
                    return "Nombre d'arguments invalides !";
                }
                break;
        }
        return "Commande invalide !";
    }
}
