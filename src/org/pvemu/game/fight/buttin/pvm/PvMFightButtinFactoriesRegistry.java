/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin.pvm;

import org.pvemu.game.fight.buttin.FightButtinFactoriesRegistry;
import org.pvemu.game.fight.fightmode.PvMFight;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PvMFightButtinFactoriesRegistry extends FightButtinFactoriesRegistry<PvMFight>{

    @Override
    public Class<PvMFight> fightClass() {
        return PvMFight.class;
    }

    public PvMFightButtinFactoriesRegistry() {
        registerFactory(new PlayerFighterPvMFightButtinFactory());
    }
    
}
