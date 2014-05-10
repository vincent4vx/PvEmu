package org.pvemu.jelly.plugin.exception;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PluginNotFound extends IOPluginException{

    public PluginNotFound(String plugin, String path) {
        super(plugin, "Plugin was not found in '" + path + "'");
    }
    
}
