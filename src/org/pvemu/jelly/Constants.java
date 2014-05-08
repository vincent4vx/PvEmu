package org.pvemu.jelly;

public class Constants {

    public final static String NAME = "PvEmu";
    public final static String VERSION = "0.53.4a";
    public final static int REV = 242;
    public final static String DOFUS_VER = "1.29.1";
    public final static int DOFUS_VER_ID = 1291;
    public final static byte SERV_ID = 1;
    public final static long ONE_YEAR = 31536000000L;
    public final static String CONFIG_FILE = "jelly.conf";
    public final static String COLOR_RED = "c10000";
    public final static String COLOR_GREEN = "009900";
    public final static int MAX_IDLE_TIME = 900; //15 minutes
    final static public long TIME_CORRECTION = -1370L * 365 * 24 * 3600 * 1000 + 3600000; //-1370 years + 1 hour
    final static public int TURN_TIME = 30; //30s
    final static public int START_FIGHT_TIME = 45; //45s
}
