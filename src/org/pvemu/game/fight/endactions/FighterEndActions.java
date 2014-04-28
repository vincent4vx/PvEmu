/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.endactions;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface FighterEndActions<T extends Fight, E extends Fighter>{
    public Class<E> getFighterClass();
    public void apply(T fight, E fighter, boolean isWinner);
}
