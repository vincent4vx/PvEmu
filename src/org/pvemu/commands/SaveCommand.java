/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.askers.Asker;
import org.pvemu.game.World;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SaveCommand extends Command{

    @Override
    public String name() {
        return "save";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) {
        asker.write("Sauvegarde en cours...");
        World.instance().save();
        asker.write("Sauvegarde terminé !");
    }

    @Override
    public Filter conditions() {
        return FilterFactory.moderatorAskerFilter();
    }
    
    
}
