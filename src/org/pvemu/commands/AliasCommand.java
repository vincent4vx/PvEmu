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
import org.pvemu.jelly.filters.FilterFactory;

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
            asker.writeError("Aucuns alias disponibles !");
        
    }
    
    private void createAlias(ArgumentList args, Asker asker) throws CommandArgumentException{
        String alias = args.getArgument(1);
        String name = args.getArgument(2);
        
        if(CommandsHandler.instance().getCommandByName(alias) != null){
            asker.writeError("Commande ou alias '" + alias + "' déjà utilisé !");
            return;
        }
        
        Command cmd;
        if((cmd = CommandsHandler.instance().getCommandByName(name)) == null){
            asker.writeError("Commande '" + name + "' inéxistante !");
            return;
        }
        
        /*String[] aliasArgs = new String[args.length - 3];
        System.arraycopy(args, 3, aliasArgs, 0, aliasArgs.length);
        LinkedCommand aliasCmd = new LinkedCommand(alias, cmd, aliasArgs);
        
        CommandsHandler.instance().registerCommand(aliasCmd);
        asker.write("Alias '" + aliasCmd.title() + "' créé.");*/
    }

    @Override
    public String[] usage() {
        return new String[]{
            "Crée un alias.",
            "alias (pas d'arguments) : affiche la liste des alias disponibles",
            "alias [nom de l'alias] [nom de la commande] {params...} : ajoute un alias"
        };
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminAskerFilter();
    }
       
}
