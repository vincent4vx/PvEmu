/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.chat;

import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.YesFilter;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MapChannel implements ChatChannel {

    @Override
    public String id() {
        return "*";
    }

    @Override
    public void post(String msg, Player player) {
        StringBuilder packet = new StringBuilder(msg.length() + 20);
        packet.append('|').append(player.getID()).append('|').append(player.getName()).append('|').append(msg);
        
        if(!player.getActionsManager().isInFight())
            GamePacketEnum.CHAT_MESSAGE_OK.sendToMap(player.getMap(), packet.toString());
        else
            GamePacketEnum.CHAT_MESSAGE_OK.sendToFight(player.getFighter().getFight(), packet);
    }

    @Override
    public Filter conditions() {
        return new YesFilter();
    }
    
}
