/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.itemlist.entrystate;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.Loggin;
import org.pvemu.models.InventoryEntry;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class BadLevelState extends EntryState{

    public BadLevelState(InventoryEntry entry) {
        super(entry);
    }

    @Override
    public void commit(IoSession out) {
        Loggin.debug("Bad level for equip item");
        GameSendersRegistry.getObject().badLevel(out);
    }
    
}
