package org.pvemu.common;

import org.pvemu.common.utils.Version;

public class Constants {

    /**
     * The name of the emulator (please don't modify)
     */
    public final static String NAME = "PvEmu";
    
    /**
     * The version of the emulator
     * @see org.pvemu.common.plugin.Plugin#compatibility()
     */
    final static public Version VERSION = new Version(0, 58, 2, "a");
    
    /**
     * The version of the client
     */
    final static public Version DOFUS_VER = new Version(1, 29, 1);
    
    /**
     * The version into int
     * @deprecated use Constants.DOFUS_VER instead
     */
    public final static int DOFUS_VER_ID = 1291;
    
    /**
     * The server id
     * @see org.pvemu.network.realm.input.ServerListPacket
     */
    public final static byte SERV_ID = 1;
    
    /**
     * One year in seconds
     */
    public final static long ONE_YEAR = 31536000000L;
    
    /**
     * The configuration file
     * @see org.pvemu.common.Config
     */
    public final static String CONFIG_FILE = "jelly.conf";
    
    /**
     * Red color into game chat
     */
    public final static String COLOR_RED = "c10000";
    
    /**
     * Green color into game chat
     */
    public final static String COLOR_GREEN = "009900";
    
    /**
     * IDLE time before kick
     */
    public final static int MAX_IDLE_TIME = 900; //15 minutes
    
    /**
     * Correction for real time to IG time
     * @see org.pvemu.network.generators.BasicGenerator
     */
    final static public long TIME_CORRECTION = -1370L * 365 * 24 * 3600 * 1000 + 3600000; //-1370 years + 1 hour
    
    /**
     * Time by turns
     * @see org.pvemu.game.fight.FightUtils#turnTimer(org.pvemu.game.fight.Fight) 
     */
    final static public int TURN_TIME = 30; //30s
    
    /**
     * Start fight countdown
     * @see org.pvemu.game.fight.FightUtils#startCountdownTimer(org.pvemu.game.fight.Fight) 
     */
    final static public int START_FIGHT_TIME = 45; //45s
}
