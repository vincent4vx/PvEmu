package org.pvemu.commands.parser;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ListParser implements Parser{
    final public char LIST_START        = '[';
    final public char LIST_END          = ']';
    final public char SPACE             = ' ';
    final public Character[] SEPARATORS = new Character[]{',', ';'};

    @Override
    public char start() {
        return LIST_START;
    }

    @Override
    public Pair<Integer, List<String>> parse(String command, int start, Asker asker) throws ParserError {
        int i = start + 1;
        List<String> list = new ArrayList<>();
        StringBuilder word = new StringBuilder(16);
        
        for(;i < command.length() && command.charAt(i) != LIST_END; ++i){
            if(command.charAt(i) == SPACE)
                continue;
            
            if(Utils.contains(SEPARATORS, command.charAt(i))){
                list.add(word.toString());
                word = new StringBuilder();
            }else{
                word.append(command.charAt(i));
            }
        }
        
        list.add(word.toString());
        
        return new Pair<>(i, list);
    }
    
}
