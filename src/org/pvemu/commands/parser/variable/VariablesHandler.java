package org.pvemu.commands.parser.variable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.pvemu.commands.askers.Asker;
import org.pvemu.commands.parser.CommandParser;
import org.pvemu.commands.parser.Parser;
import org.pvemu.commands.parser.ParserError;
import org.pvemu.jelly.filters.FilterFactory;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class VariablesHandler implements Parser{
    final static private VariablesHandler instance = new VariablesHandler();
    
    final static public char VAR_START = '$';
    
    final public Map<String, List<String>> variables = new HashMap<>();
    final public Map<String, DynamicVar> dynamicVars = new HashMap<>();

    private VariablesHandler() {
        registerDynamicVar(new MeVar());
        registerDynamicVar(new MapVar());
        registerDynamicVar(new AllVar());
    }
    
    final public void registerDynamicVar(DynamicVar var){
        dynamicVars.put(var.name().toLowerCase(), var);
    }
    
    private List<String> getValue(String var){
        var = var.toLowerCase();
        if(!variables.containsKey(var)){
            return null;
        }
        
        return variables.get(var);
    }
    
    private List<String> getDynamicValue(String var, Asker asker){
        var = var.toLowerCase();
        if(!dynamicVars.containsKey(var))
            return null;
        
        DynamicVar dv = dynamicVars.get(var);
        
        if(!asker.corresponds(dv.condition()))
            return null;
        
        return dv.getValue(asker);
    }
    
    private List<String> filterValue(List<String> value, String pattern){
        List<String> list = new ArrayList<>(value.size());
        pattern = pattern.replace("*", ".*").replace("?", ".?").toLowerCase();
        
        for(String str : value){
            if(str.toLowerCase().matches(pattern)){
                list.add(str);
            }
        }
        
        return list;
    }

    @Override
    public char start() {
        return VAR_START;
    }

    @Override
    public Pair<Integer, List<String>> parse(String command, int start, Asker asker) throws ParserError {
        if(!asker.corresponds(FilterFactory.moderatorAskerFilter()))
            throw new ParserError(command, "Only moderators can access to vars", start);
        
        int i = ++start;
        
        while(i < command.length() && command.charAt(i) != CommandParser.END_OF_WORD){
            ++i;
        }
        
        String[] tmp = Utils.split(command.substring(start, i), ":", 2);
        
        String name = tmp[0].toLowerCase();
        
        List<String> value = getDynamicValue(name, asker);
        
        if(value == null){
            value = getValue(name);
        }
        
        if(value == null)
            throw new ParserError(command, "Variable " + name + " don't exists", start);
        
        if(tmp.length == 2)
            value = filterValue(value, tmp[1]);
        
        return new Pair<>(i, value);
    }
    
    public List<String> eval(String code, Asker asker) throws ParserError{
        return parse(code, 0, asker).getSecond();
    }
    
     static public VariablesHandler instance(){
        return instance;
    }
}
