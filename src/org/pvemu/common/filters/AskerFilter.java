/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.filters;

import org.pvemu.commands.askers.Asker;
import org.pvemu.common.filters.comparators.Comparator;
import org.pvemu.common.filters.comparators.YesComparator;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AskerFilter<T extends Asker> extends Filter<T> {

    private Comparator<Byte> level = new YesComparator<>();
    private Comparator<String> name = new YesComparator<>();

    /**
     * Set the value of name
     *
     * @param name new value of name
     */
    public void setName(Comparator<String> name) {
        this.name = name;
    }


    /**
     * Set the value of level
     *
     * @param level new value of level
     */
    public void setLevel(Comparator<Byte> level) {
        this.level = level;
    }

    @Override
    public boolean corresponds(T obj) {
        return name.compare(obj.name())
                && level.compare(obj.level());
    }
    
}
