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
public class RegexComparator extends Comparator<String> {

    public RegexComparator(String pattern) {
        super(pattern);
    }

    @Override
    public boolean compare(String other) {
        return other.matches(defaultValue);
    }
    
}
