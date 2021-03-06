/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.gameaction;


/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionData<T extends ActionPerformer> {
    final private T performer;
    final private short gameActionID;
    final private String[] arguments;

    public GameActionData(T performer, short gameActionID, String[] arguments) {
        this.performer = performer;
        this.gameActionID = gameActionID;
        this.arguments = arguments;
    }
    

    /**
     * Get the value of arguments
     *
     * @return the value of arguments
     */
    public String[] getArguments() {
        return arguments;
    }
    
    /**
     * Get a value of an argument
     * @param index the index of the argument
     * @return the argument if exists, or null
     */
    public String getArgument(int index){
        return arguments.length > index ? arguments[index] : null;
    }
    
    public void setArgument(int index, String value){
        if(arguments.length > index)
            arguments[index] = value;
    }


    /**
     * Get the value of gameActionID
     *
     * @return the value of gameActionID
     */
    public short getGameActionID() {
        return gameActionID;
    }

    /**
     * Get the action performer
     * @return 
     */
    public T getPerformer() {
        return performer;
    }

}
