/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.filters;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class Filter<T extends Filterable> {
    public boolean corresponds(Object obj){
        return false;
    }
    
    abstract public boolean corresponds(T obj);
    
    public Collection<T> filter(Collection<T> list){
        ArrayList<T> filtered = new ArrayList(list.size());
        
        for(T obj : list){
            if(corresponds(obj))
                filtered.add(obj);
        }
        
        return filtered;
    }
}
