/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common;

import org.pvemu.common.utils.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Config {
    final static private HashMap<String, ConfigItem> items = new HashMap<>();
    
    /**
     * IP of the DB
     * @see org.pvemu.jelly.database.DatabaseHandler#init() 
     */
    final static public ConfigItem<String> DB_HOST           = new ConfigStringItem("DB_HOST", "127.0.0.1");
    /**
     * DB user name
     * @see org.pvemu.jelly.database.DatabaseHandler#init() 
     */
    final static public ConfigItem<String> DB_USER           = new ConfigStringItem("DB_USER", "root");
    /**
     * DB pass (can be empty)
     * @see org.pvemu.jelly.database.DatabaseHandler#init() 
     */
    final static public ConfigItem<String> DB_PASS           = new ConfigStringItem("DB_PASS", "");
    /**
     * name of the DB
     * @see org.pvemu.jelly.database.DatabaseHandler#init() 
     */
    final static public ConfigItem<String> DB_NAME           = new ConfigStringItem("DB_NAME", "pvemu");
    /**
     * number of parallele connexion
     * @see org.pvemu.jelly.database.DatabaseHandler#init() 
     */
    final static public ConfigItem<Integer> DB_POOL_SIZE     = new ConfigIntItem("DB_POOL_SIZE", 8);
    
    /**
     * Crypt the server IP
     */
    final static public ConfigItem<Boolean> CRYPT_IP         = new ConfigBoolItem("CRYPT_IP", false);
    /**
     * The game server IP
     */
    final static public ConfigItem<String> IP                = new ConfigStringItem("IP", "127.0.0.1");
    /**
     * Game server port (with a number < 1, a valid port is selected)
     */
    final static public ConfigItem<Integer> GAME_PORT        = new ConfigIntItem("GAME_PORT", 5555);
    /**
     * Realm server port
     */
    final static public ConfigItem<Integer> REALM_PORT       = new ConfigIntItem("REALM_PORT", 443);
    
    /**
     * Maximum number of characters per account
     */
    final static public ConfigItem<Integer> CHAR_PER_ACCOUNT = new ConfigIntItem("CHAR_PER_ACCOUNT", 5);
    
    /**
     * Start level of a character
     */
    final static public ConfigItem<Integer> START_LEVEL      = new ConfigIntItem("START_LEVEL", 1);
    /**
     * win xp rate on defiance
     */
    final static public ConfigItem<Integer> RATE_DEFIANCE    = new ConfigIntItem("RATE_DEFIANCE", 0);
    /**
     * win xp rate on pvm
     */
    final static public ConfigItem<Integer> RATE_PVM         = new ConfigIntItem("RATE_PVM", 1);
    
    /**
     * respawn time of monster in seconds
     */
    final static public ConfigItem<Integer> RESPAWN_TIME     = new ConfigIntItem("RESPAWN_TIME", 3);
    
    /**
     * Preload (save all datas into ram) on start
     */
    final static public ConfigItem<Boolean> PRELOAD          = new ConfigBoolItem("PRELOAD", false);
    
    /**
     * List of plugins to load
     */
    final static public ConfigItem<List<String>> PLUGINS     = new ConfigListStringItem("PLUGINS", new ArrayList<String>());
    
    /**
     * Language to use
     */
    final static public ConfigItem<String> LANG              = new ConfigStringItem("LANG", "en");
    
    abstract static public class ConfigItem<T>{
        protected String name;
        protected T value;

        protected ConfigItem(String name, T value) {
            this.name = name;
            this.value = value;
            items.put(name.toUpperCase(), this);
        }

        public T getValue() {
            return value;
        }
        
        abstract protected void setValue(String value) throws Exception;
        
    }
    
    static private class ConfigStringItem extends ConfigItem<String>{

        public ConfigStringItem(String name, String value) {
            super(name, value);
        }

        @Override
        protected void setValue(String value) {
            this.value = value;
        }
        
    }
    
    static private class ConfigIntItem extends ConfigItem<Integer>{

        public ConfigIntItem(String name, Integer value) {
            super(name, value);
        }

        @Override
        protected void setValue(String value) {
            this.value = Integer.parseInt(value);
        }
        
    }
    
    static private class ConfigBoolItem extends ConfigItem<Boolean>{

        public ConfigBoolItem(String name, Boolean value) {
            super(name, value);
        }

        @Override
        protected void setValue(String value) {
            this.value = Boolean.parseBoolean(value);
        }
        
    }
    
    static private class ConfigListStringItem extends ConfigItem<List<String>>{

        public ConfigListStringItem(String name, List<String> value) {
            super(name, value);
        }

        @Override
        protected void setValue(String value) throws Exception {
            for(String str : Utils.split(value, " ")){
                str = str.trim();
                
                if(!str.isEmpty())
                    this.value.add(str);
            }
        }
        
    }
    
    /**
     * Load the configuration file
     */
    static public void load(){
        Shell.print(I18n.tr(Commons.LOADING, I18n.tr(Commons.CONFIG)), Shell.GraphicRenditionEnum.YELLOW);
        
        File f = new File(Constants.CONFIG_FILE);

        try (FileReader FR = new FileReader(f);
             BufferedReader file = new BufferedReader(FR)){
            String line;

            while ((line = file.readLine()) != null) {
                line = line.trim();
                if(line.isEmpty() || line.charAt(0) == '#'){ //comment line
                    continue;
                }
                
                String param, value;
                
                try {
                    String[] split = Utils.split(line, "=");
                    param = split[0].toUpperCase().trim();
                    value = split[1].trim();
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }

                ConfigItem item = items.get(param);
                
                if(item == null){
                    continue;
                }
                
                try{
                    item.setValue(value);
                }catch(Exception e){
                    Loggin.error(I18n.tr(Commons.INVALID_CONFIG_VALUE, value, param), e);
                    System.exit(1);
                }
            }
            
            Shell.println("Ok", Shell.GraphicRenditionEnum.GREEN);
        } catch (Exception ex) {
            Loggin.error("Erreur lors du chargement de la config", ex);
            System.exit(1);
        }

    }
}
