/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.jelly.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class LinkedCommand extends Command {
    final private String name;
    final private Command linkedCommand;
    final private String[] args;

    public LinkedCommand(String name, Command linkedCommand, String[] args) {
        this.name = name;
        this.linkedCommand = linkedCommand;
        this.args = args;
    }   

    @Override
    public String name() {
        return name;
    }

    @Override
    public String title() {
        return name + " -> " + linkedCommand.name();
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        /*String[] finalArgs = new String[this.args.length + args.length];
        
        finalArgs[0] = args[0]; //set the correct name of the command
        System.arraycopy(this.args, 0, finalArgs, 1, this.args.length); //fill with default args in alias
        System.arraycopy(args, 1, finalArgs, this.args.length + 1, args.length - 1); //fill with rest of args
        
        linkedCommand.perform(finalArgs, asker);*/
    }

    @Override
    public Filter conditions() {
        return linkedCommand.conditions();
    }

    @Override
    public String[] usage() {
        return linkedCommand.usage();
    }    
    
}
