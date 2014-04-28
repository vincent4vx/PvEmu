/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.buttin;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightTeam;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.buttin.defiance.DefianceFightButtinFactoriesRegistry;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class FightButtinFactory {
    final static private FightButtinFactory instance = new FightButtinFactory();
    
    final private Map<Class<? extends Fight>, FightButtinFactoriesRegistry> registries = new HashMap<>();

    private FightButtinFactory() {
        registerRegistry(new DefianceFightButtinFactoriesRegistry());
    }
    
    public void registerRegistry(FightButtinFactoriesRegistry registry){
        registries.put(registry.fightClass(), registry);
    }
    
    public FightButtinFactoriesRegistry getRegistry(Class<? extends Fight> clazz){
        return registries.get(clazz);
    }
    
    public FightButtin getButtin(Fight fight, Fighter fighter, FightTeam winners, Collection<FightTeam> loosers){
        FightButtinFactoriesRegistry registry = registries.get(fight.getClass());
        
        if(registry == null){
            Loggin.debug("Cannot found FightButtinFactoriesRegistry for fight type %s", fight.getClass().getName());
            return FightButtin.emptyButtin();
        }
        
        FighterFightButtinFactory factory = registry.getFactoryByClass(fighter.getClass());
        
        if(factory == null){
            Loggin.debug("Cannot found FighterFightButtinFactory for fight type %s and fighter type %s", fight.getClass().getName(), fighter.getClass().getName());
            return FightButtin.emptyButtin();
        }
        
        return factory.makeButtin(fight, fighter, winners, loosers);
    }
    
    static public FightButtinFactory instance(){
        return instance;
    }
}
