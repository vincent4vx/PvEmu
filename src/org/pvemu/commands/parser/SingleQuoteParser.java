package org.pvemu.commands.parser;

import java.util.ArrayList;
import java.util.List;
import org.pvemu.commands.askers.Asker;
import org.pvemu.common.utils.Pair;

/**
 * parse single quotes
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class SingleQuoteParser implements Parser{
    final static public char SINGLE_QUOTE = '\'';

    @Override
    public char start() {
        return SINGLE_QUOTE;
    }

    @Override
    public Pair<Integer, List<String>> parse(String command, int start, Asker asker) throws ParserError{
        StringBuilder text = new StringBuilder();
        int i = start + 1;
        
        while(i < command.length() && command.charAt(i) != SINGLE_QUOTE){
            char c = command.charAt(i);
            
            if(c == CommandParser.ESCAPE){
                if(i + 1 >= command.length())
                    throw new UnexpectedCharacter(command, c, i);
                c = command.charAt(++i);
            }
            
            text.append(c);
            ++i;
        }
        
        List<String> list = new ArrayList<>();
        list.add(text.toString());

        return new Pair<>(i, list);
    }
    
}
