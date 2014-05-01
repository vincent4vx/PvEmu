/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.fight.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class AIHandler {
    final static private AIHandler instance = new AIHandler();
    
    final private Map<Byte, AIType> aiTypes = new HashMap<>();
    final private ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    final private AIType DEFAULT_AI = new NormalAI();

    private AIHandler() {
    }
    
    public void registerAI(AIType type){
        aiTypes.put(type.typeID(), type);
    }
    
    public void runAI(byte typeID, final Fight fight, final Fighter fighter){
        final AIType type = aiTypes.containsKey(typeID) ? aiTypes.get(typeID) : DEFAULT_AI;
        
        service.submit(new Runnable() {
            @Override
            public void run() {
                while(fighter.isAlive() && fighter.canPlay()){
                    if(!type.actions(fight, fighter)){
                        break;
                    }
                }
                fight.nextFighter();
            }
        });
    }

    public static AIHandler instance() {
        return instance;
    }
    
}
