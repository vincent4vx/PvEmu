/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;
import org.pvemu.jelly.filters.ConsoleAskerFilter;
import org.pvemu.jelly.filters.Filter;
import org.pvemu.jelly.filters.comparators.MoreThanComparator;
import org.pvemu.jelly.scripting.API;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ExecCommand extends Command {

    @Override
    public String name() {
        return "exec";
    }

    @Override
    public void perform(String[] args, Asker asker) {
        if(args.length == 1){
            asker.writeError("Nombre d'arguments invalide !");
            return;
        }
            
        for(int i = 1; i < args.length; ++i){
            long time = System.currentTimeMillis();
            API.execute(args[i]);
            asker.write(args[i] + " chargé en " + (System.currentTimeMillis() - time) + "ms");
        }
    }

    @Override
    public Filter conditions() {
        ConsoleAskerFilter filter = new ConsoleAskerFilter();
        
        filter.setLevel(new MoreThanComparator<>((byte)3));
        
        return filter;
    }

    @Override
    public String[] usage() {
        return new String[]{
            "Charge et exécute un (ou plusieurs) fichier(s) javascript.",
            "Seul les administrateurs (level > 3) peuvent exécuter cette commande.",
            "exec [script] [...]"
        };
    }

}
