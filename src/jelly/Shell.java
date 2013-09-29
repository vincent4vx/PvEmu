package jelly;

import game.World;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.fusesource.jansi.AnsiConsole;
import server.game.GameIoHandler;

public class Shell {

    private static final String ESC = "\033[";
    private static final String END = "\033[0m";

    public enum GraphicRenditionEnum {
        //FONT

        BOLD(1),
        ITALIC(3),
        UNDERLINE(4),
        BLINK(5),
        HIDDEN(8),
        BLACK(30),
        RED(31),
        GREEN(32),
        YELLOW(33),
        BLUE(34),
        MAGENTA(35),
        CYAN(36),
        WHITE(37),
        //BG
        BG_BLACK(40),
        BG_RED(41),
        BG_GREEN(42),
        BG_YELLOW(43),
        BG_BLUE(44),
        BG_MAGENTA(45),
        BG_CYAN(46),
        BG_WHITE(47),
        //SPECIAL
        BLACK_AND_BG_WHITE(7),
        RESET(0);
        private int ansi_code;

        GraphicRenditionEnum(int code) {
            this.ansi_code = code;
        }

        public int get() {
            return ansi_code;
        }
    }

    public static void clear() {
        AnsiConsole.out.print("\033[H\033[2J");
    }

    public static void setTitle(String title) {
        AnsiConsole.out.append("\033]0;").append(title).append("\007");
    }

    public static void print(String message, GraphicRenditionEnum... srgs) {
        AnsiConsole.out.print(generateAnsiCode(message, srgs));
    }

    public static void println(String message, GraphicRenditionEnum... srgs) {
        AnsiConsole.out.println(generateAnsiCode(message, srgs));
    }

    public static void eraseLine() {
        AnsiConsole.out.print(ESC + "K");
    }

    public static PrintStream out() {
        return AnsiConsole.out;
    }

    public static String generateAnsiCode(String msg, GraphicRenditionEnum... args) {
        StringBuilder b = new StringBuilder();

        for (GraphicRenditionEnum sgr : args) {
            b.append(ESC).append(sgr.get()).append('m');
        }

        b.append(msg);

        if (args.length > 0) {
            b.append(END);
        }

        return b.toString();
    }
}
