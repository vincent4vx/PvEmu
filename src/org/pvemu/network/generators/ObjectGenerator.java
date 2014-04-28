/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.pvemu.game.effect.EffectData;
import org.pvemu.game.effect.EffectsHandler;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.inventory.Inventory;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.game.objects.item.itemset.ItemSetHandler;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ObjectGenerator {
    private Map<Integer, String> itemsEffects = new HashMap<>();
    
    public String generateInventoryEntry(InventoryEntry entry){
        return new StringBuilder(12 + entry.stats.length())
                .append(Integer.toHexString(entry.id))
                .append('~').append(Integer.toHexString(entry.item_id))
                .append('~').append(Integer.toHexString(entry.qu))
                .append('~').append(ItemPosition.DEFAULT_POSITION == entry.position ? "" : Integer.toHexString(entry.position))
                .append('~').append(generateObjectData(entry))
                .toString();
    }
    
    public String generateStats(Stats stats){
        StringBuilder s = new StringBuilder(stats.getAll().size() * 16);
        
        for(Map.Entry<Stats.Element, Short> curElem : stats.getAll()){
            int jet = curElem.getValue();
            if(jet == 0){
                continue;
            }
            int elemID = curElem.getKey().getId(jet < 0);
            s.append(Integer.toHexString(elemID)).append('#').append(Integer.toHexString(jet)).append("#0#0#").append("0d0+").append(jet).append(',');
        }
        
        return s.toString();
    }
    
    public String generateObjectData(InventoryEntry entry){
        return generateItemTemplateEffects(entry.item_id) + entry.stats;
    }
    
    public String generateItemTemplateEffects(int id){
        if(!itemsEffects.containsKey(id)){
            ItemTemplate template = DAOFactory.item().getById(id);
            
            if(template == null)
                return "";
            
            StringBuilder strEffects = new StringBuilder();
            
            for(String effect : Utils.split(template.statsTemplate, ",")){
                try{
                    short effectID = Short.parseShort(Utils.split(effect, "#")[0], 16);
                    
                    if(EffectsHandler.instance().getEffect(effectID) != null)
                        strEffects.append(effect).append(',');
                }catch(Exception e){
                    Loggin.warning("cannot generate effects string %s for template %d", effect, id);
                }
            }

            itemsEffects.put(id, strEffects.toString());
        }
        
        return itemsEffects.get(id);
    }
    
    protected String generateEffects(Set<EffectData> effects){
        StringBuilder s = new StringBuilder(effects.size() * 16);
        
        
        for(EffectData data : effects){
            s.append(Integer.toHexString(data.getEffect().id())).append('#')
                    .append(Integer.toHexString(data.getMin())).append('#')
                    .append(Integer.toHexString(data.getMax())).append("#0#");
            
            int p = data.getMax() - data.getMin();
            int d = data.getMin() - p;
            int e = data.getMax() - p;
            
            s.append(d).append('d').append(e).append('+').append(p).append(',');
        }
        
        return s.toString();
    }
    
    public String generateInventory(Inventory inventory){
        StringBuilder s = new StringBuilder(32 * inventory.getItems().size());
        
        for(GameItem item : inventory.getItems()){
            s.append(generateInventoryEntry(item.getEntry())).append(';');
        }
        
        return s.toString();
    }
     
    public String generateMoveItem(InventoryEntry entry){
        return new StringBuilder(8).append(entry.id).append('|').append(entry.position).toString();
    }
    
    public String generateQuantityChange(InventoryEntry entry){
        return new StringBuilder(8).append(entry.id).append('|').append(entry.qu).toString();
    }
    
    public String generateItemSet(int itemset, ItemSetHandler handler){
        
        Set<GameItem> items = handler.getItemsInItemSet(itemset);
        
        if(items == null || items.isEmpty()){
            return "-" + itemset;
        }
        StringBuilder packet = new StringBuilder();
        
        packet.append('+').append(itemset).append('|');
        
        for(GameItem item : items){
            packet.append(item.getTemplate().id).append(';');
        }
        
        packet.append('|').append(generateStats(handler.getStatsByItemSet(itemset)));
        
        return packet.toString();
    }
}
