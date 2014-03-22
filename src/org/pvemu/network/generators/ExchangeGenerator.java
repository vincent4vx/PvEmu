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
public class ExchangeGenerator {
    public String generateExchangeOk(Player player, boolean state){
        return (state ? "1" : "0") + player.getID();
    }
}
