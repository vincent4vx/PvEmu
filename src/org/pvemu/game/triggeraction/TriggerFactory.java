/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.triggeraction;

import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.NpcResponseAction;


/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class TriggerFactory {
    static public Trigger newTrigger(org.pvemu.models.Trigger trigger){
        return new Trigger(
                trigger.actionID,
                Utils.split(trigger.actionArgs, ",")
        );
    }
    
    static public Trigger newResponseAction(NpcResponseAction nra){
        return new Trigger(
                nra.action_id,
                nra.args
        );
    }
}
