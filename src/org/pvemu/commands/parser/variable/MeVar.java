/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.parser.variable;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.askers.ClientAsker;
import org.pvemu.jelly.filters.ClientAskerFilter;
import org.pvemu.jelly.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MeVar implements DynamicVar{

    @Override
    public String name() {
        return "me";
    }

    @Override
    public List<String> getValue(Asker asker) {
        List<String> list = new ArrayList<>();
        list.add(((ClientAsker)asker).name());
        return list;
    }

    @Override
    public Filter condition() {
        return new ClientAskerFilter();
    }
    
}
