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
    public Short getInitiative();

    public Short getLevel();

    public Short getGfxID();

    public String[] getColors();
}
