/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.fight;

import org.pvemu.game.fight.fightertype.PlayerFighter;
import org.pvemu.game.gameaction.AbstractGameActionsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FightActionsRegistry extends AbstractGameActionsRegistry<PlayerFighter>{
    final static public short WALK             = 1;
    final static public short UPDATE_PA        = 101;
    final static public short USE_PA_ON_ACTION = 102;
    final static public short FIGHTER_DIE      = 103;
    final static public short UPDATE_VITA      = 110;
    final static public short USE_PM           = 128;
    final static public short USE_PM_ON_MOVE   = 129;
    final static public short INVOCATE         = 181;
    final static public short SPELL            = 300;
    final static public short WEAPON           = 303;
    
    final static private FightActionsRegistry instance = new FightActionsRegistry();
    
    private FightActionsRegistry(){
        registerGameAction(new WalkAction());
        registerGameAction(new WeaponAction());
        registerGameAction(new SpellAction());
    }

    public static FightActionsRegistry instance() {
        return instance;
    }
}
