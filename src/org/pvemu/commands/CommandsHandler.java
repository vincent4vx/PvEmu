/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CommandsHandler {
    final static private CommandsHandler instance = new CommandsHandler();
    final private HashMap<String, Command> commands = new HashMap<>();

    private CommandsHandler() {
        registerCommand(new EchoCommand());
        registerCommand(new HelpCommand());
        registerCommand(new SendCommand());
    }
    
    /**
     * Register a new command
     * @param cmd command to register
     */
    final public void registerCommand(Command cmd){
        commands.put(cmd.name(), cmd);
    }
    
    /**
     * Get the list of registered commands
     * @return the list of registered commands
     */
    final public Collection<Command> getCommandList(){
        return commands.values();
    }
    
    /**
     * Return the command by its name
     * @param name the command's name
     * @return the command object
     */
    final public Command getCommandByName(String name){
        return commands.get(name);
    }
    
    /**
     * Execute a command
     * @param commandLine the command line
     * @param asker the Asker
     */
    final public void execute(String commandLine, Asker asker){
        //String[] args = commandLine.split("\\s+");
        String[] args = parseCommand(commandLine, asker);
        
        Command cmd = commands.get(args[0]);
        
        if(cmd == null || !asker.corresponds(cmd.conditions())){
            asker.writeError("Commande indisponible !");
            return;
        }
        
        cmd.perform(args, asker);
    }
    
    private String[] parseCommand(String command, Asker asker){
        String[] args = command.split("\\s+");
        
        for(int i = 0; i < args.length; ++i){
            if(args[i].charAt(0) == '%'){
                switch(args[i]){
                    case "%me":
                        args[i] = asker.name();
                        break;
                }
            }
        }
        
        return args;
    }
    
    /**
     * Get the instance
     * @return 
     */
    final static public CommandsHandler instance(){
        return instance;
    }
}
