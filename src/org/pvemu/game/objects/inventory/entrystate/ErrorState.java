/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.inventory.entrystate;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.InventoryEntry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
class ErrorState extends EntryState {
    final private String msg;

    ErrorState(InventoryEntry entry, String msg) {
        super(entry);
        this.msg = msg;
    }

    @Override
    public void commit(IoSession out) {
        Loggin.debug("Une erreur est survenue sur l'item %d : %s", entry.id, msg);
    }
    
}
