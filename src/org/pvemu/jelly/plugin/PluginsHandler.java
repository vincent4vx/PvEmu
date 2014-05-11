package org.pvemu.jelly.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import org.pvemu.jelly.Config;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Shell;
import org.pvemu.jelly.plugin.exception.DependencieException;
import org.pvemu.jelly.plugin.exception.DependencieNotFound;
import org.pvemu.jelly.plugin.exception.IOPluginException;
import org.pvemu.jelly.plugin.exception.BadDependencieVersion;
import org.pvemu.jelly.plugin.exception.IncompatiblePluginException;
import org.pvemu.jelly.plugin.exception.InvalidPluginConstructor;
import org.pvemu.jelly.plugin.exception.InvalidPluginFile;
import org.pvemu.jelly.plugin.exception.PluginException;
import org.pvemu.jelly.plugin.exception.PluginNotFound;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.jelly.utils.VersionMatcher;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class PluginsHandler {
    final static public String PATH              = "plugins";
    final static public String EXT               = ".jar";
    final static public String CLASS_EXT         = ".class";
    final static public char DIRECTORY_SEPARATOR = '/';
    final static public char NAMESPACE_SEPARATOR = '.';
    
    final static private PluginsHandler instance = new PluginsHandler();
    final private Map<String, Plugin> plugins = new HashMap<>();
    
    private PluginsHandler(){}
    
    /**
     * load a plugin file
     * @param name
     * @return
     * @throws PluginException 
     */
    public List<Plugin> loadPlugin(String name) throws PluginException{
        List<Plugin> list = new ArrayList<>();
        Loggin.debug("Loading plugin %s", name);
        File file = new File(PATH + DIRECTORY_SEPARATOR + name + EXT);
        
        if(!file.exists()){
            throw new PluginNotFound(name, file.getAbsolutePath());
        }
        
        JarFile jar;
        
        try {
            jar = new JarFile(file);
        } catch (IOException ex) {
            throw new IOPluginException(name, ex.getMessage());
        }
        
        Enumeration e = jar.entries();
        URLClassLoader loader;
        
        try {
            loader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        } catch (MalformedURLException ex) {
            throw new IOPluginException(name, ex.toString());
        }
        
        while(e.hasMoreElements()){
            String tmp = e.nextElement().toString();
            
            if(tmp.isEmpty() || !tmp.endsWith(CLASS_EXT))
                continue;
            
            tmp = tmp.substring(0, tmp.length() - CLASS_EXT.length()).replace(
                    DIRECTORY_SEPARATOR, 
                    NAMESPACE_SEPARATOR
            );
            
            Class clazz;
            
            try{
                clazz = Class.forName(tmp, true, loader);
            }catch(ClassNotFoundException ex){
                throw new InvalidPluginFile(name, ex.toString());
            }
            
            if(!Utils.contains(clazz.getInterfaces(), Plugin.class))
                continue;
            
            Plugin plugin;
            
            try{
                plugin = (Plugin)clazz.newInstance();
            }catch(Exception ex){
                throw new InvalidPluginConstructor(name, tmp, ex.getMessage());
            }
            
            list.add(plugin);
        }
        
        return list;
    }
    
    /**
     * Load all config plugins
     */
    public void loadPlugins(){
        Shell.print("Loading plugins : ", Shell.GraphicRenditionEnum.YELLOW);
        
        long start = System.currentTimeMillis();
        
        try{
            Map<String, Plugin> tmp = new HashMap<>();
            
            for(String name : Config.PLUGINS.getValue()){
                List<Plugin> list = loadPlugin(name);
                
                for(Plugin plugin : list)
                    tmp.put(plugin.name(), plugin);
            }
            
            for(Plugin plugin : tmp.values()){
                initPlugin(plugin, tmp);
            }
        }catch(PluginException e){
            Loggin.error("Cannot load plugins", e);
            System.exit(1);
        }
        
        Shell.println("Ok in " + (System.currentTimeMillis() - start) + "ms", Shell.GraphicRenditionEnum.GREEN);
    }
    
    private void initPlugin(Plugin plugin, Map<String, Plugin> waitings) throws PluginException{
        if(plugins.containsKey(plugin.name())) //plugin already init
            return;
        
        if(!plugin.compatibility().match(Constants.VERSION))
            throw new IncompatiblePluginException(plugin.name(), plugin.compatibility());
        
        for(Pair<String, VersionMatcher> pair : plugin.dependencies()){
            if(plugins.containsKey(pair.getFirst())) //plugin already loaded
                continue;
            
            Plugin dependencie = waitings.get(pair.getFirst());
            
            if(dependencie == null)
                throw new DependencieNotFound(plugin.name(), pair.getFirst());
            
            if(!pair.getSecond().match(dependencie.version()))
                throw new BadDependencieVersion(plugin.name(), dependencie.name(), pair.getSecond(), dependencie.version());
            
            initPlugin(dependencie, waitings);
        }
        
        plugin.init();
        plugins.put(plugin.name(), plugin);
    }
    
    static public PluginsHandler instance(){
        return instance;
    }
}
