package org.pvemu.game.objects.map;

public interface GMable {
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
}
