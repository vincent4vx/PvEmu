/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.filters.comparators;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class EqualComparator<T> extends Comparator<T> {

    public EqualComparator(T defaultValue) {
        super(defaultValue);
    }

    @Override
    public boolean compare(T other) {
        if(defaultValue == null)
            return defaultValue == null;
        
        return defaultValue.equals(other);
    }
    
}
