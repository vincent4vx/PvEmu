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
public class YesComparator<T> extends Comparator<T> {

    public YesComparator() {
        super(null);
    }

    @Override
    public boolean compare(T other) {
        return true;
    }
    
}
