package org.pvemu.commands.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.commands.argument.ArgumentList;
import org.pvemu.commands.argument.LockedArgumentList;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.parser.variable.VariablesHandler;
import org.pvemu.jelly.utils.Pair;

/**
 * main parser
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class CommandParser {
    final static public char END_OF_WORD = ' ';
    final static public char ESCAPE       = '\\';
    
    final private Map<Character, Parser> parsers = new HashMap<>();

    public CommandParser() {
        registerParser(new SingleQuoteParser());
        registerParser(new ListParser());
        registerParser(VariablesHandler.instance());
    }
    
    /**
     * register a new sub-parser
     * @param parser 
     */
    final public void registerParser(Parser parser){
        parsers.put(parser.start(), parser);
    }
    
    /**
     * Get the argument list 
     * @param command the command line to parse
     * @param asker the command asker
     * @return the args list
     * @throws ParserError
     * @throws LockedArgumentList shouldn't be thrown
     */
    public ArgumentList parseCommand(String command, Asker asker) throws ParserError, LockedArgumentList{ //LockedArgumentList shoud not be thrown !
        ArgumentList args = new ArgumentList();
        StringBuilder word = new StringBuilder(16);
        
        for(int i = 0; i < command.length(); ++i){
            char c = command.charAt(i);
            
            if(c == ESCAPE){
                if(i + 1 >= command.length())
                    throw new UnexpectedCharacter(command, c, i);
                
                c = command.charAt(++i);
            }else if(c == END_OF_WORD){
                String w = word.toString();
                
                if(w.isEmpty())
                    continue;
                
                args.addArgument(w);
                word = new StringBuilder(16);
                continue;
            }else if(parsers.containsKey(c)){
                if(i + 1 >= command.length())
                    throw new UnexpectedCharacter(command, c, i);
                
                Pair<Integer, List<String>> p = parsers.get(c).parse(command, i, asker);
                
                i = p.getFirst();
                args.addList(p.getSecond());
                continue;
            }
            
            word.append(c);
        }
        
        String w = word.toString();
        
        if(!w.isEmpty())
            args.addArgument(w);
        
        args.lock();
        
        return args;
    }
}
