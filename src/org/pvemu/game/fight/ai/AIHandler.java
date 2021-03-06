package org.pvemu.game.fight.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.fightertype.AIFighter;
import org.pvemu.common.Loggin;

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
        registerAI(new PoutchAI());
        registerAI(DEFAULT_AI);
        registerAI(new RunawayAI());
    }
    
    /**
     * Register a new AI type
     * @param type AI to register
     */
    public void registerAI(AIType type){
        aiTypes.put(type.typeID(), type);
    }
    
    /**
     * Start the AI thread
     * @param typeID the AI type
     * @param fight the current fight
     * @param fighter the fighter
     */
    public void runAI(byte typeID, final Fight fight, final AIFighter fighter){
        final AIType type = aiTypes.containsKey(typeID) ? aiTypes.get(typeID) : DEFAULT_AI;
        
        fighter.setAITask(service.submit(new Runnable() {
            @Override
            public void run() {
                Loggin.debug("Start IA %d for fighter %s", type.typeID(), fighter);
                while(fighter.isAlive() && fighter.canPlay()){
                    if(!type.actions(fight, fighter)){
                        break;
                    }
                }
                
                if(fighter.canPlay())
                    fight.nextFighter();
            }
        }));
    }

    public static AIHandler instance() {
        return instance;
    }
    
}
