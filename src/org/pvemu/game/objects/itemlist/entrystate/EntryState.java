/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.itemlist.entrystate;

import org.apache.mina.core.session.IoSession;
import org.pvemu.models.InventoryEntry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class EntryState {
    final protected InventoryEntry entry;

    EntryState(InventoryEntry entry) {
        this.entry = entry;
    }
    
    final public InventoryEntry getEntry(){
        return entry;
    }
    
    abstract public void commit(IoSession out);
}
