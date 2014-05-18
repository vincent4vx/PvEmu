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
import org.pvemu.common.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AliasCommand extends Command {

    @Override
    public String name() {
        return "alias";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        if(args.size() == 1){
            aliasList(asker);
        }else if(args.size() > 2){
            createAlias(args, asker);
        }
    }
    
    private void aliasList(Asker asker){
        boolean empty = true;
        for(Command cmd : CommandsHandler.instance().getCommandList()){
            if(cmd instanceof LinkedCommand){
                asker.write(cmd.title());
                empty = false;
            }
        }
        
        if(empty)
            asker.writeError(I18n.tr(Commands.NO_ALIAS_FOUND));
        
    }
    
    private void createAlias(ArgumentList args, Asker asker) throws CommandArgumentException{
        String alias = args.getArgument(1);
        String commandLine = args.getArgument(2);
        String name = Utils.split(commandLine.trim(), " ", 2)[0];
        
        if(CommandsHandler.instance().getCommandByName(alias) != null){
            asker.writeError(I18n.tr(Commands.ALIAS_ALREADY_EXISTS, alias));
            return;
        }
        
        Command cmd;
        if((cmd = CommandsHandler.instance().getCommandByName(name)) == null){
            asker.writeError(I18n.tr(Commands.UNAVAILABLE_COMMAND, name));
            return;
        }
        
        Command aliasCmd = new LinkedCommand(alias, commandLine, cmd);
        
        CommandsHandler.instance().registerCommand(aliasCmd);
        asker.write(I18n.tr(Commands.ALIAS_CREATED, aliasCmd.title()));
    }

    @Override
    public String[] usage() {
        return new String[]{
            I18n.tr(Commands.ALIAS_USAGE1),
            I18n.tr(Commands.ALIAS_USAGE2),
            I18n.tr(Commands.ALIAS_USAGE3)
        };
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminAskerFilter();
    }
       
}
