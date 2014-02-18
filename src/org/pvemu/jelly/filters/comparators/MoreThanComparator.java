/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.filters.comparators;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MoreThanComparator<T extends Comparable> extends Comparator<T> {

    public MoreThanComparator(T defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean compare(T other) {
        if(defaultValue == null)
            return false;
        
        return defaultValue.compareTo(other) == 1;
    }
    
}
