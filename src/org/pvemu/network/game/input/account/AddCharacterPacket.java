/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.network.game.input.account;

import org.apache.mina.core.session.IoSession;
import org.pvemu.game.ExperienceHandler;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.player.classes.ClassData;
import org.pvemu.game.objects.player.classes.ClassesHandler;
//import org.pvemu.game.objects.player.OldClassData;
import org.pvemu.common.Config;
import org.pvemu.common.utils.Utils;
import org.pvemu.models.Account;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.InputPacket;
import org.pvemu.network.SessionAttributes;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class AddCharacterPacket implements InputPacket {

    @Override
    public String id() {
        return "AA";
    }

    @Override
    public void perform(String extra, IoSession session) {

        Account acc = SessionAttributes.ACCOUNT.getValue(session);

        if (acc == null) {
            return;
        }

        String[] arr_data = Utils.split(extra, "|");

        try {
            if (DAOFactory.character().countByAccount(acc.id) >= Config.CHAR_PER_ACCOUNT.getValue()) {
                GamePacketEnum.CREATE_CHARACTER_FULL.send(session);
                return;
            }
            if (DAOFactory.character().nameExists(arr_data[0])) {
                GamePacketEnum.NAME_ALEREADY_EXISTS.send(session);
                return;
            }

            org.pvemu.models.Character c = new org.pvemu.models.Character();
            c.accountId = acc.id;
            c.name = arr_data[0];
            c.classId = Byte.parseByte(arr_data[1]);
            c.sexe = Byte.parseByte(arr_data[2]);
            c.color1 = Integer.parseInt(arr_data[3]);
            c.color2 = Integer.parseInt(arr_data[4]);
            c.color3 = Integer.parseInt(arr_data[5]);
            ClassData classData = ClassesHandler.instance().getClass(c.classId);
            c.gfxid = classData.getGfxID(c.sexe);
            c.startMap = c.lastMap = classData.getStartMap();
            c.startCell = c.lastCell = classData.getStartCell();
            c.currentVita = classData.getClassStats(1).get(Stats.Element.VITA);
            c.level = Config.START_LEVEL.getValue().shortValue();
            c.experience = ExperienceHandler.instance().getPlayerMinXp(c.level);

            if (!DAOFactory.character().create(c)) {
                GamePacketEnum.CREATE_CHARACTER_ERROR.send(session);
                return;
            }
            acc.addCharacter(c);
        } catch (ArrayIndexOutOfBoundsException e) {
            GamePacketEnum.CREATE_CHARACTER_ERROR.send(session);
            return;
        }

        GamePacketEnum.CREATE_CHARACTER_OK.send(session);
        GameSendersRegistry.getAccount().charactersList(session, acc);
        GamePacketEnum.TUTORIAL_BEGIN.send(session);

    }
    
}
