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
public class Sacrieur extends ClassData{

    public Sacrieur() {
        addSpell(1, 431);
        addSpell(1, 432);
        addSpell(1, 434);
        addSpell(3, 444);
        addSpell(6, 449);
        addSpell(9, 436);
        addSpell(13, 437);
        addSpell(17, 439);
        addSpell(21, 433);
        addSpell(26, 443);
        addSpell(31, 440);
        addSpell(36, 442);
        addSpell(42, 441);
        addSpell(48, 445);
        addSpell(54, 438);
        addSpell(60, 446);
        addSpell(70, 447);
        addSpell(80, 448);
        addSpell(90, 435);
        addSpell(100, 450);
        addSpell(200, 1911);
    }

    @Override
    public byte id() {
        return ClassesHandler.CLASS_SACRIEUR;
    }

    @Override
    public short getStartMap() {
        return 10296;
    }

    @Override
    public short getStartCell() {
        return 244;
    }

    @Override
    public short getAstrubStatueMap() {
        return 7336;
    }

    @Override
    public short getAstrubStatueCell() {
        return 198;
    }
    
}
