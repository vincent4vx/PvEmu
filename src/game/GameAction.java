package game;

import game.objects.Player;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GameAction {

    private static ConcurrentHashMap<Player, HashMap<Integer, GameAction>> actions = new ConcurrentHashMap<>();
    public Player _player;
    public int actionID;
    public Object[] args;
    public int id = 0;
    private HashMap<Object, Object> _attachement = new HashMap<>();

    public GameAction(Player p, int actionID, Object[] args) {
        _player = p;
        this.actionID = actionID;
        this.args = args;

        if (actions.containsKey(p)) {
            Object[] keys = actions.get(p).keySet().toArray();
            if (keys.length > 0) {
                id = (int) keys[keys.length - 1] + 1;
            }
        }
    }

    public void attach(Object key, Object o) {
        _attachement.put(key, o);
    }

    public Object get(Object key) {
        return _attachement.get(key);
    }

    /**
     * sauvegarde la GA, et la met en attente
     */
    public void save() {
        HashMap<Integer, GameAction> p_actions;

        if (actions.containsKey(_player)) {
            p_actions = actions.get(_player);
        } else {
            p_actions = new HashMap<>();
        }

        p_actions.put(id, this);
        actions.put(_player, p_actions);
    }

    /**
     * Supprime la GA de la liste d'attente
     */
    public void delete() {
        if (!actions.containsKey(_player)) {
            return; //ne devrait pas arriver
        }

        actions.get(_player).remove(id);
    }

    /**
     * récupère une game action
     *
     * @param p
     * @param id
     * @return
     */
    public static GameAction get(Player p, int id) {
        if (!actions.containsKey(p)) {
            return null;
        }
        HashMap<Integer, GameAction> p_actions = actions.get(p);

        return p_actions.get(id);
    }
}
