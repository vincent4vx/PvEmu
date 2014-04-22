/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;

import java.util.HashMap;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class AbstractGameActionsRegistry<T extends ActionPerformer> {    
    final private HashMap<Short, GameAction<T>> gameActions = new HashMap<>();
    
    final public void registerGameAction(GameAction<T> GA){
        gameActions.put(GA.id(), GA);
    }
    
    final public GameAction getGameAction(short gameActionID){
        return gameActions.get(gameActionID);
    }
}
