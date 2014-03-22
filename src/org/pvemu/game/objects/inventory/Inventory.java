package org.pvemu.game.objects.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.inventory.entrystate.EntryState;
import org.pvemu.game.objects.inventory.entrystate.EntryStateFactory;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.game.objects.item.factory.ItemsFactory;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.dao.DAOFactory;

final public class Inventory {
    
    final private HashMap<Integer, GameItem> items = new HashMap<>();
    final private HashMap<Byte, ArrayList<GameItem>> itemsByPos = new HashMap<>();
    final private ArrayList<EntryState> waitingStates = new ArrayList<>();
    final private Inventoryable owner;
    
    public Inventory(Inventoryable owner){
        this.owner = owner;
        
        for(InventoryEntry entry : DAOFactory.inventory().getByOwner(owner.getOwnerType(), owner.getID())){
            GameItem item = ItemsFactory.recoverItem(entry);
            
            if(!canAddItem(item)){
                item.getEntry().position = ItemPosition.DEFAULT_POSITION;
            }
            
            GameItem other = getSameItem(item);
            
            if(other == null)
                addGameItem(item);
            else
                stackGameItem(item, other);
        }
    }
    
    public void addOrStackItem(GameItem item){
        if(!canAddItem(item)){
            waitingStates.add(EntryStateFactory.errorState(item.getEntry(), "Permission refusée"));
            return;
        }
        
        GameItem other = getSameItem(item);
        
        if(other == null){
            addGameItem(item);
            waitingStates.add(EntryStateFactory.addState(item.getEntry()));
            return;
        }
        
        stackGameItem(item, other);
        waitingStates.add(EntryStateFactory.stackState(other.getEntry()));
        waitingStates.add(EntryStateFactory.deleteState(item.getEntry()));
    }
    
    /**
     * Test if the item can be add to the inventory
     * @param item the item to test
     * @return true if it can be add
     */
    public boolean canAddItem(GameItem item){
        return canMoveItem(item, item.getEntry().position);
    }
    
    /**
     * Test if the item can be move to the position
     * @param item item to test
     * @param position the target position
     * @return true if ok
     */
    public boolean canMoveItem(GameItem item, byte position){
        ItemPosition pos = ItemPosition.getByPosID(position);
        
        if(!pos.isValidPosition(item))
            return false;
        
        if(pos.isMultiplePlace()) //can have multiple items in this position
            return true;
        
        ArrayList<GameItem> itemsInPos = itemsByPos.get(position);
        
        if(itemsInPos == null || itemsInPos.isEmpty()) //the place is free
            return true;
        
        return !pos.isWearPlace() && itemsInPos.get(0).isSameItem(item); //need stack
    }
    
    /**
     * Test if there are same item in inventory
     * @param item the item to test
     * @return true if exists same item
     */
    public boolean isItemExists(GameItem item){
        return getSameItem(item) != null;
    }
    
    private GameItem getSameItem(GameItem item){
        return getSameItemInPos(item, item.getEntry().position);
    }
    
    private GameItem getSameItemInPos(GameItem item, byte position){
        for(GameItem other : getItemsOnPos(position)){
            if(item.isSameItem(other))
                return other;
        }
        
        return null;
    }
    
    /**
     * Stack the item if possible
     * @param item the item to stack
     */
    public void stackItem(GameItem item){
        if(!canAddItem(item)){
            waitingStates.add(EntryStateFactory.errorState(item.getEntry(), "Permission refusée"));
            return;
        }
        
        GameItem other = getSameItem(item);
        
        if(other == null){
            waitingStates.add(EntryStateFactory.errorState(item.getEntry(), "Necessite un add à la place d'un stack"));
            return;
        }
        
        stackGameItem(item, other);
        waitingStates.add(EntryStateFactory.stackState(item.getEntry()));
    }
    
    /**
     * Add the item if possible
     * @param item the item to add
     */
    public void addItem(GameItem item){
        if(!canAddItem(item)){
            waitingStates.add(EntryStateFactory.errorState(item.getEntry(), "Permission refusée"));
            return;
        }
        
        if(getSameItem(item) != null){
            waitingStates.add(EntryStateFactory.errorState(item.getEntry(), "Necessite un stack à la place d'un add"));
            return;
        }
        
        addGameItem(item);
        waitingStates.add(EntryStateFactory.addState(item.getEntry()));
    }
    
    /**
     * Add the game item to inventory
     * @warning this method don't test if the item is valid !
     * @param item the item to add
     */
    private void addGameItem(GameItem item){
        if(!item.getEntry().isCreated()){
            DAOFactory.inventory().create(item.getEntry());
        }
        
        items.put(item.getID(), item);
        
        ArrayList<GameItem> list = itemsByPos.get(item.getEntry().position);
        
        if(list == null){
            list = new ArrayList<>();
            itemsByPos.put(item.getEntry().position, list);
        }
        
        list.add(item);
    }
    
    /**
     * Stack the src game item to the dest game item
     * @warning this method don't test if the item is valid !
     * @param src the item to stack
     * @param dest the stack item
     */
    private void stackGameItem(GameItem src, GameItem dest){
        dest.getEntry().qu += src.getEntry().qu;
        
        if(src.getEntry().isCreated())
            delete(src);
    }
    
    /**
     * Move the item to dest_pos
     * @param item item to move
     * @param dest_qu quantity to move
     * @param dest_pos the target position
     */
    public void moveItem(GameItem item, int dest_qu, byte dest_pos){
        if(dest_pos == item.getEntry().position 
                || !canMoveItem(item, dest_pos)
                || !items.containsValue(item)
                || item.getEntry().qu > dest_qu){
            waitingStates.add(EntryStateFactory.errorState(item.getEntry(), "Permission refusée"));
            return;
        }
        
        //GameItem newItem;
        if(dest_qu == item.getEntry().qu){
            getItemsOnPos(item.getEntry().position).remove(item);
            item.getEntry().position = dest_pos;
            //newItem = item;
            
            GameItem other = getSameItem(item);
            if(other != null){
                stackGameItem(item, other);
                waitingStates.add(EntryStateFactory.stackState(other.getEntry()));
                waitingStates.add(EntryStateFactory.deleteState(item.getEntry()));
            }else{
                addGameItem(item);
                waitingStates.add(EntryStateFactory.moveState(item.getEntry()));
            }
        }else{
            GameItem newItem = ItemsFactory.copyItem(item, owner, dest_qu, dest_pos);
            item.getEntry().qu -= dest_qu;
            waitingStates.add(EntryStateFactory.stackState(item.getEntry()));
            addOrStackItem(newItem);
        }
    }
    
    public HashMap<Byte, ArrayList<GameItem>> getItemsByPos(){
        return itemsByPos;
    }
    
    /**
     * Get the list of items
     * @return 
     */
    public Collection<GameItem> getItems(){
        return items.values();
    }
    
    /**
     * Get the list of items at pos
     * @param pos the position of items
     * @return the list of the items
     */
    public ArrayList<GameItem> getItemsOnPos(byte pos){
        ArrayList<GameItem> list = itemsByPos.get(pos);
        
        if(list == null){
            list = new ArrayList<>();
            itemsByPos.put(pos, list);
        }
        
        return list;
    }
    
    /**
     * Get the list of items at pos
     * @param pos the position of items
     * @return the list of the items
     */
    public ArrayList<GameItem> getItemsOnPos(ItemPosition pos){
        ArrayList<GameItem> list = new ArrayList<>();
        
        for(byte posID : pos.getPosIds()){
            list.addAll(getItemsOnPos(posID));
        }
        
        return list;
    }
    
    /**
     * Get the item
     * @param id id of the item
     * @return 
     */
    public GameItem getItemById(int id){
        return items.get(id);
    }
    
    /**
     * Save all the inventory
     */
    public void save(){
        for(GameItem item : items.values()){
            save(item);
        }
    }
    
    /**
     * Save the item
     * @param item item to save
     */
    private void save(GameItem item){
        DAOFactory.inventory().update(item.getEntry());
    }
    
    /**
     * Delete the item
     * @param item item to delete
     */
    private void delete(GameItem item){
        items.remove(item.getID());
        itemsByPos.get(item.getEntry().position).remove(item);
    }
    
    public void delete(GameItem item, int quantity){
        if(!items.containsValue(item)){
            waitingStates.add(EntryStateFactory.errorState(item.getEntry(), "Impossible de supprimer un item qui ne vous appartient pas"));
            return;
        }
        
        if(item.getEntry().qu > quantity){
            item.getEntry().qu -= quantity;
            waitingStates.add(EntryStateFactory.stackState(item.getEntry()));
        }else{
            delete(item);
            waitingStates.add(EntryStateFactory.deleteState(item.getEntry()));
        }
    }
    
    public boolean commitStates(IoSession out){
        boolean haveChanges = false;
        for(EntryState state : waitingStates){
            state.commit(out);
            haveChanges = true;
        }
        waitingStates.clear();
        return haveChanges;
    }
}
