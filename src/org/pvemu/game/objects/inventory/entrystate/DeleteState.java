/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.inventory.entrystate;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
class DeleteState extends EntryState {

    DeleteState(InventoryEntry entry) {
        super(entry);
    }

    @Override
    public void commit(IoSession out) {
        if(entry.isCreated()){
            GameSendersRegistry.getObject().removeItem(entry, out);
            Loggin.debug("Suppression de l'item %d", entry.id);
            DAOFactory.inventory().delete(entry);
        }
    }
    
}
