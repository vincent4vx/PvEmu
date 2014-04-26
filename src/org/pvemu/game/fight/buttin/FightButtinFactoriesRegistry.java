/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class FightButtinFactoriesRegistry<T extends Fight> {
    final private Map<Class<? extends Fighter>, FighterFightButtinFactory> factories = new HashMap<>();
    
    abstract public Class<T> fightClass();
    
    public void registerFactory(FighterFightButtinFactory factory){
        factories.put(factory.fighterClass(), factory);
    }
    
    public FighterFightButtinFactory getFactoryByClass(Class<? extends Fighter> clazz){
        return factories.get(clazz);
    }
}
