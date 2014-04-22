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
    public short id();
    public void start(GameActionData<T> data);
    public void end(GameActionData<T> data, boolean success, String[] args);
}
