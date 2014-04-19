/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.account;

import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.InputPacket;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class GenerateNamePacket implements InputPacket {

    @Override
    public String id() {
        return "AP";
    }

    @Override
    public void perform(String extra, IoSession session) {
        StringBuilder name = new StringBuilder();

        int size = Utils.rand(6, 12);

        String[] prefix = {
            "Rex", "Xer", "Oy", "Mel", "Weir", "Kor", "Swi", "Tco", "Ret",
            "Kit", "Rom", "Bir", "Nor", "Your", "Yor", "Kra", "Ken", "Tar",
            "Heit", "Thre", "Cys", "Jil", "Fire", "As", "Flow", "Rhi", "Luc",
            "Hug", "Aim", "Bug", "Cris", "Del", "Ety", "Fal", "Gli", "Inn",
            "Jet", "Lin", "Mop", "Nai", "Otis", "Psy", "Quel", "Rav", "Stri",
            "Try", "Ug", "Vis", "Wes", "Xult", "Yoh", "Zyr"
        };

        String cons = "zrtpqsdfghjklmwxcvbn";
        String voy = "aeiouy";

        String[] s1 = {
            "si", "ma", "li", "wei", "po", "se", "bo", "wo", "ka", "moa", "la",
            "bro", "fu", "sur", "you", "jo", "plo", "gor", "stu", "wel", "lis",
            "cu"
        };

        String[] s2 = {
            "elle", "el", "il", "al", "en", "ut", "olin", "ed", "er", "ije",
            "era", "owei", "edi", "arc", "up", "ufo", "ier", "ead", "ing", "ana"
        };

        name.append(Utils.array_rand(prefix));

        while (name.length() < size) {
            if (cons.contains(name.substring(name.length() - 1))) {
                name.append(Utils.char_rand(voy));
                if (name.length() > size) {
                    break;
                }
                name.append(Utils.array_rand(s1));
            } else {
                name.append(Utils.char_rand(cons));
                if (name.length() > size) {
                    break;
                }
                name.append(Utils.array_rand(s2));
            }
        }

        GamePacketEnum.CHARACTER_GENERATOR_NAME.send(session, name.substring(0, size));
      
    }
    
}
