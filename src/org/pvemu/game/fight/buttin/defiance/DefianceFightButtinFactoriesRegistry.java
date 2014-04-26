/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin.defiance;

import org.pvemu.game.fight.DefianceFight;
import org.pvemu.game.fight.buttin.FightButtinFactoriesRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DefianceFightButtinFactoriesRegistry extends FightButtinFactoriesRegistry<DefianceFight>{

    public DefianceFightButtinFactoriesRegistry() {
        registerFactory(new PlayerFighterDefianceFightButtinFactory());
    }

    @Override
    public Class<DefianceFight> fightClass() {
        return DefianceFight.class;
    }
    
}
