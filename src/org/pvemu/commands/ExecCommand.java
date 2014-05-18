/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;
import org.pvemu.common.i18n.translation.Commons;
import org.pvemu.common.scripting.API;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ExecCommand extends Command {

    @Override
    public String name() {
        return "exec";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        if(args.size() == 1){
            asker.writeError(I18n.tr(Commands.INVALID_ARGUMENTS_NUMBER));
            return;
        }
            
        for(int i = 1; i < args.size(); ++i){
            long time = System.currentTimeMillis();
            API.execute(args.getArgument(i));
            asker.write(I18n.tr(Commons.LOADED_IN, args.getArgument(i), (System.currentTimeMillis() - time)));
        }
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminConsoleFilter();
    }

    @Override
    public String[] usage() {
        return new String[]{
            I18n.tr(Commands.EXEC_USAGE1),
            I18n.tr(Commands.EXEC_USAGE2)
        };
    }

}
