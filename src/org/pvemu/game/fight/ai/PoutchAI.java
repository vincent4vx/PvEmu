/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.ai;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.fightertype.AIFighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PoutchAI extends AIType{

    @Override
    public byte typeID() {
        return 0;
    }

    @Override
    public boolean actions(Fight fight, AIFighter fighter) {
        return false;
    }
    
}
