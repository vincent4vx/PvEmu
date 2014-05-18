/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.systats;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.SyStats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ThreadsNumber implements StatsLine{
    final private ThreadMXBean TMB = ManagementFactory.getThreadMXBean();

    @Override
    public String label() {
        return I18n.tr(SyStats.THREADS_NUMBER);
    }

    @Override
    public void displayValue() {
        int count = getThreadCount();
        int coef = count / Runtime.getRuntime().availableProcessors();
        
        Shell.GraphicRenditionEnum gre;
        
        if(coef < 5)
            gre = Shell.GraphicRenditionEnum.GREEN;
        else if(coef < 10)
            gre = Shell.GraphicRenditionEnum.BLUE;
        else
            gre = Shell.GraphicRenditionEnum.RED;
        
        Shell.println("" + count, gre);
    }
    
    private int getThreadCount() {
        return TMB.getThreadCount();
    }
}
