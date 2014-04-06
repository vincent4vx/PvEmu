/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;
import org.pvemu.jelly.Jelly;
import org.pvemu.jelly.filters.AskerFilter;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.comparators.MoreThanComparator;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DebugCommand extends Command {

    @Override
    public String name() {
        return "debug";
    }

    @Override
    public void perform(String[] args, Asker asker) {
        if(args.length == 1){
            Jelly.DEBUG = !Jelly.DEBUG;
        }else{
            try{
                Jelly.DEBUG = Boolean.parseBoolean(args[1]);
            }catch(Exception e){
                asker.writeError("Argument " + args[1] + " invalide !");
                return;
            }
        }
        
        asker.write("Mode DEBUG " + (Jelly.DEBUG ? "activé" : "désactivé") + " !");
    }

    @Override
    public Filter conditions() {
        AskerFilter filter = new AskerFilter();
        
        filter.setLevel(new MoreThanComparator((byte)3));
        
        return filter;
    }

    @Override
    public String[] usage() {
        return new String[]{
            "Active / désactive le mode DEBUG",
            "debug : change le statut du mode DEBUG courant",
            "debug [true/false] : active / désactive le mode DEBUG"
        };
    }
    
    
}
