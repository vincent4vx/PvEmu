/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.objects.Player;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PlayerSender {
    public void accessories(Player player){
        GamePacketEnum.OBJECT_ACCESSORIES.sendToMap(
                player.getMap(),
                GeneratorsRegistry.getPlayer().generateUpdateAccessories(player)
        );
    }
    
    public void weightUsed(Player player, IoSession session){
        GamePacketEnum.OBJECTS_WEIGHT.send(
                session,
                GeneratorsRegistry.getPlayer().generateWeightUsed(player)
        );
    }
}