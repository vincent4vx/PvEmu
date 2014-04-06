/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.objects.player.classes;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class Feca extends ClassData{

    @Override
    public byte id() {
        return ClassesHandler.CLASS_FECA;
    }

    @Override
    public short getStartMap() {
        return 10300;
    }

    @Override
    public short getStartCell() {
        return 308;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7398;
    }

    @Override
    public short getAstrubStatueCell() {
        return 284;
    }
    
}
