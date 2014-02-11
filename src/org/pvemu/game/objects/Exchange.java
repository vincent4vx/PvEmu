package org.pvemu.game.objects;

import org.pvemu.game.objects.inventory.GameItem;
import java.util.HashMap;

public class Exchange {
    private Player _owner, _target;
    private HashMap<Integer, Integer> pendingItems = new HashMap<>();
    
    public Exchange(Player owner, Player target){
        _owner = owner;
        _target = target;
    }
    
    /**
     * Joueur cible
     * @return 
     */
    public Player getTarget(){
        return _target;
    }
    
    /**
     * Ajoute l'item donné, avec les quantités données
     * @param itemID
     * @param qu
     * @return -1 en cas d'erreur, la nouvelle quantité sinon
     */
    public int addItem(int itemID, int qu){
        GameItem GI = _owner.getInventory().getItemById(itemID);
        
        if(GI == null){
            return -1;
        }
        
        if(qu <= 0){
            return -1;
        }
        
        int lastQu = pendingItems.containsKey(itemID) ? pendingItems.get(itemID) : 0;
        
        if(qu > GI.getInventoryEntry().qu - lastQu){
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
}
