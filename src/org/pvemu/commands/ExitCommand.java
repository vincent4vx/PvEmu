/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.askers.ClientAsker;
import org.pvemu.common.filters.AskerFilter;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;
import org.pvemu.common.filters.comparators.MoreThanComparator;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ExitCommand extends Command {

    @Override
    public String name() {
        return "exit";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) {
        if(asker instanceof ClientAsker){
            ((ClientAsker)asker).getAccount().getSession().close(false); //kick the asker
        }
        
        //Exit in a new thread (to preserve dead lock)
        new Thread(){
            @Override
            public void run(){
                System.exit(0);
            }
        }.start();
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminAskerFilter();
    }

    @Override
    public String[] usage() {
        return new String[]{
            "Arrête le serveur sans préavis, sécurités, sauvegardes...",
            "exit (pas de paramètres)",
            "Attention : A utiliser avec prudence !"
        };
    }

    @Override
    public String title() {
        return name() + " - arrêt brusque du serveur";
    }
    
    
    
}
