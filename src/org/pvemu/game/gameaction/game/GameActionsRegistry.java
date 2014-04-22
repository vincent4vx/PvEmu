/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.game;

import org.pvemu.game.gameaction.*;
import org.pvemu.game.objects.player.Player;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionsRegistry extends AbstractGameActionsRegistry<Player>{
    final static public short WALK               = 1;
    final static public short CINEMATIC          = 2;
    final static public short INTERACTIVE_OBJECT = 500;
    final static public short ASK_DEFIANCE       = 900;
    final static public short ACCEPT_DEFIANCE    = 901;
    final static public short CANCEL_DEFIANCE    = 902;
    final static public short JOIN_FIGHT         = 903;
    
    final static private GameActionsRegistry instance = new GameActionsRegistry();
    
    private GameActionsRegistry(){
        registerGameAction(new WalkAction());
        registerGameAction(new InteractiveObjectAction());
        registerGameAction(new CinematicAction());
        registerGameAction(new AskDefianceAction());
        registerGameAction(new CancelDefianceAction());
        registerGameAction(new AcceptDefianceAction());
        registerGameAction(new JoinFightAction());
    }
    
    static public GameActionsRegistry instance(){
        return instance;
    }
}
