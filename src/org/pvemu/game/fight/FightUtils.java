/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class FightUtils {
    final static int TIMERS_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    final private static ScheduledExecutorService timers = Executors.newScheduledThreadPool(TIMERS_POOL_SIZE);
    
    static public void startCountdownTimer(final Fight fight){
        if(fight.canCancel())
            return;
        
        fight.setStartCountdownTimer(timers.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        if(fight.decrementCountdown() <= 0)
                            fight.startFight();
                    }
                }, 
                1, 1, TimeUnit.SECONDS
        ));
    }
    
    static public ScheduledFuture scheduleTask(Runnable task, int time){
        return timers.schedule(task, time, TimeUnit.SECONDS);
    }
    
    static public ScheduledFuture turnTimer(final Fight fight){
        return scheduleTask(new Runnable() {
            @Override
            public void run() {
                fight.nextFighter();
            }
        }, Constants.TURN_TIME);
    }
}
