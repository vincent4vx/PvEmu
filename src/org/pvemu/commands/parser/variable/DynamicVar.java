package org.pvemu.commands.parser.variable;

import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.jelly.filters.Filter;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public interface DynamicVar {
    public String name();
    public List<String> getValue(Asker asker);
    public Filter condition();
}
