/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Effect {
    public enum EffectType{
        ATTACK(false),
        BUFF(true),
        DEBUFF(false),
        HEAL(true),
        ENVIRONMENT(false);
        
        final boolean friendEffect;

        private EffectType(boolean friendEffect) {
            this.friendEffect = friendEffect;
        }

        public boolean isFriendEffect() {
            return friendEffect;
        }
        
    }
    
    /**
     * Get the effect ID
     * @return 
     */
    public short id();
    
    /**
     * Apply this effect to the fight
     * @param data the effect data
     * @param fight the current fight
     * @param caster the spell caster
     * @param cell the target cell
     */
    public void applyToFight(EffectData data, Fight fight, Fighter caster, short cell);
    
    /**
     * Return the efficience of a spell for the fight
     * @param data the effect data
     * @param fight the current fight
     * @param caster the spell caster
     * @param cell the target cell
     * @return negative value is bad, zero is useless, more than 1 is usefull
     */
    public int getEfficiency(EffectData data, Fight fight, Fighter caster, short cell);
    public EffectType getEffectType();
}
