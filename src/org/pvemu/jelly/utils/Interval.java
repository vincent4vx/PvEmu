package org.pvemu.jelly.utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class Interval {
    final private int min;
    final private int max;

    public Interval(int min, int max) {
        if(min > max)
            throw new IllegalArgumentException("min > max");
        
        this.min = min;
        this.max = max;
    }
    
    public Interval(){
        this.min = Integer.MIN_VALUE;
        this.max = Integer.MAX_VALUE;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
    
    public boolean belongs(int v){
        return v >= min && v <= max;
    }
    
    static public Interval from(int min){
        return new Interval(min, Integer.MAX_VALUE);
    }
    
    static public Interval to(int max){
        return new Interval(Integer.MIN_VALUE, max);
    }
    
    static public Interval fixed(int val){
        return new Interval(val, val);
    }

    @Override
    public String toString() {
        return "[" + min + ".." + max + "]";
    }
}
