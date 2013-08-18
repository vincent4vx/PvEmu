package game;

import game.objects.Player;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GameAction {
    private static ConcurrentHashMap<Player, HashMap<Integer, GameAction>> actions = new ConcurrentHashMap<>();
    
    public Player _player;
    public int actionID;
    public String args;
    public int id;
    
    private GameAction(Player p, int actionID, String args, int id){
        _player = p;
        this.actionID = actionID;
        this.args = args;
    }
    
    /**
     * Ajoute une nouvelle GA dans la liste d'attente
     * @param p
     * @param actionID
     * @param args 
     */
    public static GameAction create(Player p, int actionID, String args){
        HashMap<Integer, GameAction> p_actions;
        
        if(actions.containsKey(p)){
            p_actions = actions.get(p);
        }else{
            p_actions = new HashMap<>();
        }
        
        int id = p_actions.size();
        GameAction GA = new GameAction(p, actionID, args, id);
        
        p_actions.put(id, GA);
        
        actions.put(p, p_actions);
        
        return GA;
    }
    
    /**
     * récupère une game action
     * @param p
     * @param id
     * @return 
     */
    public static GameAction get(Player p, int id){
        if(!actions.containsKey(p)){
            return null;
        }
        HashMap<Integer, GameAction> p_actions = actions.get(p);
        
        if(!p_actions.containsKey(id)){
            return null;
        }
        
        return p_actions.get(id);
    }
}
