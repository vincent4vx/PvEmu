/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.commands.askers;

import org.pvemu.jelly.Shell;
import org.pvemu.jelly.Shell.GraphicRenditionEnum;
import org.pvemu.jelly.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SystemAsker implements Asker {

    @Override
    public String name() {
        return "Système";
    }

    @Override
    public void write(String msg) {
        Shell.println(msg, GraphicRenditionEnum.YELLOW, GraphicRenditionEnum.ITALIC);
    }

    @Override
    public void writeError(String msg) {
        Shell.println(msg, GraphicRenditionEnum.RED, GraphicRenditionEnum.ITALIC);
    }

    @Override
    public byte level() {
        return Byte.MAX_VALUE;
    }

    @Override
    public boolean corresponds(Filter filter) {
        return true;
    }
    
}
