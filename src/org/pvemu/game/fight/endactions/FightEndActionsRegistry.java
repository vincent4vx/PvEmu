/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.endactions;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public abstract class FightEndActionsRegistry<F extends Fight>{
    final private Map<Class<? extends Fighter>, FighterEndActions> actions = new HashMap<>();
    
    abstract public Class<F> getFightClass();
    
    public void registerEndActions(FighterEndActions endActions){
        actions.put(endActions.getFighterClass(), endActions);
    }
    
    public FighterEndActions getEndActions(Class<? extends Fighter> clazz){
        return actions.get(clazz);
    }
}
