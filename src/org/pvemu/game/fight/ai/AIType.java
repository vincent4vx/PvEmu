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
abstract public class AIType{
    /**
     * ID of the AI
     * @return 
     */
    abstract public byte typeID();
    
    /**
     * Actions to perform while it's possible
     * @param fight
     * @param fighter
     * @return true if can perform other actions, false is can't
     */
    abstract public boolean actions(Fight fight, AIFighter fighter);
}
