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

    /**
     * The creature level
     * @return 
     */
    public short getLevel();

    /**
     * the gfx
     * @return 
     */
    public short getGfxID();

    /**
     * the colors
     * @return 
     */
    public String[] getColors();
}
