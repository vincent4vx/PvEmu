/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.output;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameSendersRegistry {
    
    private static InformativeMessageSender informativeMessage = null;

    /**
     * Get the value of informativeMessage
     *
     * @return the value of informativeMessage
     */
    public static InformativeMessageSender getInformativeMessage() {
        if(informativeMessage == null)
            informativeMessage = new InformativeMessageSender();
        return informativeMessage;
    }

    /**
     * Set the value of informativeMessage
     *
     * @param informativeMessage new value of informativeMessage
     */
    public static void setInformativeMessage(InformativeMessageSender informativeMessage) {
        GameSendersRegistry.informativeMessage = informativeMessage;
    }

}
