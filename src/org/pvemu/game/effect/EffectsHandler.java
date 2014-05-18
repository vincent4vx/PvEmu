package org.pvemu.game.effect;

import org.pvemu.game.effect.physical.attack.NeutralAttackEffect;
import org.pvemu.game.effect.physical.HealEffect;
import org.pvemu.game.effect.physical.attack.WaterAttackEffect;
import org.pvemu.game.effect.physical.attack.FireAttackEffect;
import org.pvemu.game.effect.physical.attack.GroundAttackEffect;
import org.pvemu.game.effect.physical.attack.AirAttackEffect;
import java.util.HashMap;
import java.util.Map;
import org.pvemu.game.effect.physical.lifesteal.AirLifeStealEffect;
import org.pvemu.game.effect.physical.lifesteal.FireLifeStealEffect;
import org.pvemu.game.effect.physical.lifesteal.GroundLifeStealEffect;
import org.pvemu.game.effect.physical.lifesteal.NeutralLifeStealEffect;
import org.pvemu.game.effect.physical.lifesteal.WaterLifeStealEffect;

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
        registerEffect(new NeutralLifeStealEffect());
        registerEffect(new WaterLifeStealEffect());
        registerEffect(new GroundLifeStealEffect());
        registerEffect(new AirLifeStealEffect());
        registerEffect(new FireLifeStealEffect());
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
