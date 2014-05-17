/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.plugin.exception;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DependencieNotFound extends DependencieException{

    public DependencieNotFound(String plugin, String dependencie) {
        super(plugin, dependencie, "cannot load this dependencie");
    }
    
}
