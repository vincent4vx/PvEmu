/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.triggeraction;

import java.util.HashMap;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class TriggerActionHandler {
    final static private TriggerActionHandler instance = new TriggerActionHandler();
    final private HashMap<Short, TriggerAction> actions = new HashMap<>();
    
    private TriggerActionHandler(){
        registerAction(new TeleportAction());
        registerAction(new DialogResponseAction());
        registerAction(new GoAstrubAction());
    }
    
    public void registerAction(TriggerAction action){
        actions.put(action.actionId(), action);
    }
    
    public void triggerAction(Trigger trigger, Player player){
        TriggerAction action = actions.get(trigger.getActionID());
        
        if(action == null){
            Loggin.debug("Action non trouvée : %s", trigger);
            return;
        }
        
        if(!player.corresponds(action.condition())){
            Loggin.debug("Action non %s autorisée pour %s", trigger, player.getName());
            return;
        }
        
        Loggin.debug("[%s] Déclanchement de l'action %s", player.getName(), trigger);
        action.perform(trigger, player);
    }
    
    static public TriggerActionHandler instance(){
        return instance;
    }
}
