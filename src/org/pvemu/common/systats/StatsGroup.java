/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.common.systats;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.common.Shell;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class StatsGroup {
    final private String title;
    final private List<StatsLine> lines = new ArrayList<>();

    public StatsGroup(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    
    public void display(){
        Shell.println(title, Shell.GraphicRenditionEnum.BOLD);
        
        int length = getMaxLength();
        
        for(StatsLine line : lines){
            StringBuilder sb = new StringBuilder(line.label());
            
            while(sb.length() < length)
                sb.append(' ');
            
            Shell.print(sb.toString() + " : ", Shell.GraphicRenditionEnum.YELLOW);
            line.displayValue();
        }
    }
    
    public void addStatsLine(StatsLine line){
        lines.add(line);
    }
    
    private int getMaxLength(){
        int length = 0;
        
        for(StatsLine line : lines){
            if(line.label().length() > length)
                length = line.label().length();
        }
        
        return length;
    }
}
