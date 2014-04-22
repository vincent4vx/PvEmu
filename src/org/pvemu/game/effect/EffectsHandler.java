/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
