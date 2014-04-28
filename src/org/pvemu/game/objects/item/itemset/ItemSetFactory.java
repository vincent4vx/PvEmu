package org.pvemu.game.objects.item.itemset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.ItemSet;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class ItemSetFactory {
    final static private Map<Integer, ItemSetData> itemSets = new HashMap<>();
    
    static public ItemSetData getById(int id){
        if(!itemSets.containsKey(id)){
            ItemSet model = DAOFactory.getItemSet().find(id);
            
            if(model == null)
                return null;
            
            itemSets.put(id, getByModel(model));
        }
        
        return itemSets.get(id);
    }
    
    static private ItemSetData getByModel(ItemSet is){
        String[] strItems = Utils.split(is.items, ",");
        Set<Integer> items = new HashSet<Integer>(strItems.length);
        
        for(String strId : strItems){
            strId = strId.trim();
            
            if(strId.isEmpty())
                continue;
            try{
                items.add(Integer.parseInt(strId));
            }catch(NumberFormatException e){
                Loggin.error("Cannot parse item list of " + is, e);
                continue;
            }
        }
        
        String[] strStats = Utils.split(is.bonus, ";");
        List<Stats> stats = new ArrayList<>(items.size() - 1);
        
        for(int i = 0; i < items.size() - 1 && i < strStats.length; ++i){ //don't make more stats than items
            stats.add(parseStats(strStats[i]));
        }
        
        return new ItemSetData(is, items, stats);
    }
    
    static private Stats parseStats(String str){
        Stats stats = new Stats();
        
        for(String s : Utils.split(str, ",")){
            String[] tmp = Utils.split(s.trim(), ":");
            int elemID = Integer.parseInt(tmp[0].trim());
            short qu = Short.parseShort(tmp[1].trim());
            stats.add(elemID, qu);
        }
        
        return stats;
    }
}
