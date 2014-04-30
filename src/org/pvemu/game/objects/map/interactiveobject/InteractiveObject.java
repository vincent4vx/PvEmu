package org.pvemu.game.objects.map.interactiveobject;

import org.pvemu.game.objects.map.interactiveobject.type.InteractiveObjectType;
import org.pvemu.game.objects.player.Player;

final public class InteractiveObject {
    final private InteractiveObjectType type;
    final private int objID;
    final private short map;
    final private short cell;
    
    InteractiveObject(InteractiveObjectType type, int objID, short cell, short map){
        this.type = type;
        this.objID = objID;
        this.cell = cell;
        this.map = map;
    }
    
    public int getObjID(){
        return objID;
    }

    public short getMap() {
        return map;
    }

    public short getCell() {
        return cell;
    }
    
    /**
     * Start an IOAction
     * @param player performer
     * @param action ID of the action
     */
    public void startAction(Player player, int action){
        type.startAction(this, player, action);
    }

    @Override
    public String toString() {
        return "InteractiveObject{" + "objID=" + objID + ", map=" + map + ", cell=" + cell + '}';
    }
}
