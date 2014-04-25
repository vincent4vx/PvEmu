/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

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

    @Override
    public String name() {
        return "add";
    }

    @Override
    public void perform(String[] args, Asker asker) {
        if(args.length < 3){
            asker.writeError("Nombre de paramètres invalide !");
            return;
        }
        
        Player target;
        
        if(args.length < 4){
            if(!(asker instanceof ClientAsker)){
                asker.writeError("Vous devez être en jeu !");
                return;
            }
            
            target = SessionAttributes.PLAYER.getValue(((ClientAsker)asker).getAccount().getSession());
        }else{
            target = World.instance().getOnlinePlayer(args[3].trim());
        }
        
        if(target == null){
            asker.writeError("Impossible de trouver le peronnage !");
            return;
        }
        
        short qu;
        
        try{
            qu = Short.parseShort(args[2]);
        }catch(NumberFormatException e){
            asker.writeError("Argument n°2 invalide !");
            return;
        }
        
        if(qu <= 0){
            asker.writeError("Argument n°2 invalide !");
            return;
        }
        
        switch(args[1].toLowerCase()){
            case "spellpoints":
                addSpellPoints(target, qu, asker);
                break;
            case "boostpoints":
                addBoostPoints(target, qu, asker);
                break;
            default:
                asker.writeError("Argument n°1 invalide !");
        }
    }
    
    private void addSpellPoints(Player target, short qu, Asker asker){
        target.getCharacter().spellPoints += qu;
        GameSendersRegistry.getPlayer().stats(target, target.getSession());
        asker.write(qu + " points de sort ont été ajouté au joueur " + target.getName() + ". Il en a maintenant " + target.getCharacter().spellPoints);
    }
    
    private void addBoostPoints(Player target, short qu, Asker asker){
        target.getCharacter().boostPoints += qu;
        GameSendersRegistry.getPlayer().stats(target, target.getSession());
        asker.write(qu + " points de boost ont été ajouté au joueur " + target.getName() + ". Il en a maintenant " + target.getCharacter().boostPoints);
    }

    @Override
    public Filter conditions() {
        return FilterFactory.moderatorAskerFilter();
    }

    @Override
    public String[] usage() {
        return new String[]{
            "Ajoute des élément à un joueur",
            "Schéma général : add [element] [qu] {player}",
            "Si {player} n'est pas fournis, le joueur courant est pris.",
            "add spellpoints [qu] {player} - ajoute [qu] points de sort au joueur {player}",
            "add boostpoints [qu] {player} - ajoute [qu] points de boost (stats) au joueur {player}"
        };
    }
    
}
