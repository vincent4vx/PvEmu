package org.pvemu.game.objects.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.game.objects.item.factory.ItemsFactory;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.dao.DAOFactory;

final public class Inventory {
    static public enum MoveState{
        MOVE, STACK, ADD, DELETE, ERROR
    }
    
    final private HashMap<Integer, GameItem> items = new HashMap<>();
    final private HashMap<Byte, ArrayList<GameItem>> itemsByPos = new HashMap<>();
    final private Inventoryable owner;
    
    public Inventory(Inventoryable owner){
        this.owner = owner;
        
        for(InventoryEntry entry : DAOFactory.inventory().getByOwner(owner.getOwnerType(), owner.getID())){
            GameItem item = ItemsFactory.recoverItem(entry);
            
            if(addOrStackItem(item) == MoveState.ERROR){
                item.getEntry().position = ItemPosition.DEFAULT_POSITION;
                addOrStackItem(item);
                save(item);
            }
        }
    }
    
    public MoveState addOrStackItem(GameItem item){
        if(!canAddItem(item))
            return MoveState.ERROR;
        
        GameItem other = getSameItem(item);
        
        if(other == null){
            addGameItem(item);
            return MoveState.ADD;
        }
        
        stackGameItem(item, other);
        return MoveState.STACK;
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
     * @return true on success
     */
    public boolean stackItem(GameItem item){
        if(!canAddItem(item))
            return false;
        
        GameItem other = getSameItem(item);
        
        if(other == null)
            return false;
        
        stackGameItem(item, other);
        return true;
    }
    
    /**
     * Add the item if possible
     * @param item the item to add
     * @return true on success
     */
    public boolean addItem(GameItem item){
        if(!canAddItem(item))
            return false;
        
        if(getSameItem(item) != null) //need stack
            return false;
        
        addGameItem(item);
        return false;
    }
    
    /**
     * Add the game item to inventory
     * @warning this method don't test if the item is valid !
     * @param item the item to add
     */
    private void addGameItem(GameItem item){
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
        delete(src);
    }
    
    /**
     * Move the item to dest_pos
     * @param item item to move
     * @param dest_qu quantity to move
     * @param dest_pos the target position
     * @return the state of the move
     */
    public MoveState moveItem(GameItem item, int dest_qu, byte dest_pos){
        if(dest_pos == item.getEntry().position 
                || !canMoveItem(item, dest_pos)
                || !items.containsValue(item)
                || item.getEntry().qu > dest_qu)
            return MoveState.ERROR;
        
        GameItem newItem;
        if(dest_qu == item.getEntry().qu){
            getItemsOnPos(item.getEntry().position).remove(item);
            item.getEntry().position = dest_pos;
            newItem = item;
        }else{
            newItem = ItemsFactory.copyItem(item, owner, dest_qu, dest_pos);
            item.getEntry().qu -= dest_qu;
        }
        
        MoveState state = addOrStackItem(newItem);
        
        if(newItem == item && state == MoveState.ADD)
            return MoveState.MOVE;
        
        return state;
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
        DAOFactory.inventory().delete(item.getEntry());
    }
}
