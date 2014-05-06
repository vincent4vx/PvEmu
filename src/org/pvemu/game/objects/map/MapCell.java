package org.pvemu.game.objects.map;

import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.triggeraction.Trigger;
import org.pvemu.game.triggeraction.TriggerActionHandler;
import org.pvemu.game.triggeraction.TriggerFactory;
import org.pvemu.jelly.utils.Pair;

public class MapCell {

    final private short id;
    final private short map;
    final private boolean walkable;
    final private InteractiveObject obj;
    final private boolean sightBlock;

    public MapCell(short id, short map, boolean walkable, boolean sightBlock, InteractiveObject obj) {
        this.id = id;
        this.map = map;
        this.walkable = walkable;
        this.obj = obj;
        this.sightBlock = sightBlock;
    }

    public short getMap() {
        return map;
    }

    public short getID() {
        return id;
    }

    public boolean isWalkable() {
        return walkable;
    }
    
    /**
     * Perform cells actions (if exists)
     * @param player the player
     */
    public void onArrivedOnCell(Player player){
        for(Trigger trigger : TriggerFactory.getTriggersOnCell(new Pair<>(map, id))){
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

    public boolean isSightBlock() {
        return sightBlock;
    }
}
