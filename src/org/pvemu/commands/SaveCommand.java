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
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commons;

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
        asker.write(I18n.tr(Commons.SAVING));
        World.instance().save();
        asker.write(I18n.tr(Commons.SAVING_OK));
    }

    @Override
    public Filter conditions() {
        return FilterFactory.moderatorAskerFilter();
    }
    
    
}
