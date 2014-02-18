/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.jelly.Utils;

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
            "echo [text] [...] : affiche les paramètres donnés séparés par un espace"
        };
    }

    @Override
    public void perform(String[] args, Asker asker) {
        asker.write(Utils.join(args, " "));
    }
    
}
