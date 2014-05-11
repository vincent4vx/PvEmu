package org.pvemu.game.objects.map;

import java.util.List;
import org.pvemu.game.fight.FightFactory;
import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.monster.MonsterGroup;
import org.pvemu.game.objects.player.Player;
import org.pvemu.game.triggeraction.Trigger;
import org.pvemu.game.triggeraction.TriggerActionHandler;
import org.pvemu.game.triggeraction.TriggerFactory;

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
        if(player.getMap().canFight()){
            MonsterGroup group = player.getMap().getMonsterGroupByCell(id);
            if(group != null){
                FightFactory.pvm(player, group);
                return;
            }
        }
        List<Trigger> triggers = TriggerFactory.getTriggersOnCell(map, id);
        
        if(triggers == null)
            return;
        
        for(Trigger trigger : triggers){
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

    /**
     * Test if the cell block the line of sight
     * @return 
     */
    public boolean isSightBlock() {
        return sightBlock;
    }
}
