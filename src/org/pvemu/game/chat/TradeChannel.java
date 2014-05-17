/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.chat;

import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TradeChannel implements ChatChannel{

    @Override
    public String id() {
        return ":";
    }

    @Override
    public void post(String msg, Player player) {
        String packet = id() + "|" + player.getID() 
                + "|" + player.getName() 
                + "|" + msg;
        
        GamePacketEnum.CHAT_MESSAGE_OK.sendToAll(packet);
    }

    @Override
    public Filter conditions() {
        return FilterFactory.yesFilter();
    }
    
}
