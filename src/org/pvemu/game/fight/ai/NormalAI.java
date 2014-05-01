package org.pvemu.game.fight.ai;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.fightertype.AIFighter;
import org.pvemu.jelly.Loggin;

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
    public boolean actions(Fight fight, AIFighter fighter) {
        Fighter target = AIUtils.getNearestEnnemy(fight, fighter);
        
        if(target == null){
            Loggin.warning("No ennemy found for %s", fighter);
            return false;
        }
        
        if(!AIUtils.moveNear(fight, fighter, target)){
            if(!AIUtils.attack(fight, fighter)){
                AIUtils.leavePlaceForFriends(fight, fighter);
                return false;
            }
        }
        return true;
    }
    
}
