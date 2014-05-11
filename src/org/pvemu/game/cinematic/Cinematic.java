/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.cinematic;

import org.pvemu.game.objects.player.Player;

/**
 * cinamatic action
 * @see org.pvemu.game.gameaction.game.CinematicAction
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Cinematic {
    /**
     * The cinematic id
     * @return 
     */
    public byte id();
    
    /**
     * actions performs on end of cinematic
     * @param player 
     */
    public void end(Player player);
}
