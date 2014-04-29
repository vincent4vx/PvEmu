/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.itemlist.entrystate;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
class MoveState extends EntryState {

    MoveState(InventoryEntry entry) {
        super(entry);
    }

    @Override
    public void commit(IoSession out) {
        GameSendersRegistry.getObject().moveItem(entry, out);
        DAOFactory.inventory().update(entry);
        Loggin.debug("Déplacement de l'item %d", entry.id);
    }
    
}
