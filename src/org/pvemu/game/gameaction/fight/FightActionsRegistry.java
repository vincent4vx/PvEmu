/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.fight;

import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.game.gameaction.AbstractGameActionsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightActionsRegistry extends AbstractGameActionsRegistry<PlayerFighter>{
    final static public short WALK           = 1;
    final static public short USE_PM         = 128;
    final static public short USE_PM_ON_MOVE = 129;
    
    final static private FightActionsRegistry instance = new FightActionsRegistry();
    
    private FightActionsRegistry(){
        registerGameAction(new WalkAction());
    }

    public static FightActionsRegistry instance() {
        return instance;
    }
}
