/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.spell.effect;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class SpellEffectsHandler {
    final static private SpellEffectsHandler instance = new SpellEffectsHandler();
    final static private Map<Short, SpellEffect> effects = new HashMap<>();
    
    private SpellEffectsHandler() {
    }
    
    public void registerSpellEffect(SpellEffect effect){
        effects.put(effect.id(), effect);
    }
    
    public SpellEffect getSpellEffect(short id){
        return effects.get(id);
    }
    
    static public SpellEffectsHandler instance(){
        return instance;
    }
}
