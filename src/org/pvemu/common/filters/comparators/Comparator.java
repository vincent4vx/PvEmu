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
abstract public class Comparator<T> {
    protected final T defaultValue;

    protected Comparator(T defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    /**
     * Compare the defaultValue to other
     * @param other the other value to compre
     * @return true if other is valid
     */
    abstract public boolean compare(T other);
}
