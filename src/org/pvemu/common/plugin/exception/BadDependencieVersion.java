/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.plugin.exception;

import org.pvemu.common.utils.Version;
import org.pvemu.common.utils.VersionMatcher;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class BadDependencieVersion extends DependencieException{

    public BadDependencieVersion(String plugin, String dependencie, VersionMatcher need, Version current) {
        super(plugin, dependencie, "the plugin need " + need + " but the dependencie is on version " + current);
    }
    
}
