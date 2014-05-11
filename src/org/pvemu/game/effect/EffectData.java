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
    final private byte target;
    final private String area;

    EffectData(Effect effect, int min, int max, int duration, byte target, String area) {
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

    public byte getTarget() {
        return target;
    }

    /**
     * The effective area
     * @return 
     * @see org.pvemu.game.objects.map.MapUtils#getCellsFromArea(org.pvemu.game.objects.map.GameMap, short, short, java.lang.String) 
     */
    public String getArea() {
        return area;
    }

    @Override
    public String toString() {
        return "EffectData{" + "effect=" + effect + ", min=" + min + ", max=" + max + ", duration=" + duration + ", target=" + target + ", area=" + area + '}';
    }
}
