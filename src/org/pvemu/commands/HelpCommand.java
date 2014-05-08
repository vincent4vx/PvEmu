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
            "Retourne la liste des commandes disponibles, ou donne l'utilisation d'une commande.",
            "help (pas de paramètres) : liste des commandes disponibles",
            "help [cmd] : donne l'aide sur la commande demandé"
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
        asker.write("Liste des commandes disponibles :");
        
        Collection<Command> commands = new ArrayList<>();
        
        for(Command cmd : CommandsHandler.instance().getCommandList()){
            if(asker.corresponds(cmd.conditions())){
                commands.add(cmd);
            }
        }
        
        if(commands.isEmpty()){
            asker.writeError("Aucunes commandes disponibles.");
            return;
        }
        
        for(Command cmd : commands){
            asker.write(cmd.title());
        }
    }
    
    private void showCommandInfo(String cmdName, Asker asker){
        Command cmd = CommandsHandler.instance().getCommandByName(cmdName);
        
        if(cmd == null)
            asker.writeError("Commande non trouvée !");
        else{
            asker.write("Commande : " + cmd.name());
            asker.write("Utilisation : ");
            for(String msg : cmd.usage())
                asker.write(msg);
        }
    }
    
}
