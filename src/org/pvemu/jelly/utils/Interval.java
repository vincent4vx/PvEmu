package org.pvemu.jelly.utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class Interval {
    final static public Interval INFINITE = new Interval(Integer.MIN_VALUE, Integer.MAX_VALUE);
    
    final private int min;
    final private int max;

    public Interval(int min, int max) {
        if(min > max)
            throw new IllegalArgumentException("min > max");
        
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    /**
     * Test if v belongs to this
     * @param v
     * @return 
     */
    public boolean belongs(int v){
        return v >= min && v <= max;
    }
    
    /**
     * Generate the interval [v..+inf]
     * @param min
     * @return 
     */
    static public Interval from(int min){
        return new Interval(min, Integer.MAX_VALUE);
    }
    
    /**
     * Generate the interval [-inf..v]
     * @param max
     * @return 
     */
    static public Interval to(int max){
        return new Interval(Integer.MIN_VALUE, max);
    }
    
    /**
     * Generate a single value interval [val..val]
     * @param val
     * @return 
     */
    static public Interval fixed(int val){
        return new Interval(val, val);
    }

    @Override
    public String toString() {
        return "[" + min + ".." + max + "]";
    }
}
