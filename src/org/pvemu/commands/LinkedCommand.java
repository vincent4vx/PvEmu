package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class LinkedCommand extends Command {
    final private String name;
    final private String commandLine;
    final private Command command;

    public LinkedCommand(String name, String commandLine, Command command) {
        this.name = name;
        this.commandLine = commandLine;
        this.command = command;
    }   

    @Override
    public String name() {
        return name;
    }

    @Override
    public String title() {
        return name + " -> " + commandLine;
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        try{
            ArgumentList aliasArgs = CommandsHandler.instance().getParser().parseCommand(commandLine, asker);
            ArgumentList newArgs = new ArgumentList();
            newArgs.addArgument(name);
            
            for(int i = 1; i < aliasArgs.size(); ++i){
                newArgs.addList(aliasArgs.getList(i));
            }
            
            for(int i = 1; i < args.size(); ++i){
                newArgs.addList(args.getList(i));
            }
            
            command.perform(newArgs, asker);
        }catch(Exception e){
            asker.writeError(e.getMessage());
        }
    }

    @Override
    public Filter conditions() {
        return command.conditions();
    }

    @Override
    public String[] usage() {
        return command.usage();
    }    
    
}
