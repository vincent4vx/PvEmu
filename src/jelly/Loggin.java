package jelly;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

public class Loggin {
    private final static Loggin self = new Loggin();
    private Logger realm;
    private Logger game;
    
    private StreamHandler realm_handler;
    private StreamHandler game_handler;
    
    private Loggin(){
        try {
            realm = Logger.getLogger("Realm");
            realm.addHandler(new FileHandler("realm.log"));
            
            realm_handler = new StreamHandler(System.out, new ConsoleFormatter());
            realm.addHandler(realm_handler);
            realm.setUseParentHandlers(false);
            
            game = Logger.getLogger("Game");
            game.addHandler(new FileHandler("game.log"));
            game_handler = new StreamHandler(System.out, new ConsoleFormatter());
            game.addHandler(game_handler);
            game.setUseParentHandlers(false);
        } catch (Exception e) {}
    }
    
    /**
     * Message de debug (non enregistré)
     * @param msg 
     */
    public static void debug(String msg){
        if(Jelly.DEBUG){
            System.out.println("[debug] " + msg);
        }
    }
    
    /**
     * Message de debug avec format (non enregistré)
     * @param format
     * @param args 
     */
    public static void debug(String format, Object ... args){
        if(Jelly.DEBUG){
            System.out.println("[debug]" + String.format(format, args));
        }
    }
    
    /**
     * log realm en debug avec format
     * @param msg
     * @param args 
     */
    public static void realm(String msg, Object ... args){
        if(Jelly.DEBUG){
            realm(msg, Level.INFO, args);
        }
    }
    
    /**
     * log realm avec exception
     * @param msg
     * @param lvl
     * @param ex 
     */
    public static void realm(String msg, Level lvl, Exception ex){
        if(lvl == Level.INFO && !Jelly.DEBUG){
            return;
        }
        self.realm.log(lvl, msg, ex);
        self.realm_handler.flush();
    }
    
    /**
     * log realm avec format (hors debug)
     * @param format
     * @param lvl
     * @param args 
     */
    public static void realm(String format, Level lvl, Object ... args){
        if(lvl == Level.INFO && !Jelly.DEBUG){
            return;
        }
        self.realm.log(lvl, format, args);
        self.realm_handler.flush();    
    }
    
    /**
     * Log game avec format pour debug
     * @param msg
     * @param args 
     */
    public static void game(String msg, Object ... args){
        if(Jelly.DEBUG){
            game(msg, Level.INFO, args);
        }
    }
    
    /**
     * log game avec exception
     * @param msg
     * @param lvl
     * @param ex 
     */
    public static void game(String msg, Level lvl, Exception ex){
        if(lvl == Level.INFO && !Jelly.DEBUG){
            return;
        }
        self.game.log(lvl, msg, ex);
        self.game_handler.flush();
    }
    
    /**
     * log game avec format hors debug
     * @param format
     * @param lvl
     * @param args 
     */
    public static void game(String format, Level lvl, Object ... args){
        if(lvl == Level.INFO && !Jelly.DEBUG){
            return;
        }
        self.game.log(lvl, format, args);
        self.game_handler.flush();    
    }
    
    private static class ConsoleFormatter extends Formatter{

        @Override
        public String format(LogRecord record) {
            StringBuilder msg = new StringBuilder();
            
            msg.append("[").append(record.getLoggerName()).append("] ").append(record.getLevel().toString()).append(" : ");
            msg.append(String.format(record.getMessage(), record.getParameters()));
            if(record.getThrown() != null){
                msg.append("\n").append(record.getThrown().toString());
            }
            msg.append("\n");
            
            return msg.toString();
        }
        
    }
}
