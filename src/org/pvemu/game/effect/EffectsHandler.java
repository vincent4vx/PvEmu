package org.pvemu.game.effect;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class EffectsHandler {
    final static private EffectsHandler instance = new EffectsHandler();
    final private Map<Short, Effect> effects = new HashMap<>();
    
    private EffectsHandler() {
        registerEffect(new NeutralAttackEffect());
        registerEffect(new WaterAttackEffect());
        registerEffect(new GroundAttackEffect());
        registerEffect(new AirAttackEffect());
        registerEffect(new FireAttackEffect());
        registerEffect(new HealEffect());
        registerEffect(new RemovePAEffect());
    }
    
    public void registerEffect(Effect effect){
        effects.put(effect.id(), effect);
    }
    
    public Effect getEffect(short id){
        return effects.get(id);
    }
    
    static public EffectsHandler instance(){
        return instance;
    }
}
