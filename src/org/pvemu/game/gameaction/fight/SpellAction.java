/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction.fight;

import org.pvemu.game.fight.PlayerFighter;
import org.pvemu.game.gameaction.GameAction;
import org.pvemu.game.gameaction.GameActionData;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SpellAction implements GameAction<PlayerFighter>{

    @Override
    public short id() {
        return FightActionsRegistry.SPELL;
    }

    @Override
    public void start(GameActionData<PlayerFighter> data) {
        
    }

    @Override
    public void end(GameActionData<PlayerFighter> data, boolean success, String[] args) {
    }
    
}
