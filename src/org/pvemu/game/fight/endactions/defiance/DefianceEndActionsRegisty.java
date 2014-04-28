/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.endactions.defiance;

import org.pvemu.game.fight.endactions.FightEndActionsRegistry;
import org.pvemu.game.fight.fightmode.DefianceFight;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DefianceEndActionsRegisty extends FightEndActionsRegistry<DefianceFight>{

    @Override
    public Class<DefianceFight> getFightClass() {
        return DefianceFight.class;
    }

    public DefianceEndActionsRegisty() {
        registerEndActions(new PlayerDefianceEndActions());
    }
    
    
}
