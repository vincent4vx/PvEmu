/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.generators;

import org.pvemu.jelly.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GameActionGenerator {
    public String generateGameAction(int id, short gameActionID, Object... args){
        return new StringBuilder()
                .append(id)
                .append(';').append(gameActionID)
                .append(';').append(Utils.join(args, ";"))
                .toString();
    }
    
    public String generateUnidentifiedGameAction(short gameActionID, Object... args){
        return new StringBuilder()
                .append(';').append(gameActionID)
                .append(';').append(Utils.join(args, ";"))
                .toString();
    }
    
    public String generateActionFinish(int tip, int id){
        return tip + "|" + id;
    }
}
