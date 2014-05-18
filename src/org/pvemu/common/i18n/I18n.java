/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.i18n;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.common.Config;
import org.pvemu.common.utils.IPair;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class I18n {    
    final static private Map<String, Map<Translation, String>> translations = new HashMap<>();
    
    static public void register(Translations tr){
        Map<Translation, String> map = translations.get(tr.lang());
        
        if(map == null){
            map = new HashMap<>();
            translations.put(tr.lang(), map);
        }
        
        for(IPair<Translation, String>  pair : tr.entries()){
            map.put(pair.getFirst(), pair.getSecond());
        }
    }
    
    static public String tr(Translation tr, Object... args){
        String s = null;
        
        Map<Translation, String> map = translations.get(Config.LANG.getValue());
        
        if(map != null)
            s = map.get(tr);
        
        if(s == null)
            s = tr.defaultTranslation();
        
        return String.format(s, args);
    }
}
