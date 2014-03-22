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
public class ExchangeSender {
    public void exchangeOk(Player owner, Player target, boolean state){
        GamePacketEnum.EXCHANGE_OK.send(owner.getSession(), GeneratorsRegistry.getExchange().generateExchangeOk(owner, state));
        GamePacketEnum.EXCHANGE_OK.send(target.getSession(), GeneratorsRegistry.getExchange().generateExchangeOk(owner, state));
    }
}
