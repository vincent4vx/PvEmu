/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.endactions.pvm;

import org.pvemu.game.fight.endactions.FightEndActionsRegistry;
import org.pvemu.game.fight.fightmode.PvMFight;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PvmEndActionsRegistry extends FightEndActionsRegistry<PvMFight>{

    @Override
    public Class<PvMFight> getFightClass() {
        return PvMFight.class;
    }

    public PvmEndActionsRegistry() {
        registerEndActions(new PlayerPvmEndActions());
    }
    
}
