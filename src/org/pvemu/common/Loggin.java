package org.pvemu.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import org.pvemu.common.Shell.GraphicRenditionEnum;

public class Loggin {
    public static int ERROR_COUNT = 0;

    private final static Loggin self = new Loggin();
    private Logger realm;
    private Logger game;
    private Logger errors;
    private StreamHandler realm_handler;
    private StreamHandler game_handler;
    private StreamHandler errors_handler;

    private Loggin() {
        try {
            realm = Logger.getLogger("Realm");
            realm.addHandler(new FileHandler("realm.log"));

            realm_handler = new StreamHandler(Shell.out(), new ConsoleFormatter());
            realm.addHandler(realm_handler);
            realm.setUseParentHandlers(false);

            game = Logger.getLogger("Game");
            game.addHandler(new FileHandler("game.log"));
            
            game_handler = new StreamHandler(Shell.out(), new ConsoleFormatter());
            game.addHandler(game_handler);
            game.setUseParentHandlers(false);
            
            errors = Logger.getLogger("Errors");
            errors.addHandler(new FileHandler("errors.log"));
            
            errors_handler = new StreamHandler(Shell.out(), new ErrorsFormatter());
            errors.addHandler(errors_handler);
            errors.setUseParentHandlers(false);
        } catch (Exception e) {
        }
    }

    /**
     * Message de debug avec format (non enregistré)
     *
     * @param format
     * @param args
     */
    public static void debug(String format, Object... args) {
        if (PvEmu.DEBUG) {
            Shell.print("[Debug] ", GraphicRenditionEnum.GREEN, GraphicRenditionEnum.BOLD);
            Shell.println(String.format(format, args), GraphicRenditionEnum.CYAN);
        }
    }

    /**
     * log realm en debug avec format
     *
     * @param msg
     * @param args
     */
    public static void realm(String msg, Object... args) {
        if (PvEmu.DEBUG) {
            realm(msg, Level.INFO, args);
        }
    }

    /**
     * log realm avec exception
     *
     * @param msg
     * @param lvl
     * @param ex
     */
    public static void realm(String msg, Level lvl, Exception ex) {
        if (lvl == Level.INFO && !PvEmu.DEBUG) {
            return;
        }
        if(lvl != Level.INFO){
            ERROR_COUNT++;
        }
        self.realm.log(lvl, msg, ex);
        self.realm_handler.flush();
    }

    /**
     * log realm avec format (hors debug)
     *
     * @param format
     * @param lvl
     * @param args
     */
    public static void realm(String format, Level lvl, Object... args) {
        if (lvl == Level.INFO && !PvEmu.DEBUG) {
            return;
        }
        self.realm.log(lvl, format, args);
        self.realm_handler.flush();
    }

    /**
     * Log game avec format pour debug
     *
     * @param msg
     * @param args
     */
    public static void game(String msg, Object... args) {
        if (PvEmu.DEBUG) {
            game(msg, Level.INFO, args);
        }
    }

    /**
     * log game avec exception
     *
     * @param msg
     * @param lvl
     * @param ex
     */
    public static void game(String msg, Level lvl, Exception ex) {
        if (lvl == Level.INFO && !PvEmu.DEBUG) {
            return;
        }
        if(lvl != Level.INFO){
            ERROR_COUNT++;
        }
        self.game.log(lvl, msg, ex);
        self.game_handler.flush();
    }

    /**
     * log game avec format hors debug
     *
     * @param format
     * @param lvl
     * @param args
     */
    public static void game(String format, Level lvl, Object... args) {
        if (lvl == Level.INFO && !PvEmu.DEBUG) {
            return;
        }
        self.game.log(lvl, format, args);
        self.game_handler.flush();
    }
    
    /**
     * log pour erreurs autre que Game et Realm
     * @param msg
     * @param ex 
     */
    public static void error(String msg, Throwable ex){
        ERROR_COUNT++;
        self.errors.log(Level.WARNING, msg, ex);
        self.errors_handler.flush();
    }
    
    /**
     * Log pour les erreur quelconques, sans execption
     * @param msg
     * @param args 
     */
    static public void warning(String msg, Object... args){
        ++ERROR_COUNT;
        self.errors.log(Level.WARNING, String.format(msg, args));
        self.errors_handler.flush();
    }

    private static class ConsoleFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            StringBuilder msg = new StringBuilder();

            msg.append(Shell.generateAnsiCode(new StringBuilder().append("[").append(record.getLoggerName()).append("] ").toString(), GraphicRenditionEnum.YELLOW, GraphicRenditionEnum.BOLD));

            GraphicRenditionEnum color = GraphicRenditionEnum.YELLOW;
            if (record.getLevel() == Level.INFO) {
                color = GraphicRenditionEnum.CYAN;
            } else if (record.getLevel() == Level.SEVERE) {
                color = GraphicRenditionEnum.RED;
            }

            msg.append(Shell.generateAnsiCode(record.getLevel().toString(), color));
            msg.append(" : ");

            String log = String.format(record.getMessage(), record.getParameters());
            log = log.replace("Recv <<", Shell.generateAnsiCode("Recv <<", GraphicRenditionEnum.GREEN));
            log = log.replace("Send >>", Shell.generateAnsiCode("Send >>", GraphicRenditionEnum.GREEN));

            msg.append(Shell.generateAnsiCode(log, GraphicRenditionEnum.ITALIC));
            if (record.getThrown() != null) {
                msg.append("\n").append(Shell.generateAnsiCode(record.getThrown().toString(), GraphicRenditionEnum.RED));
            }
            msg.append("\n");

            return msg.toString();
        }
    }
    
    /**
     * Utilisé uniquement par le logger d'erreurs
     */
    private static class ErrorsFormatter extends Formatter{

        @Override
        public String format(LogRecord record) {
            StringBuilder msg = new StringBuilder();
            
            msg.append(Shell.generateAnsiCode("[Error] ", GraphicRenditionEnum.RED, GraphicRenditionEnum.BOLD));
            String className = record.getThrown().getStackTrace()[0].getClassName();
            msg.append(' ').append(Shell.generateAnsiCode(className, GraphicRenditionEnum.YELLOW)).append(" : ");
            msg.append(record.getMessage()).append('\n');
            
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            
            record.getThrown().printStackTrace(pw);
            
            String trace = sw.toString();
            
            msg.append(Shell.generateAnsiCode(trace, GraphicRenditionEnum.RED));
            
            return msg.toString();
        }
        
    }
}
