/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.realm.input;

import org.pvemu.network.InputPacketsHandler;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class RealmInputHandler extends InputPacketsHandler {
    static private RealmInputHandler instance = new RealmInputHandler();
    
    public RealmInputHandler() {
        registerPacket(new ServerListPacket());
        registerPacket(new SelectServerPacket());
    }
    
    static public RealmInputHandler instance(){
        return instance;
    }
}
