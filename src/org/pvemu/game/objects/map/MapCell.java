package org.pvemu.game.objects.map;

import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.pvemu.game.objects.monster.MonsterGroup;
import org.pvemu.game.triggeraction.TriggerActionHandler;
import org.pvemu.game.triggeraction.TriggerFactory;

public class MapCell {

    final private short id;
    final private short map;
    final private boolean walkable;
    final private InteractiveObject obj;
    final private boolean canSight;
    /*final private ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<Integer, MonsterGroup> monstersGroups = new ConcurrentHashMap<>();*/
    //final private Collection<Player> players = Collections.synchronizedCollection(new ArrayList<Player>());
    final private Collection<MonsterGroup> monsterGroups = Collections.synchronizedCollection(new ArrayList<MonsterGroup>());
    final private List<org.pvemu.game.triggeraction.Trigger> actions;

    public MapCell(short id, short map, boolean walkable, boolean canSight, InteractiveObject obj, List<org.pvemu.game.triggeraction.Trigger> actions) {
        this.id = id;
        this.map = map;
        this.walkable = walkable;
        this.obj = obj;
        this.canSight = canSight;
        this.actions = actions;
    }

    public short getMap() {
        return map;
    }

    public Short getID() {
        return id;
    }

    public boolean isWalkable() {
        return walkable;
    }

    /*public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public Collection<Player> getPlayers() {
        return players;
    }*/

    public Collection<MonsterGroup> getMonsterGroups() {
        return monsterGroups;
    }
    
    public void addMonsterGroup(MonsterGroup group){
        monsterGroups.add(group);
    }
    
    public void removeMonsterGroup(MonsterGroup group){
        monsterGroups.remove(group);
    }

    /**
     * Ajoute une action sur la cellule
     *
     * @param trigger
     */
    public void addTrigger(org.pvemu.models.Trigger trigger) {
        actions.add(TriggerFactory.newTrigger(trigger));
    }

    /**
     * Effectue les actions sur la cellule (triggers)
     *
     * @param player
     */
    public void performCellAction(Player player) {
        for (org.pvemu.game.triggeraction.Trigger trigger : actions) {
            Loggin.debug("[%s] Déclanchement du trigger en [%d;%d] : %s", player.getName(), map, id, trigger);
            TriggerActionHandler.instance().triggerAction(trigger, player);
        }
    }

    /**
     * Retourne l'id de l'objet sur la cellule
     *
     * @return
     */
    public InteractiveObject getObj() {
        return obj;
    }
}
