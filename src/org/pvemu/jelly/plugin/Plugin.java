/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.plugin;

import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Version;
import org.pvemu.jelly.utils.VersionMatcher;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface Plugin {
    /**
     * Get the plugin version
     * @return 
     */
    public Version version();
    
    /**
     * Get the compatible versions of the emulator
     * @return 
     */
    public VersionMatcher compatibility();
    
    /**
     * Return the list of dependencies
     * The first element of Pair is the plugin, the second is the version
     * @return 
     */
    public Pair<String, VersionMatcher>[] dependencies();
    
    public String name();
    
    /**
     * This method was call after construction
     * 
     */
    public void init();
}
