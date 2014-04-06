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
public class Pandawa extends ClassData{

    @Override
    public byte id() {
        return ClassesHandler.CLASS_PANDAWA;
    }

    @Override
    public short getStartMap() {
        return 10289;
    }

    @Override
    public short getStartCell() {
        return 263;
    }

    @Override
    public short getAstrubStatueMap() {
        return 8035;
    }

    @Override
    public short getAstrubStatueCell() {
        return 340;
    }
    
}
