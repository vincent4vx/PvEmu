/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class EnvironmentEffect implements Effect{

    @Override
    public EffectType getEffectType(){
        return EffectType.ENVIRONMENT;
    }
    
}
