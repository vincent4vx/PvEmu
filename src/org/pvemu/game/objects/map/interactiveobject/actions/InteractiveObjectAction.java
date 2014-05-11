/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map.interactiveobject.actions;

import org.pvemu.game.objects.map.interactiveobject.InteractiveObject;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface InteractiveObjectAction {
    /**
     * The action id
     * @return 
     */
    public int id();
    
    /**
     * Start the action
     * @param IO
     * @param player 
     */
    public void startAction(InteractiveObject IO, Player player);
    
    /**
     * Conditions to perform this action
     * @return 
     */
    public Filter condition();
    
    /**
     * When an error occure
     * @param IO
     * @param player 
     */
    public void onError(InteractiveObject IO, Player player);
}
