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
    public Integer getID();
    
    public short getCellId();
    public byte getOrientation();
    public String getName();
}
