package org.pvemu.common.systats;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.SyStats;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SystemStatsContent {
    final private List<StatsGroup> groups = new ArrayList<>();
    
    public void addStatsGroup(StatsGroup group){
        groups.add(group);
    }
    
    public void display(){
        Shell.println(I18n.tr(SyStats.STATISTICS), Shell.GraphicRenditionEnum.YELLOW);
        Shell.println("===========================\n", Shell.GraphicRenditionEnum.YELLOW);
        
        for(StatsGroup group : groups){
            group.display();
            System.out.println();
        }
    }
}
