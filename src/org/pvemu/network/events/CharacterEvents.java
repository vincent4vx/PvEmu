package org.pvemu.network.events;

import org.pvemu.game.World;
import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.dep.ClassData;
import org.pvemu.jelly.Config;
import org.pvemu.jelly.Constants;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.Utils;
import org.pvemu.models.Account;
import org.pvemu.models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.GameServer;
import org.pvemu.network.generators.GeneratorsFactory;
import org.pvemu.network.generators.PlayerGenerator;

public class CharacterEvents {

    public static void onCharacterSelected(IoSession session, String packet) {

        try {
            Account acc = (Account) session.getAttribute("account");

            if (acc == null) {
                return;
            }

            int id, svr = 0;
            
            try{
                String[] data = Utils.split(packet, "|");//packet.split("\\|");
                
                id = Integer.parseInt(data[0]);
                
                if(data.length == 2){
                    svr = Integer.parseInt(data[1]);
                }
            }catch(Exception e){
                GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
                return;
            }
            
            org.pvemu.models.Character chr = acc.getCharacter(id);

            if (chr == null) {
                GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
                return;
            }

            Player p = chr.getPlayer();

            if (Constants.DOFUS_VER_ID >= 1100) { //pour dofus "récents" : connecte directement le pesonnage
                session.setAttribute("player", p);
                chr.getPlayer().setSession(session);

                World.instance().addOnline(p);

                //génération du packet ASK
                StringBuilder param = new StringBuilder();

                param.append('|').append(chr.id).append("|").append(chr.name).append("|")
                        .append(chr.level).append("|").append(chr.classId).append("|")
                        .append(chr.sexe).append("|").append(chr.gfxid).append("|")
                        .append(Utils.implode("|", p.getColors())).append("|");

                param.append(p.getInventory().toString());

                GamePacketEnum.SELECT_CHARACTER_OK.send(session, param.toString());
            } else { //vielles version (cf: 1.09.1), envoit les ids du game
                String ticket = acc.setWaiting();
                GamePacketEnum.SELECT_CHARACTER_OK.send(session, GameServer.CRYPT_IP + ticket);
                acc.setWaitingCharacter(p);
            }
        } catch (Exception e) {
            Loggin.error("Impossible de sélectionner le personnage", e);
            GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
        }
    }

    public static void onGameCreate(IoSession session) {
        Player p = getPlayer(session);

        if (p == null) {
            return;
        }

        GamePacketEnum.GAME_CREATE_OK.send(session, p.getName());
        onStatsChange(session, p);
        ObjectEvents.onWeightChange(session, p);
        if(Constants.DOFUS_VER_ID >= 1100){
            GamePacketEnum.CHAT_CHANEL_ADD.send(session, p.getChanels());
        }
        GamePacketEnum.CHARACTER_RESTRICTION.send(session, p.restriction);
        ChatEvents.onSendErrorMessage(session, 89);
        MapEvents.onArrivedInGame(session);
        
        if(Constants.DOFUS_VER_ID < 1100){
            BasicEvents.onDate(session);
            
            if(p.getAccount().level > 0){
                BasicEvents.onPrompt(session);
            }
        }
    }

    public static void onStatsChange(IoSession session, Player p) {
        GamePacketEnum.STATS_PACKET.send(session, GeneratorsFactory.getPlayer().generateAs(p));
    }

    private static Player getPlayer(IoSession session) {
        Player p = (Player) session.getAttribute("player");

        if (p == null) {
            session.close(false);
        }

        return p;
    }

    public static void onNameGenerator(IoSession session) {
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

    public static void onCharacterAdd(IoSession session, String packet) {

        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            return;
        }

        String[] arr_data = Utils.split(packet, "|");//packet.split("\\|");

        try {
            if (DAOFactory.character().countByAccount(acc.id) >= Config.getInt("char_per_account", 5)) {
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
            c.gfxid = ClassData.getCharacterGfxID(c);
            c.startMap = c.lastMap = ClassData.getStartMap(c.classId)[0];
            c.startCell = c.lastCell = ClassData.getStartMap(c.classId)[1];

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
        GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
        GamePacketEnum.TUTORIAL_BEGIN.send(session);
    }

    public static void onDelete(IoSession session, String packet) {
        Account acc = (Account) session.getAttribute("account");

        if (acc == null) {
            return;
        }

        String response = "";
        int id = 0;

        try {
            String[] data = Utils.split(packet, "|");//packet.split("\\|");
            id = Integer.parseInt(data[0]);
            if (data.length > 1) {
                response = data[1];
            }
        } catch (Exception e) {
            GamePacketEnum.CHARACTER_DELETE_ERROR.send(session);
            return;
        }

        org.pvemu.models.Character chr = acc.getCharacter(id);

        if (chr == null) {
            GamePacketEnum.CHARACTER_DELETE_ERROR.send(session);
            return;
        }

        if (chr.level >= 20 && !response.equalsIgnoreCase(acc.response)) {
            GamePacketEnum.CHARACTER_DELETE_ERROR.send(session);
            return;
        }

        acc.deleteCharacter(id);
        GamePacketEnum.CHARCTERS_LIST.send(session, acc.getCharactersList());
    }
}
