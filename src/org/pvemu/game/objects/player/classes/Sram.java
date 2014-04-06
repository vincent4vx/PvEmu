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
public class Sram extends ClassData{

    @Override
    public byte id() {
        return ClassesHandler.CLASS_SRAM;
    }

    @Override
    public short getStartMap() {
        return 10285;
    }

    @Override
    public short getStartCell() {
        return 234;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7392;
    }

    @Override
    public short getAstrubStatueCell() {
        return 282;
    }
    
}
