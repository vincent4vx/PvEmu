/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.chat;

import java.util.HashMap;
import org.pvemu.game.objects.Player;
import org.pvemu.jelly.Loggin;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class ChatHandler {
    final static private ChatHandler instance = new ChatHandler();
    final private HashMap<String, ChatChanel> chanels = new HashMap<>();
    
    private ChatHandler(){
        registerChanel(new MapChanel());
        registerChanel(new RecruitmentChanel());
    }
    
    public void parse(String[] args, Player player){
        ChatChanel chanel = chanels.get(args[0]);
        
        if(chanel == null){
            Loggin.debug("Cannal de discution '%s' non trouvé !", args[0]);
            return;
        }
            
        if(!player.corresponds(chanel.conditions())){
            return;
        }
        
        chanel.post(args[1], player);
    }
    
    /**
     * Add a new chanel to chat system
     * @param chanel the chanel to add
     */
    public void registerChanel(ChatChanel chanel){
        chanels.put(chanel.id(), chanel);
    }
    
    static public ChatHandler instance(){
        return instance;
    }
}
