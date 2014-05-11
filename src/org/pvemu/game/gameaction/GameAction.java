/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface GameAction<T extends ActionPerformer> {
    /**
     * The action id
     * @return 
     */
    public short id();
    
    /**
     * Actions on start of the GA
     * @param data 
     * @see org.pvemu.network.game.input.game.GameActionPacket
     */
    public void start(GameActionData<T> data);
    
    /**
     * actions on end of the GA
     * @param data
     * @param success
     * @param args new args if success = false
     */
    public void end(GameActionData<T> data, boolean success, String[] args);
}
