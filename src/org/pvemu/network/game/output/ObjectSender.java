/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ObjectSender {
    
    public void moveItem(GameItem item, IoSession session){
        GamePacketEnum.OBJECT_MOVE.send(
                session,
                GeneratorsRegistry.getObject().generateMoveItem(item)
        );
    }
    
    public void quantityChange(GameItem item, IoSession session){
        GamePacketEnum.OBJECT_QUANTITY.send(
                session,
                GeneratorsRegistry.getObject().generateQuantityChange(item)
        );
    }
    
    public void addItem(GameItem item, IoSession session){
        GamePacketEnum.OBJECT_ADD_OK.send(
                session,
                GeneratorsRegistry.getObject().generateInventoryEntry(item.getEntry())
        );
    }
    
    public void removeItem(GameItem item, IoSession session){
        GamePacketEnum.OBJECT_REMOVE.send(session, item.getID());
    }
}
