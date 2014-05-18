/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;
import org.pvemu.network.Sessionable;

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
        if(asker instanceof Sessionable){
            ((Sessionable)asker).getSession().close(false); //kick the asker
        }
        
        System.exit(0);
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminAskerFilter();
    }

    @Override
    public String[] usage() {
        return new String[]{
            I18n.tr(Commands.EXIT_USAGE1),
            I18n.tr(Commands.EXIT_USAGE2),
            I18n.tr(Commands.EXIT_USAGE3)
        };
    }    
}
