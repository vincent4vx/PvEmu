/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;
import java.util.ArrayList;
import java.util.Collection;
import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class HelpCommand extends Command {

    @Override
    public String name() {
        return "help";
    }
    
    @Override
    public String[] usage(){
        return new String[]{
            I18n.tr(Commands.HELP_USAGE1),
            I18n.tr(Commands.HELP_USAGE2),
            I18n.tr(Commands.HELP_USAGE3)
        };
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException {
        if(args.size() == 1){
            showCommandsList(asker);
        }else{
            showCommandInfo(args.getArgument(1), asker);
        }
    }
    
    private void showCommandsList(Asker asker){
        asker.write(I18n.tr(Commands.AVAILABLE_COMMANDS));
        
        Collection<Command> commands = new ArrayList<>();
        
        for(Command cmd : CommandsHandler.instance().getCommandList()){
            if(asker.corresponds(cmd.conditions())){
                commands.add(cmd);
            }
        }
        
        if(commands.isEmpty()){
            asker.writeError(I18n.tr(Commands.NO_COMMANDS_AVAILABLE));
            return;
        }
        
        for(Command cmd : commands){
            asker.write(cmd.title());
        }
    }
    
    private void showCommandInfo(String cmdName, Asker asker){
        Command cmd = CommandsHandler.instance().getCommandByName(cmdName);
        
        if(cmd == null)
            asker.writeError(I18n.tr(Commands.UNAVAILABLE_COMMAND, cmdName));
        else{
            asker.write(I18n.tr(Commands.COMMAND, cmd.name()));
            asker.write(I18n.tr(Commands.USAGE));
            for(String msg : cmd.usage())
                asker.write(msg);
        }
    }
    
}
