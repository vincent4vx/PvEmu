package game.objects.dep;

import java.util.Map.Entry;
import java.util.Objects;
import jelly.Utils;
import models.InventoryEntry;
import models.ItemTemplate;
import models.dao.DAOFactory;

public class ItemStats {
    private Stats stats = new Stats();
    private ItemTemplate _template;
    private byte position = -1;
    
    public ItemStats(ItemTemplate T, boolean useMax){
        _template = T;
        parseStats(T.statsTemplate, useMax);
    }
    
    public ItemStats(ItemTemplate T){
        this(T, false);
    }
    
    public ItemStats(InventoryEntry I){
        _template = DAOFactory.item().getById(I.item_id);
        position = I.position;
        parseStats(I.stats, false);
    }
    
    private void parseStats(String stats, boolean useMax){
        for(String e : stats.split(",")){
            if(e.isEmpty()){
                continue;
            }
            String[] elem_data = e.split("#");
            
            int elemID = Integer.parseInt(elem_data[0], 16);
            int min = Integer.parseInt(elem_data[1], 16);
            int max = Integer.parseInt(elem_data[2], 16);
            
            max = min > max ? min : max;
            
            int jet = useMax ? max : Utils.rand(min, max);
            
            this.stats.add(elemID, jet);
        }
    }
    
    @Override
    public int hashCode(){
        return position + stats.hashCode() + _template.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemStats other = (ItemStats) obj;
        if(other.position != position){
            return false;
        }
        if (!Objects.equals(this.stats, other.stats)) {
            return false;
        }
        if (!Objects.equals(this._template, other._template)) {
            return false;
        }
        return true;
    }
    
    /**
     * Retourne l'id du template de l'item
     * @return 
     */
    public int getID(){
        return _template.id;
    }
    
    public ItemTemplate getTemplate(){
        return _template;
    }
    
    /**
     * Transforme les stats en String pour enregistré dans l'inventaire (et envoyer le packet)
     * @return 
     */
    public String statsToString(){
        StringBuilder s = new StringBuilder();
        
        for(Entry<Stats.Element, Integer> curElem : stats.getAll()){
            int jet = curElem.getValue();
            if(jet == 0){
                continue;
            }
            int elemID = curElem.getKey().getId(jet < 0);
            s.append(Integer.toHexString(elemID)).append('#').append(Integer.toHexString(jet)).append("#0#0#").append("0d0+").append(jet).append(',');
        }
        
        return s.toString();
    }
    
    /**
     * Appelé en cas de GameItem.move
     * Ne pas utiliser directement !
     * @param pos 
     */
    public void setPosition(byte pos){
        position = pos;
    }
}
