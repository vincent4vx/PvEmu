/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.askers.Asker;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.jelly.filters.ConsoleAskerFilter;
import org.pvemu.jelly.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class EchoCommand extends Command {

    @Override
    public String name() {
        return "echo";
    }
    
    @Override
    public String[] usage(){
        return new String[]{
            "Affiche un texte.",
            "echo {texte ...} : affiche les paramètres donnés séparés par un espace"
        };
    }

    @Override
    public void perform(String[] args, Asker asker) {
        String[] params = new String[args.length - 1];
        System.arraycopy(args, 1, params, 0, params.length);
        asker.write(Utils.join(params, " "));
    }

    @Override
    public Filter conditions() {
        return new ConsoleAskerFilter();
    }
    
    
    
}
