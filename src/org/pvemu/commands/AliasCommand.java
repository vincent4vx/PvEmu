/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;

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
    public void perform(String[] args, Asker asker) {
        if(args.length == 1){
            aliasList(asker);
        }else if(args.length > 2){
            createAlias(args, asker);
        }else{
            asker.writeError("Commande invalide. Veuillez vous référer à help alias");
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
    
    private void createAlias(String[] args, Asker asker){
        String alias = args[1];
        String name = args[2];
        
        if(CommandsHandler.instance().getCommandByName(alias) != null){
            asker.writeError("Commande ou alias '" + alias + "' déjà utilisé !");
            return;
        }
        
        Command cmd;
        if((cmd = CommandsHandler.instance().getCommandByName(name)) == null){
            asker.writeError("Commande '" + name + "' inéxistante !");
            return;
        }
        
        String[] aliasArgs = new String[args.length - 3];
        System.arraycopy(args, 3, aliasArgs, 0, aliasArgs.length);
        LinkedCommand aliasCmd = new LinkedCommand(alias, cmd, aliasArgs);
        
        CommandsHandler.instance().registerCommand(aliasCmd);
        asker.write("Alias '" + aliasCmd.title() + "' créé.");
    }

    @Override
    public String[] usage() {
        return new String[]{
            "Crée un alias.",
            "alias (pas d'arguments) : affiche la liste des alias disponibles",
            "alias [nom de l'alias] [nom de la commande] {params...} : ajoute un alias"
        };
    }
       
}
