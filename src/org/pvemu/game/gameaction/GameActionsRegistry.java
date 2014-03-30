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
final public class GameActionsRegistry {
    final static private GameActionsRegistry instance = new GameActionsRegistry();
    
    final private HashMap<Short, GameAction> gameActions = new HashMap<>();
    
    private GameActionsRegistry(){
        registerGameAction(new WalkAction());
        registerGameAction(new InteractiveObjectAction());
    }
    
    public void registerGameAction(GameAction GA){
        gameActions.put(GA.id(), GA);
    }
    
    public GameAction getGameAction(short gameActionID){
        return gameActions.get(gameActionID);
    }
    
    static public GameActionsRegistry instance(){
        return instance;
    }
}
