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
public class Sadida extends ClassData{

    @Override
    public byte id() {
        return ClassesHandler.CLASS_SADIDA;
    }

    @Override
    public short getStartMap() {
        return 10279;
    }

    @Override
    public short getStartCell() {
        return 270;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7395;
    }

    @Override
    public short getAstrubStatueCell() {
        return 357;
    }
    
}
