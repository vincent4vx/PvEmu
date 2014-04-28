/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly;

import org.pvemu.jelly.utils.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Config {
    final static private HashMap<String, ConfigItem> items = new HashMap<>();
    
    final static public ConfigItem<String> DB_HOST           = new ConfigStringItem("DB_HOST", "127.0.0.1");
    final static public ConfigItem<String> DB_USER           = new ConfigStringItem("DB_USER", "root");
    final static public ConfigItem<String> DB_PASS           = new ConfigStringItem("DB_PASS", "");
    final static public ConfigItem<String> DB_NAME           = new ConfigStringItem("DB_NAME", "pvemu");
    final static public ConfigItem<Integer> DB_COMMIT_TIME   = new ConfigIntItem("DB_COMMIT_TIME", 600);
    final static public ConfigItem<Integer> DB_POOL_SIZE     = new ConfigIntItem("DB_POOL_SIZE", 8);
    
    final static public ConfigItem<Boolean> CRYPT_IP         = new ConfigBoolItem("CRYPT_IP", false);
    final static public ConfigItem<String> IP                = new ConfigStringItem("IP", "127.0.0.1");
    final static public ConfigItem<Integer> GAME_PORT        = new ConfigIntItem("GAME_PORT", 5555);
    final static public ConfigItem<Integer> REALM_PORT       = new ConfigIntItem("REALM_PORT", 443);
    
    final static public ConfigItem<Integer> CHAR_PER_ACCOUNT = new ConfigIntItem("CHAR_PER_ACCOUNT", 5);
    
    final static public ConfigItem<Integer> START_LEVEL      = new ConfigIntItem("START_LEVEL", 1);
    
    final static public ConfigItem<Boolean> PRELOAD          = new ConfigBoolItem("PRELOAD", false);
    
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
    
    static public void load(){
        Shell.print("Chargement de la configuration : ", Shell.GraphicRenditionEnum.YELLOW);
        
        File f = new File(Constants.CONFIG_FILE);

        try {
            FileReader FR = new FileReader(f);
            BufferedReader file = new BufferedReader(FR);
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
                    Loggin.debug("Item de configuration '%s' introuvable.", param);
                    continue;
                }
                
                try{
                    item.setValue(value);
                }catch(Exception e){
                    Loggin.error("Valeur '" + value + "' incorrecte pour '" + param + "'", e);
                }
            }

            file.close();
            FR.close();
            Shell.println("Ok", Shell.GraphicRenditionEnum.GREEN);
        } catch (Exception ex) {
            Loggin.error("Erreur lors du chargement de la config", ex);
            System.exit(1);
        }

    }
}
