/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.triggeraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.pvemu.jelly.Shell;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.NpcResponseAction;
import org.pvemu.models.dao.DAOFactory;


/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TriggerFactory {
    final static private Map<Pair<Short, Short>, List<Trigger>> mapTriggers = new HashMap<>();
    
    static public List<Trigger> getTriggersOnCell(Pair<Short, Short> position){
        if(!mapTriggers.containsKey(position)){
            storeTriggerList(position, DAOFactory.trigger().getByCell(position));
        }
        
        return mapTriggers.get(position);
    }
    
    static private Trigger newTrigger(org.pvemu.models.Trigger trigger){
        return new Trigger(
                trigger.actionID,
                Utils.split(trigger.actionArgs, ",")
        );
    }
    
    static private void storeTriggerList(Pair<Short, Short> position, List<org.pvemu.models.Trigger> list){
        List<Trigger> triggers = new ArrayList<>();
        for(org.pvemu.models.Trigger t : list){
            triggers.add(newTrigger(t));
        }
        mapTriggers.put(position, triggers);
    }
    
    static public Trigger newResponseAction(NpcResponseAction nra){
        return new Trigger(
                nra.action_id,
                nra.args
        );
    }
    
    static public void preloadTriggers(){
        Shell.print("Loading triggers : ", Shell.GraphicRenditionEnum.YELLOW);
        
        Map<Pair<Short, Short>, List<org.pvemu.models.Trigger>> models = DAOFactory.trigger().getAll();
        int count = 0;
        
        for(Entry<Pair<Short, Short>, List<org.pvemu.models.Trigger>> entry : models.entrySet()){
            storeTriggerList(entry.getKey(), entry.getValue());
            count += entry.getValue().size();
        }
        
        Shell.println(count + " triggers loaded", Shell.GraphicRenditionEnum.GREEN);
    }
}
