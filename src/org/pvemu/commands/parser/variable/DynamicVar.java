package org.pvemu.commands.parser.variable;

import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface DynamicVar {
    /**
     * The var name
     * @return 
     */
    public String name();
    
    /**
     * The value
     * @param asker
     * @return 
     */
    public List<String> getValue(Asker asker);
    
    /**
     * Conditions to access to this var
     * @return 
     */
    public Filter condition();
}
