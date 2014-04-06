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
public class Iop extends ClassData {

    @Override
    public byte id() {
        return ClassesHandler.CLASS_IOP;
    }

    @Override
    public short getStartMap() {
        return 10294;
    }

    @Override
    public short getStartCell() {
        return 278;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7427;
    }

    @Override
    public short getAstrubStatueCell() {
        return 267;
    }
    
}
