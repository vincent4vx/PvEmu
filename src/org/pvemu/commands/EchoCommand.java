/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.filters.ConsoleAskerFilter;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;

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
            I18n.tr(Commands.ECHO_USAGE1),
            I18n.tr(Commands.ECHO_USAGE2)
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
