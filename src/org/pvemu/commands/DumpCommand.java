package org.pvemu.commands;

import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.CommandArgumentException;
import org.pvemu.commands.argument.RequiredArgumentException;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.filters.Filter;
import org.pvemu.common.filters.FilterFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DumpCommand  extends Command{

    @Override
    public String name() {
        return "dump";
    }

    @Override
    public void perform(ArgumentList args, Asker asker) throws CommandArgumentException {
        if(args.size() == 1)
            throw new RequiredArgumentException(1);
        
        for(int i = 1; i < args.size(); ++i){
            asker.write(args.getList(i).toString());
        }
    }

    @Override
    public Filter conditions() {
        return FilterFactory.adminAskerFilter();
    }
    
}
