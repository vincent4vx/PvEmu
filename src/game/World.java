package game;

import game.objects.Player;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class World {
    
    private static ConcurrentHashMap<Integer, Player> _online = new ConcurrentHashMap<>();
    
    /**
     * Ajoute un joueur dans la liste des joueurs en ligne
     * @param p 
     */
    public static void addOnline(Player p){
        _online.put(p.getID(), p);
    }
    
    /**
     * Supprime un joueur de la liste des connectés
     * @param p 
     */
    public static void removeOnline(Player p){
        _online.remove(p.getID());
    }
    
    /**
     * Retourne la liste de tout les joueurs en ligne
     * @return 
     */
    public static Collection<Player> getOnlinePlayers(){
        return _online.values();
    }
    
    /**
     * retourne la liste des joueurs correspondant au gm lvl donné
     * @param c = : gm level strictement identique, + : gm level suppérieur, - : gm level inférieur
     * @param level
     * @return 
     */
    public static Collection<Player> getOnlinePlayers(char c, byte level){
        Collection<Player> players = new ArrayList<>();
        
        for(Player P : _online.values()){
            switch(c){
                case '+':
                    if(P.getAccount().level > level){
                        players.add(P);
                    }
                    break;
                case '-':
                    if(P.getAccount().level < level){
                        players.add(P);
                    }
                    break;
                case '=':
                    if(P.getAccount().level == level){
                        players.add(P);
                    }
                    break;
            }
        }
        
        return players;
    }
}
