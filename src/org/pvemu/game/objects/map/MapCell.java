/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.objects.map;

import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.game.triggeraction.TriggerActionHandler;
import org.pvemu.game.triggeraction.TriggerFactory;

public class MapCell {

    protected GameMap map;
    protected boolean walkable;
    protected InteractiveObject obj;
    protected boolean canSight = true;
    protected ConcurrentHashMap<Integer, Player> _players = new ConcurrentHashMap<>();
    protected short id;
    protected ArrayList<org.pvemu.game.triggeraction.Trigger> actions = new ArrayList<>();

    public MapCell(GameMap map, short cellID, String CellData) {
        this.map = map;
        id = cellID;

        walkable = (((parseHashChar(CellData.charAt(2))) & 56) >> 3) != 0;
        canSight = ((parseHashChar(CellData.charAt(0))) & 1) != 0;
        int layerObject2 = (((parseHashChar(CellData.charAt(0))) & 2) << 12) + (((parseHashChar(CellData.charAt(7))) & 1) << 12) + ((parseHashChar(CellData.charAt(8))) << 6) + parseHashChar(CellData.charAt(9));
        boolean layerObject2Interactive = (((parseHashChar(CellData.charAt(7))) & 2) >> 1) != 0;
        obj = (layerObject2Interactive ? new InteractiveObject(layerObject2, this, this.map) : null);
    }

    private static byte parseHashChar(char c) {
        char[] HASH = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
            'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'
        };

        int count = HASH.length;

        for (byte i = 0; i < count; i++) {
            if (HASH[i] == c) {
                return i;
            }
        }

        return -1;
    }

    public Short getID() {
        return id;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public void removePlayer(int id) {
        _players.remove(id);
    }

    public void addPlayer(Player p) {
        _players.put(p.getID(), p);
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
            Loggin.debug("[%s] Déclanchement du trigger en [%d;%d] : %s", player.getName(), map.id, id, trigger);
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
