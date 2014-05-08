package org.pvemu.commands.argument;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ArgumentList {
    final private List<List<String>> arguments = new ArrayList<>();
    private boolean locked = false;

    public void lock() {
        this.locked = true;
    }
    
    public void addArgument(String value) throws LockedArgumentList{
        List<String> list = new ArrayList<>();
        list.add(value);
        addList(list);
    }
    
    public void addList(List<String> value) throws LockedArgumentList{
        if(locked)
            throw new LockedArgumentList();
        
        arguments.add(value);
    }
    
    public String getCommand() throws CommandArgumentException{
        return getArgument(0);
    }
    
    public String getArgument(int number) throws CommandArgumentException{
        if(number >= arguments.size())
            throw new RequiredArgumentException(number);
        
        List<String> list = getList(number);
        
        if(list.isEmpty())
            throw new UnauthorizedEmptyList(number);
        
        return list.get(0);
    }
    
    public int getInteger(int number) throws CommandArgumentException{
        try{
            return Integer.parseInt(getArgument(number));
        }catch(NumberFormatException e){
            throw new CommandArgumentException(number, "not a valid number");
        }
    }
    
    public int getInteger(int number, int defaultValue){
        try{
            return getInteger(number);
        }catch(Exception e){
            return defaultValue;
        }
    }
    
    public short getShort(int number) throws CommandArgumentException{
        try{
            return Short.parseShort(getArgument(number));
        }catch(NumberFormatException e){
            throw new CommandArgumentException(number, "not a valid number");
        }
    }
    
    public short getShort(int number, short defaultValue){
        try{
            return getShort(number);
        }catch(Exception e){
            return defaultValue;
        }
    }
    
    public boolean getBoolean(int number) throws CommandArgumentException{
        return Boolean.parseBoolean(getArgument(number));
    }
    
    public String getArgument(int number, String defaultValue){
        try{
            return getArgument(number);
        }catch(CommandArgumentException e){
            return defaultValue;
        }
    }
    
    public List<String> getList(int number) throws RequiredArgumentException{
        if(number >= arguments.size())
            throw new RequiredArgumentException(number);
        
        return arguments.get(number);
    }
    
    public List<String> getList(int number, List<String> defaultValue){
        try{
            return getList(number);
        }catch(RequiredArgumentException e){
            return defaultValue;
        }
    }
    
    public int size(){
        return arguments.size();
    }
}
