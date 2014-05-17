package org.pvemu.common.plugin.exception;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
abstract public class PluginException extends Exception{
    final private String plugin;
    final private String message;

    public PluginException(String plugin, String message) {
        this.plugin = plugin;
        this.message = message;
    }

    public String getPlugin() {
        return plugin;
    }

    @Override
    public String getMessage() {
        return "[" + plugin + "] " + message;
    }
    
    
}
