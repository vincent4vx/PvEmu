package game.objects.dep;

public interface GMable {
    /**
     * Données pour affichage sur map
     * @return 
     */
    public String getGMData();
    
    /**
     * Retourne l'id sur map
     * @return 
     */
    public int getID();
}
