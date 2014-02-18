/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.filters;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class YesFilter extends Filter {
    
    @Override
    public boolean corresponds(Object obj){
        return true;
    }

    @Override
    public boolean corresponds(Filterable obj) {
        return true;
    }
    
}
