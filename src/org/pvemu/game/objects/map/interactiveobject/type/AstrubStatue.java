/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.map.interactiveobject.type;

import org.pvemu.game.objects.map.interactiveobject.actions.GotoIncarnam;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AstrubStatue extends InteractiveObjectType{

    public AstrubStatue() {
        registerAction(new GotoIncarnam());
    }

    @Override
    public int[] objIDs() {
        return new int[]{1845, 1853, 1854, 1855, 1856, 1857, 1858, 1859, 1860, 1861, 1862, 2319};
    }
    
}
