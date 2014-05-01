/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.ai;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class NormalAI extends AIType{

    @Override
    public byte typeID() {
        return 1;
    }

    @Override
    public boolean actions(Fight fight, Fighter fighter) {
        Fighter target = AIUtils.getNearestEnnemy(fight, fighter);
        
        if(target == null)
            return false;
        
        return AIUtils.moveNear(fight, fighter, target);
    }
    
}
