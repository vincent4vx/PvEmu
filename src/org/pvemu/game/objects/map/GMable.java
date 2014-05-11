package org.pvemu.game.objects.map;

public interface GMable {
    final static public byte MONSTER   = -3;
    final static public byte PERCEPTOR = -6;
    
    /**
     * Données pour affichage sur map
     * @return Le param du packet sous forme de string
     */
    public String getGMData();
    
    /**
     * Retourne l'id sur map
     * @return 
     */
    public int getID();
    
    /**
     * Get the current cell
     * @return 
     */
    public short getCellId();
    
    /**
     * Get the orientation
     * @return 
     */
    public byte getOrientation();
    
    /**
     * get the name
     * @return 
     */
    public String getName();
    
    /**
     * get the alignment
     * @return 
     */
    public byte getAlignment();
}
