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
import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;
import org.pvemu.common.utils.Pair;
import org.pvemu.common.utils.Utils;
import org.pvemu.models.NpcResponseAction;
import org.pvemu.models.dao.DAOFactory;


/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TriggerFactory {
    final static private Map<Short, Map<Short, List<Trigger>>> mapTriggers = new HashMap<>();
    
    static public List<Trigger> getTriggersOnCell(short map, short cell){
        if(!mapTriggers.containsKey(map)){
            loadTriggersByMap(map);
        }
        
        return mapTriggers.get(map).get(cell);
    }
    
    static public void loadTriggersByMap(short map){
        for(Entry<Short, List<org.pvemu.models.Trigger>> entry : DAOFactory.trigger().getByMap(map).entrySet()){
            storeTriggerList(map, entry.getKey(), entry.getValue());
        }
    }
    
    static private Trigger newTrigger(org.pvemu.models.Trigger trigger){
        return new Trigger(
                trigger.actionID,
                Utils.split(trigger.actionArgs, ",")
        );
    }
    
    static private void storeTriggerList(short map, short cell, List<org.pvemu.models.Trigger> list){
        List<Trigger> triggers = new ArrayList<>();
        for(org.pvemu.models.Trigger t : list){
            triggers.add(newTrigger(t));
        }
        
        Map<Short, List<Trigger>> cells = mapTriggers.get(map);
        
        if(cells == null){
            cells = new HashMap<>();
            mapTriggers.put(map, cells);
        }
        
        cells.put(cell, triggers);
    }
    
    static public Trigger newResponseAction(NpcResponseAction nra){
        return new Trigger(
                nra.action_id,
                nra.args
        );
    }
    
    static public void preloadTriggers(){
        Shell.print(I18n.tr(Commons.LOADING, I18n.tr(Commons.TRIGGERS)), Shell.GraphicRenditionEnum.YELLOW);
        
        Map<Pair<Short, Short>, List<org.pvemu.models.Trigger>> models = DAOFactory.trigger().getAll();
        int count = 0;
        
        for(Entry<Pair<Short, Short>, List<org.pvemu.models.Trigger>> entry : models.entrySet()){
            storeTriggerList(
                    entry.getKey().getFirst(), 
                    entry.getKey().getSecond(), 
                    entry.getValue()
            );
            count += entry.getValue().size();
        }
        
        Shell.println(I18n.tr(Commons.TRIGGERS_LOADED, count), Shell.GraphicRenditionEnum.GREEN);
    }
}
