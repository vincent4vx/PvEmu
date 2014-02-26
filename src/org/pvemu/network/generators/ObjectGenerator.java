/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.game.objects.Player;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ObjectGenerator {
    public String generateUpdateAccessories(Player player){
        return new StringBuilder().append(player.getID())
                .append('|')
                .append(GeneratorsRegistry.getPlayer().generateAccessories(player))
                .toString();
    }
}
