/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.triggeraction;

import org.pvemu.game.objects.player.Player;
import org.pvemu.common.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface TriggerAction {
    /**
     * the action id
     * @return 
     */
    public short actionId();
    
    /**
     * Actions to perform
     * @param trigger
     * @param player 
     */
    public void perform(Trigger trigger, Player player);
    
    /**
     * Conditions to perform this action
     * @return 
     */
    public Filter condition();
}
