/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map.interactiveobject;

import org.pvemu.game.objects.map.interactiveobject.type.InteractiveObjectTypeHandler;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class InteractiveObjectFactory {
    static public InteractiveObject getInteractiveObject(int objID, short cell, short map){
        return new InteractiveObject(
                InteractiveObjectTypeHandler.instance().getIOType(objID), 
                objID, 
                cell, 
                map
        );
    }
}
