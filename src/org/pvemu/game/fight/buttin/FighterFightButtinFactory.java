/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface FighterFightButtinFactory<T extends Fighter, E extends Fight>{
    public Class<T> fighterClass();
    
    public FightButtin makeButtin(E fight, T fighter, byte winners, int winTeamLevel, int loseLeamLevel);
}
