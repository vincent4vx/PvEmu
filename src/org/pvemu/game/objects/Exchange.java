package org.pvemu.game.objects;

import org.pvemu.game.objects.item.GameItem;
import java.util.HashMap;
import org.pvemu.actions.ActionsRegistry;
import org.pvemu.game.objects.item.factory.ItemsFactory;

public class Exchange {
    final private Player owner, target;
    final private HashMap<Integer, Integer> pendingItems = new HashMap<>();
    private boolean state = false;
    
    public Exchange(Player owner, Player target){
        this.owner = owner;
        this.target = target;
    }
    
    /**
     * Joueur cible
     * @return 
     */
    public Player getTarget(){
        return target;
    }
    
    public Player getOwner(){
        return owner;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
    
    /**
     * Ajoute l'item donné, avec les quantités données
     * @param itemID
     * @param qu
     * @return -1 en cas d'erreur, la nouvelle quantité sinon
     */
    public int addItem(int itemID, int qu){
        GameItem item = owner.getInventory().getItemById(itemID);
        
        if(item == null){
            return -1;
        }
        
        if(qu <= 0){
            return -1;
        }
        
        int lastQu = pendingItems.containsKey(itemID) ? pendingItems.get(itemID) : 0;
        
        if(qu > item.getEntry().qu - lastQu){
            return -1;
        }
        
        pendingItems.put(itemID, qu + lastQu);
        
        return qu + lastQu;
    }
    
    /**
     * Enlève la quantité d'item donnée
     * @param itemID
     * @param qu
     * @return -1 en cas d'erreur, sinon la quantité final
     */
    public int removeItem(int itemID, int qu){
        if(qu <= 0){
            return -1;
        }
        
        if(!pendingItems.containsKey(itemID)){
            return -1;
        }
        
        int nQu = pendingItems.get(itemID) - qu;
        
        if(nQu < 0){
            return -1;
        }
        
        if(nQu == 0){
            pendingItems.remove(itemID);
        }else{
            pendingItems.put(itemID, nQu);
        }
        
        return nQu;
    }
    
    public void accept(){
        for(int key : pendingItems.keySet()){
            int quantity = pendingItems.get(key);
            
            GameItem item = owner.getInventory().getItemById(key);
            GameItem copy = ItemsFactory.copyItem(item, target, quantity);
            
            ActionsRegistry.getObject().addItem(copy, target);
            ActionsRegistry.getObject().deleteItem(item, quantity, owner);
        }
    }
}
