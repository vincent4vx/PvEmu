/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.pvemu.game.objects.monster.MonsterFactory;
import org.pvemu.game.objects.monster.MonsterGroup;
import org.pvemu.common.Config;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 * handle map timers
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class MapTimers {
    final static private ScheduledExecutorService timers = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    
    static public void registerMonstersRespawnTimer(final GameMap map){
        timers.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(map.getModel().numgroup <= map.getCurrentGroupCount() || map.getAvailableMonsters().isEmpty())
                    return;
                
                MonsterGroup group = MonsterFactory.generateMonsterGroup(map.getAvailableMonsters(), map);
                map.addMonsterGroup(group);
                GameSendersRegistry.getMap().addGMable(map, group);
            }
        }, Config.RESPAWN_TIME.getValue(), Config.RESPAWN_TIME.getValue(), TimeUnit.SECONDS);
    }
}
