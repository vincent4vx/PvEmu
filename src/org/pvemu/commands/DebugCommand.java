package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.PvEmu;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.Commands;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DebugCommand extends Command {

    @Override
    public String name() {
        return "debug";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException{
        if(args.size() == 1){
            PvEmu.DEBUG = !PvEmu.DEBUG;
        }else{
            PvEmu.DEBUG = args.getBoolean(1);
        }
        
        asker.write(
                I18n.tr(Commands.DEBUG_STATE, I18n.tr(PvEmu.DEBUG ? Commands.ENABLE : Commands.DISABLE))
        );
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminAskerFilter();
    }

    @Override
    public String[] usage() {
        return new String[]{
            I18n.tr(Commands.DEBUG_USAGE1),
            I18n.tr(Commands.DEBUG_USAGE2),
            I18n.tr(Commands.DEBUG_USAGE3)
        };
    }
    
    
}
