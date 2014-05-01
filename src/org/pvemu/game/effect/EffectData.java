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
public class EffectData {
    final private Effect effect;
    final private int min;
    final private int max;
    final private int duration;
    final private int target;
    final private String area;

    EffectData(Effect effect, int min, int max, int duration, int target, String area) {
        this.effect = effect;
        this.min = min;
        this.max = max;
        this.duration = duration;
        this.target = target;
        this.area = area;
    }

    public Effect getEffect() {
        return effect;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getDuration() {
        return duration;
    }

    public int getTarget() {
        return target;
    }

    public String getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "EffectData{" + "effect=" + effect + ", min=" + min + ", max=" + max + ", duration=" + duration + ", target=" + target + ", area=" + area + '}';
    }
}
