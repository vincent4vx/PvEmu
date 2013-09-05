package game.objects.inventory;

import java.util.Collection;
import java.util.HashMap;
import jelly.Loggin;
import models.InventoryEntry;
import models.dao.DAOFactory;

public class Inventory {
    private HashMap<Integer, GameItem> items = new HashMap<>();
    private HashMap<ItemStats, GameItem> itemsByStats = new HashMap<>();
    private HashMap<Byte, GameItem> itemsByPos = new HashMap<>();
    private InventoryAble owner;
    
    public Inventory(InventoryAble owner){
        this.owner = owner;
        
        for(InventoryEntry IE : DAOFactory.inventory().getByOwner(owner.getOwnerType(), owner.getID())){
            GameItem GI = IE.getGameItem();
            items.put(GI.getID(), GI);
            itemsByStats.put(GI.getItemStats(), GI);
            
            if(IE.position != -1){
                if(!GI.canMove(IE.position)){
                    GI.changePos((byte)-1);
                    continue;
                }
                itemsByPos.put(IE.position, GI);
            }
        }
    }
    
    /**
     * Ajoute l'item à l'inventaire
     * @param IS
     * @param qu 
     */
    public void addItem(ItemStats IS, int qu){
        if(itemsByStats.containsKey(IS)){
            GameItem GI = itemsByStats.get(IS);
            GI.addQuantity(qu);
            owner.onQuantityChange(GI.getID(), GI.getInventoryEntry().qu);
        }else{
            GameItem GI = new GameItem(owner, IS, qu, (byte)-1);
            addGameItem(GI);
        }
    }
    
    private void addGameItem(GameItem GI){
        items.put(GI.getID(), GI);
        itemsByStats.put(GI.getItemStats(), GI);
        
        if(GI.getInventoryEntry().position != -1){
            itemsByPos.put(GI.getInventoryEntry().position, GI);
        }
        owner.onAddItem(GI);
    }
    
    /**
     * Déplace l'item demandé si possible
     * @param id
     * @param qu
     * @param pos
     * @return 
     */
    public boolean moveItem(int id, int qu, byte pos){
        GameItem GI = items.get(id);
        
        if(GI == null){
            return false;
        }
        
        if(!GI.canMove(pos)){
            return false;
        }
        
        if(itemsByPos.containsKey(pos)){
            moveGameItem(itemsByPos.get(pos), (byte)-1);
        }
        
        if(!owner.canMoveItem(GI, qu, pos)){
            return false;
        }
        
        if(qu < 1){
            qu = 1;
        }
        
        if(qu > GI.getInventoryEntry().qu){
            qu = GI.getInventoryEntry().qu;
        }
        
        if(qu == GI.getInventoryEntry().qu){
            GI = moveGameItem(GI, pos);
        }else{
            GameItem nGI = new GameItem(owner, GI.getItemStats(), qu, pos);
            GI.addQuantity(-qu);
            owner.onQuantityChange(GI.getID(), GI.getInventoryEntry().qu);
            addGameItem(nGI);
        }
        
        owner.onMoveItemSuccess(GI, pos);
        return true;
    }
    
    /**
     * Déplace tout les items du GI
     * @param GI
     * @param pos 
     */
    public GameItem moveGameItem(GameItem GI, byte pos){
        byte lastPos = GI.getInventoryEntry().position;
        
        if(lastPos != -1){
            itemsByPos.remove(lastPos);
        }
        
        itemsByStats.remove(GI.getItemStats());
        GI.changePos(pos);
        
        if(itemsByStats.containsKey(GI.getItemStats())){
            GameItem oGI = itemsByStats.get(GI.getItemStats());
            oGI.addQuantity(GI.getInventoryEntry().qu);
            owner.onQuantityChange(oGI.getID(), oGI.getInventoryEntry().qu);
            GI.changePos(lastPos);
            deleteGameItem(GI);
            return oGI;
        }else{
            if(pos != -1){
                itemsByPos.put(pos, GI);
            }
            itemsByStats.put(GI.getItemStats(), GI);
            owner.onMoveItem(GI.getID(), pos);
            return GI;
        }
    }
    
    private void deleteGameItem(GameItem GI){
        Loggin.debug("Delete de l'item %d", GI.getID());
        int id = GI.getID();
        items.remove(id);
        itemsByStats.remove(GI.getItemStats());
        itemsByPos.remove(GI.getInventoryEntry().position);
        GI.delete();
        owner.onDeleteItem(id);
    }
    
    public HashMap<Byte, GameItem> getItemsByPos(){
        return itemsByPos;
    }
    
    public Collection<GameItem> getItems(){
        return items.values();
    }
    
    /**
     * Retourne l'item à la position indiqué
     * @param pos
     * @return 
     */
    public GameItem getItemByPos(byte pos){
        return itemsByPos.get(pos);
    }
    
    /**
     * Indique si la place est disponible ou non (déjà prise)
     * @param pos
     * @return 
     */
    public boolean isAvailable(byte pos){
        return !itemsByPos.containsKey(pos);
    }
    
    /**
     * Génère les données à envoyer au client
     * @return 
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        
        for(GameItem GI : items.values()){
            sb.append(GI.toString()).append(';');
        }
        
        return sb.toString();
    }
}
