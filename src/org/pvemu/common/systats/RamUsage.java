/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.systats;

import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.SyStats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class RamUsage implements StatsLine{

    @Override
    public String label() {
        return I18n.tr(SyStats.RAM_USAGE);
    }

    @Override
    public void displayValue() {
        long ram = getRamUsage();
        
        Shell.GraphicRenditionEnum gre;
        
        if(ram < 50)
            gre = Shell.GraphicRenditionEnum.GREEN;
        else if(ram < 150)
            gre = Shell.GraphicRenditionEnum.CYAN;
        else if(ram < 400)
            gre = Shell.GraphicRenditionEnum.BLUE;
        else if(ram < 700)
            gre = Shell.GraphicRenditionEnum.YELLOW;
        else
            gre = Shell.GraphicRenditionEnum.RED;
        
        Shell.println(ram + " Mio", gre);
    }
    
    private long getRamUsage() {
        return Runtime.getRuntime().totalMemory() / (1024 * 1024);
    }
}
