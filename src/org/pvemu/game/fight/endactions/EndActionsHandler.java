package org.pvemu.game.fight.endactions;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.endactions.defiance.DefianceEndActionsRegisty;
import org.pvemu.game.fight.endactions.pvm.PvmEndActionsRegistry;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class EndActionsHandler {

    final static private EndActionsHandler instance = new EndActionsHandler();
    final private Map<Class<? extends Fight>, FightEndActionsRegistry> registries = new HashMap<>();

    private EndActionsHandler() {
        registerEndActionsRegistry(new DefianceEndActionsRegisty());
        registerEndActionsRegistry(new PvmEndActionsRegistry());
    }
    
    public void registerEndActionsRegistry(FightEndActionsRegistry registry){
        registries.put(registry.getFightClass(), registry);
    }
    
    public void applyEndActions(Fight fight, Fighter fighter, boolean isWinner){
        FightEndActionsRegistry registry = registries.get(fight.getClass());
        
        if(registry == null){
            Loggin.debug("No actions found for fight type %s", fight.getClass().getName());
            return;
        }
        
        FighterEndActions actions = registry.getEndActions(fighter.getClass());
        
        if(actions == null){
            Loggin.debug("No actions found for fighter type %s on fight type %s", fighter.getClass().getName(), fight.getClass().getName());
            return;
        }
        
        actions.apply(fight, fighter, isWinner);
    }

    static public EndActionsHandler instance() {
        return instance;
    }
}
