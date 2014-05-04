package org.pvemu.game.objects.dep;

public interface Creature {

    /**
     * Get all the stats
     * @return 
     */
    abstract public Stats getTotalStats();

    /**
     * Retourne le nom de la créature
     *
     * @return
     */
    public String getName();

    /**
     * Retourne l'initiative
     *
     * @return
     */
    public int getInitiative();

    public short getLevel();

    public short getGfxID();

    public String[] getColors();
}
