package org.pvemu.common.systats;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.pvemu.common.PvEmu;
import org.pvemu.common.Shell;
import org.pvemu.common.i18n.I18n;
import org.pvemu.common.i18n.translation.SyStats;

public class SystemStats {
    static private SystemStatsContent content = null;
    
    public static void init(){
        if(content == null){
            content = defaultContent();
        }
        
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                new Runnable(){
                    @Override
                    public void run(){
                        if(PvEmu.DEBUG){
                            return;
                        }
                        Shell.clear();
                        content.display();
                    }
                }, 0, 1, TimeUnit.SECONDS
        );
    }
    
    public static SystemStatsContent defaultContent(){
        SystemStatsContent ssc = new SystemStatsContent();
        
        StatsGroup sockets = new StatsGroup(I18n.tr(SyStats.SOCKETS_INFO));
        sockets.addStatsLine(new RecvPackets());
        sockets.addStatsLine(new SentPackets());
        sockets.addStatsLine(new ConnexionsNumber());
        ssc.addStatsGroup(sockets);
        
        StatsGroup system = new StatsGroup(I18n.tr(SyStats.SYSTEM_INFO));
        system.addStatsLine(new RamUsage());
        system.addStatsLine(new ThreadsNumber());
        system.addStatsLine(new CpuUsage());
        ssc.addStatsGroup(system);
        
        StatsGroup game = new StatsGroup(I18n.tr(SyStats.GAME_INFO));
        game.addStatsLine(new OnlineCount());
        game.addStatsLine(new Uptime());
        game.addStatsLine(new ErrorsCount());
        ssc.addStatsGroup(game);
        
        return ssc;
    }

    public static SystemStatsContent getContent() {
        return content;
    }

    public static void setContent(SystemStatsContent content) {
        SystemStats.content = content;
    }
}
