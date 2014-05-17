/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.triggeraction;

import org.pvemu.common.utils.Utils;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class Trigger {
    final private short actionID;
    final private String[] args;

    Trigger(short actionID, String[] args) {
        this.actionID = actionID;
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public short getActionID() {
        return actionID;
    }

    @Override
    public String toString() {
        return "[id:" + actionID + " args:" + Utils.join(args, ",") + "]";
    }
    
}
