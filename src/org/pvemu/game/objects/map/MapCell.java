/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.objects.map;

import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.game.objects.monster.MonsterGroup;
import org.pvemu.game.triggeraction.TriggerActionHandler;
import org.pvemu.game.triggeraction.TriggerFactory;

public class MapCell {

    final private short id;
    final private short map;
    final private boolean walkable;
    final private InteractiveObject obj;
    final private boolean canSight;
    final private ConcurrentHashMap<Integer, Player> players = new ConcurrentHashMap<>();
    final private ConcurrentHashMap<Integer, MonsterGroup> monstersGroups = new ConcurrentHashMap<>();
    final private ArrayList<org.pvemu.game.triggeraction.Trigger> actions = new ArrayList<>();

    public MapCell(short id, short map, boolean walkable, boolean canSight, InteractiveObject obj) {
        this.id = id;
        this.map = map;
        this.walkable = walkable;
        this.obj = obj;
        this.canSight = canSight;
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

    public void removePlayer(int id) {
        players.remove(id);
    }

    public void addPlayer(Player p) {
        players.put(p.getID(), p);
    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }

    public ConcurrentHashMap<Integer, MonsterGroup> getMonstersGroups() {
        return monstersGroups;
    }
    
    public void addMonsterGroup(MonsterGroup group){
        monstersGroups.put(group.getID(), group);
    }
    
    public void removeMonsterGroup(MonsterGroup group){
        monstersGroups.remove(group.getID());
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
