/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GeneratorsRegistry {
    private static PlayerGenerator player = null;
    private static InformativeMessageGenerator informativeMessage = null;
    private static ObjectGenerator object = null;
    private static AccountGenerator account = null;

    /**
     * Get the value of account
     *
     * @return the value of account
     */
    public static AccountGenerator getAccount() {
        if(account == null)
            account = new AccountGenerator();
        return account;
    }

    /**
     * Set the value of account
     *
     * @param account new value of account
     */
    public static void setAccount(AccountGenerator account) {
        GeneratorsRegistry.account = account;
    }


    /**
     * Get the value of object
     *
     * @return the value of object
     */
    public static ObjectGenerator getObject() {
        if(object == null)
            object = new ObjectGenerator();
        return object;
    }

    /**
     * Set the value of object
     *
     * @param object new value of object
     */
    public static void setObject(ObjectGenerator object) {
        GeneratorsRegistry.object = object;
    }


    /**
     * Get the value of informativeMessage
     *
     * @return the value of informativeMessage
     */
    public static InformativeMessageGenerator getInformativeMessage() {
        return informativeMessage;
    }

    /**
     * Set the value of informativeMessage
     *
     * @param informativeMessage new value of informativeMessage
     */
    public static void setInformativeMessage(InformativeMessageGenerator informativeMessage) {
        GeneratorsRegistry.informativeMessage = informativeMessage;
    }

    
    public static PlayerGenerator getPlayer(){
        if(player == null)
            player = new PlayerGenerator();
        
        return player;
    }
    
    public static void setPlayer(PlayerGenerator newPlayer){
        player = newPlayer;
    }
    
    
}
