package jelly;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;
import jelly.Shell.GraphicRenditionEnum;

public class Loggin {

    private final static Loggin self = new Loggin();
    private Logger realm;
    private Logger game;
    private StreamHandler realm_handler;
    private StreamHandler game_handler;

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
        if (Jelly.DEBUG) {
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
        if (Jelly.DEBUG) {
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
        if (lvl == Level.INFO && !Jelly.DEBUG) {
            return;
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
        if (lvl == Level.INFO && !Jelly.DEBUG) {
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
        if (Jelly.DEBUG) {
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
        if (lvl == Level.INFO && !Jelly.DEBUG) {
            return;
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
        if (lvl == Level.INFO && !Jelly.DEBUG) {
            return;
        }
        self.game.log(lvl, format, args);
        self.game_handler.flush();
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
}
