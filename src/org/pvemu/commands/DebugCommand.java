package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.Jelly;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;

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
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        if(args.size() == 1){
            Jelly.DEBUG = !Jelly.DEBUG;
        }else{
            Jelly.DEBUG = args.getBoolean(1);
        }
        
        asker.write("Mode DEBUG " + (Jelly.DEBUG ? "activé" : "désactivé") + " !");
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminAskerFilter();
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
