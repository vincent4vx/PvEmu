/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.inventory.entrystate;

import org.pvemu.models.InventoryEntry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class EntryStateFactory {
    static public EntryState addState(InventoryEntry entry){
        return new AddState(entry);
    }
    
    static public EntryState deleteState(InventoryEntry entry){
        return new DeleteState(entry);
    }
    
    static public EntryState errorState(InventoryEntry entry, String msg){
        return new ErrorState(entry, msg);
    }
    
    static public EntryState moveState(InventoryEntry entry){
        return new MoveState(entry);
    }
    
    static public EntryState stackState(InventoryEntry entry){
        return new StackState(entry);
    }
}
