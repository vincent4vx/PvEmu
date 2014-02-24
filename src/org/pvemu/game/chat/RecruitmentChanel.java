/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.chat;

import org.pvemu.game.objects.Player;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.YesFilter;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class RecruitmentChanel implements ChatChanel {

    @Override
    public String id() {
        return "?";
    }

    @Override
    public void post(String msg, Player player) {
        StringBuilder packet = new StringBuilder(msg.length() + 20);
        packet.append('?').append('|')
                .append(player.getID()).append('|')
                .append(player.getName()).append('|')
                .append(msg);
        
        GamePacketEnum.CHAT_MESSAGE_OK.sendToAll(packet.toString());
    }

    @Override
    public Filter conditions() {
        return new YesFilter();
    }
    
}
