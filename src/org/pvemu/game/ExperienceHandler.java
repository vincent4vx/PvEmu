package org.pvemu.game;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.models.Experience;
import org.pvemu.models.dao.DAOFactory;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class ExperienceHandler {
    final static private ExperienceHandler instance = new ExperienceHandler();
    
    final private Map<Short, Pair<Experience, Experience>> levels = new TreeMap<>();
    final public short LEVEL_MAX;

    public ExperienceHandler() {
        List<Experience> xps = DAOFactory.experience().getAll();
        Iterator<Experience> it = xps.iterator();
        Experience last = it.next();
        
        while(it.hasNext()){
            Experience next = it.next();
            levels.put(last.lvl, new Pair<>(last, next));
            last = next;
        }
        
        LEVEL_MAX = (short)(last.lvl - 1);
    }
    
    public long getPlayerMinXp(short level){
        Pair<Experience, Experience> pair = levels.get(level);
        
        if(pair == null)
            return -1;
        
        return pair.getFirst().player;
    }
    
    public long getPlayerMaxXp(short level){
        Pair<Experience, Experience> pair = levels.get(level);
        
        if(pair == null)
            return -1;
        
        return pair.getSecond().player;
    }
    
    public short getPlayerLevelByXp(long xp){
        Iterator<Entry<Short, Pair<Experience, Experience>>> it = levels.entrySet().iterator();
        Pair<Experience, Experience> pair;
        
        do{
            pair = it.next().getValue();
        }while(it.hasNext() && pair.getSecond().player < xp);
        
        return pair.getFirst().lvl;
    }
    
    public Pair<Experience, Experience> getLevel(short level){
        return levels.get(level);
    }
    
    static public ExperienceHandler instance(){
        return instance;
    }
}
