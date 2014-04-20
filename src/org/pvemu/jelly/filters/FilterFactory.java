/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.jelly.filters;

import org.pvemu.jelly.filters.comparators.EqualComparator;
import org.pvemu.jelly.filters.comparators.MoreThanComparator;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class FilterFactory {
    static private AskerFilter adminAskerFilter = null;
    final static private Filter yesFilter = new YesFilter();
    static private AskerFilter moderatorAskerFilter = null;
    static private AskerFilter adminConsoleFilter = null;
    static private PlayerFilter playerNotInFightFilter = null;
    
    static public Filter adminAskerFilter(){
        if(adminAskerFilter == null){
            adminAskerFilter = new AskerFilter();
            adminAskerFilter.setLevel(new MoreThanComparator((byte)3));
        }
        return adminAskerFilter;
    }
    
    static public Filter adminConsoleFilter(){
        if(adminConsoleFilter == null){
            adminConsoleFilter = new ConsoleAskerFilter();
            adminConsoleFilter.setLevel(new MoreThanComparator((byte)3));
        }
        return adminConsoleFilter;
    }
    
    static public Filter moderatorAskerFilter(){
        if(moderatorAskerFilter == null){
            moderatorAskerFilter = new AskerFilter();
            moderatorAskerFilter.setLevel(new MoreThanComparator((byte)2));
        }
        return moderatorAskerFilter;
    }
    
    static public Filter yesFilter(){
        return yesFilter;
    }
    
    static public Filter playerNotInFightFilter(){
        if(playerNotInFightFilter == null){
            playerNotInFightFilter = new PlayerFilter();
            playerNotInFightFilter.setInFight(new EqualComparator<>(false));
        }
        
        return playerNotInFightFilter;
    }
}
