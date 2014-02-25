/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

import org.pvemu.game.objects.Player;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ObjectSender {
    public void accessories(Player player){
        GamePacketEnum.OBJECT_ACCESSORIES.sendToMap(
                player.getMap(), 
                GeneratorsRegistry.getObject().generateAccessories(player)
        );
    }
}
