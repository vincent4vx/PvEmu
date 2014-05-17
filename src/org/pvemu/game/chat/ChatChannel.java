/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.chat;

import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface ChatChannel {
    /**
     * Get the chanel id
     * @return chanel id
     */
    public String id();
    
    /**
     * Post the message into the chat chanel
     * @param msg the message to post
     * @param player the curent player
     */
    public void post(String msg, Player player);
    
    /**
     * Get the condition to post / receive message of this chanel
     * @return the filter
     */
    public Filter conditions();
}
