/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.utils.Utils;
import org.pvemu.common.filters.ConsoleAskerFilter;
import org.pvemu.common.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class EchoCommand extends Command {

    @Override
    public String name() {
        return "echo";
    }
    
    @Override
    public String[] usage(){
        return new String[]{
            "Affiche un texte.",
            "echo {texte ...} : affiche les paramètres donnés séparés par un espace"
        };
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        for(int i = 1; i < args.size(); ++i){
            asker.write(args.getArgument(i));
        }
    }

    @Override
    public Filter conditions() {
        return new ConsoleAskerFilter();
    }
    
    
    
}
