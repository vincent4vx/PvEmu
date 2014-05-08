/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import java.util.HashMap;
import java.util.Map;
import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.askers.ClientAsker;
import org.pvemu.game.World;
import org.pvemu.game.objects.player.Player;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.FilterFactory;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AddCommand extends Command{
    private abstract interface AddSubCommand{
        String subName();
        String utility();
        void perform(Player target, int qu, Asker asker);
    }
    
    private class AddSpellPoints implements AddSubCommand{

        @Override
        public String subName() {
            return "spellpoints";
        }

        @Override
        public String utility() {
            return "Ajoute [qu] points de sort à {player}";
        }

        @Override
        public void perform(Player target, int qu, Asker asker) {
            target.getCharacter().spellPoints += qu;
            GameSendersRegistry.getPlayer().stats(target, target.getSession());
            asker.write(qu + " points de sort ont été ajouté au joueur " + target.getName() + ". Il en a maintenant " + target.getCharacter().spellPoints);
        }
        
    }
    
    private class AddBoostPoints implements AddSubCommand{

        @Override
        public String subName() {
            return "boostpoints";
        }

        @Override
        public String utility() {
            return "Ajoute [qu] points de boost (stats) à {player}";
        }

        @Override
        public void perform(Player target, int qu, Asker asker) {
            target.getCharacter().boostPoints += qu;
            GameSendersRegistry.getPlayer().stats(target, target.getSession());
            asker.write(qu + " points de boost ont été ajouté au joueur " + target.getName() + ". Il en a maintenant " + target.getCharacter().boostPoints);
        }
        
    }
    
    private class AddXpPoints implements AddSubCommand{

        @Override
        public String subName() {
            return "xp";
        }

        @Override
        public String utility() {
            return "Ajoute [qu] points d'xp à {player}";
        }

        @Override
        public void perform(Player target, int qu, Asker asker) {
            target.addXp(qu);
            asker.write(qu + " points  d'xp a été ajouté au joueur " + target.getName() + ". Il a maintenant " + target.getCharacter().experience + " points d'xp");
        }
        
    }
    
    final private Map<String, AddSubCommand> subCommands = new HashMap<>();
    
    public AddCommand() {
        registerSubCommand(new AddSpellPoints());
        registerSubCommand(new AddBoostPoints());
        registerSubCommand(new AddXpPoints());
    }
    
    private void registerSubCommand(AddSubCommand cmd){
        subCommands.put(cmd.subName().toLowerCase(), cmd);
    }

    @Override
    public String name() {
        return "add";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{        
        Player target;
        
        if(args.size() < 4){
            if(!(asker instanceof ClientAsker)){
                asker.writeError("Vous devez être en jeu !");
                return;
            }
            
            target = SessionAttributes.PLAYER.getValue(((ClientAsker)asker).getAccount().getSession());
        }else{
            target = World.instance().getOnlinePlayer(args.getArgument(3));
        }
        
        if(target == null){
            asker.writeError("Impossible de trouver le peronnage !");
            return;
        }
        
        int qu = args.getInteger(2);
        
        if(qu <= 0){
            asker.writeError("Argument n°2 invalide !");
            return;
        }
        
        AddSubCommand cmd = subCommands.get(args.getArgument(1).toLowerCase().trim());
        
        if(cmd == null){
            asker.writeError("Argument n°1 invalide !");
            return;
        }
        
        cmd.perform(target, qu, asker);
    }

    @Override
    public Filter conditions() {
        return FilterFactory.moderatorAskerFilter();
    }

    @Override
    public String[] usage() {
        String[] tmp = new String[3 + subCommands.size()];
        
        int i = 0;
        tmp[i++] = "Ajoute des éléments à un joueur";
        tmp[i++] = "Schéma général : add [element] [qu] {player}";
        tmp[i++] = "Si {player} n'est pas fournis, le joueur courant est pris.";
        
        for(AddSubCommand cmd : subCommands.values()){
            tmp[i++] = "add " + cmd.subName() + " [qu] {player} - " + cmd.utility();
        }
        
        return tmp;
    }
    
}
