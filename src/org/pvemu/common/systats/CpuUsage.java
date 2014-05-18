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
public class CpuUsage implements StatsLine{
    final private ThreadMXBean TMB = ManagementFactory.getThreadMXBean();
    
    private long lastNanoTime = 0;
    private long lastCpuTime = 0;

    @Override
    public String label() {
        return I18n.tr(SyStats.CPU_USAGE);
    }

    @Override
    public void displayValue() {
        double cpu = getCpuUsage() * 100;
        
        String tmp = String.valueOf(cpu);
        
        if(tmp.length() > 3)
            tmp = tmp.substring(0, 4);
        
        String msg = tmp + "%";
        Shell.GraphicRenditionEnum color;
        
        if(cpu >= 75){
            color = Shell.GraphicRenditionEnum.RED;
        }else if(cpu >= 50){
            color = Shell.GraphicRenditionEnum.YELLOW;
        }else if(cpu >= 25){
            color = Shell.GraphicRenditionEnum.BLUE;
        }else{
            color = Shell.GraphicRenditionEnum.GREEN;
        }
        
        Shell.println(msg, color);
    }
    
    private double getCpuUsage(){
        long time = System.nanoTime();
        double timeDiff = time - lastNanoTime;
        
        long cpuTime = 0;
        
        for(long id : TMB.getAllThreadIds()){
            cpuTime += TMB.getThreadCpuTime(id);
        }
        
        double cpuDiff = cpuTime - lastCpuTime;
        
        if(cpuDiff < 0)
            cpuDiff = 0;
        
        lastNanoTime = time;
        lastCpuTime = cpuTime;
        
        return cpuDiff / timeDiff / Runtime.getRuntime().availableProcessors();
    }
    
}
