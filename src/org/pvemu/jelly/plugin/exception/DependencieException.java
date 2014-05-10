package org.pvemu.jelly.plugin.exception;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DependencieException extends PluginException{

    public DependencieException(String plugin, String dependencie, String message) {
        super(plugin, "for dependencie '" + dependencie + "' : " + message);
    }
    
}
