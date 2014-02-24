/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.InputPacket;
import org.pvemu.network.game.GamePacketEnum;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class DatePacket implements InputPacket {

    @Override
    public String id() {
        return "BD";
    }

    @Override
    public void perform(String extra, IoSession session) {
        Date actDate = new Date();
        StringBuilder p = new StringBuilder();


        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        p.append(Integer.parseInt(dateFormat.format(actDate)) - 1370).append("|");

        dateFormat = new SimpleDateFormat("MM");
        String mois = (Integer.parseInt(dateFormat.format(actDate)) - 1) + "";

        if (mois.length() < 2) {
            p.append(0);
        }

        p.append(mois).append("|");

        dateFormat = new SimpleDateFormat("dd");
        String jour = Integer.parseInt(dateFormat.format(actDate)) + "";
        if (jour.length() < 2) {
            p.append(0);
        }

        p.append(jour).append("|");

        GamePacketEnum.BASIC_DATE.send(session, p.toString());
        GamePacketEnum.BASIC_TIME.send(session, String.valueOf(actDate.getTime() + 3600000));
        
    }
    
}
