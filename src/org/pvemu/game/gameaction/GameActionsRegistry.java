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
    final static public short WALK               = 1;
    final static public short CINEMATIC          = 2;
    final static public short INTERACTIVE_OBJECT = 500;
    final static public short ASK_DEFIANCE       = 900;
    final static public short ACCEPT_DEFIANCE    = 901;
    final static public short CANCEL_DEFIANCE    = 902;
    final static public short DEFIANCE_ERROR     = 903;
    
    final static private GameActionsRegistry instance = new GameActionsRegistry();
    
    final private HashMap<Short, GameAction> gameActions = new HashMap<>();
    
    private GameActionsRegistry(){
        registerGameAction(new WalkAction());
        registerGameAction(new InteractiveObjectAction());
        registerGameAction(new CinematicAction());
        registerGameAction(new AskDefianceAction());
        registerGameAction(new CancelDefianceAction());
        registerGameAction(new AcceptDefianceAction());
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
