/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map.interactiveobject.type;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.map.interactiveobject.actions.InteractiveObjectAction;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class InteractiveObjectType {
    final private Map<Integer, InteractiveObjectAction> actions = new HashMap<>();
    
    /**
     * Get all ids of this type of object
     * @return
     */
    abstract public int[] objIDs();
    
    /**
     * Register a new action into the type
     * @param action action tu register
     */
    public void registerAction(InteractiveObjectAction action){
        actions.put(action.id(), action);
    }
    
    /**
     * start an action
     * @param IO current interactive object
     * @param player perfomer
     * @param actionID action to perform
     */
    public void startAction(InteractiveObject IO, Player player, int actionID){
        InteractiveObjectAction action = actions.get(actionID);
        
        if(action == null){
            Loggin.debug("Cannot found IOAction %d for %s", actionID, IO);
            return;
        }
        
        if(!player.corresponds(action.condition())){
            Loggin.debug("Player don't match with conditions");
            action.onError(IO, player);
            return;
        }
        
        action.startAction(IO, player);
    }
}
