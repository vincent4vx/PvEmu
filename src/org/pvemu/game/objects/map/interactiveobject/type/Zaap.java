/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map.interactiveobject.type;

import org.pvemu.game.objects.map.interactiveobject.actions.SaveIOAction;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Zaap extends InteractiveObjectType{

    public Zaap() {
        registerAction(new SaveIOAction());
    }

    @Override
    public int[] objIDs() {
        return new int[]{7000, 7026, 7029, 4297};
    }
    
}
