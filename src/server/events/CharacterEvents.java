package server.events;

import game.World;
import game.objects.inventory.GameItem;
import game.objects.Player;
import game.objects.dep.ClassData;
import jelly.Config;
import jelly.Loggin;
import jelly.Utils;
import models.Account;
import models.dao.DAOFactory;
import org.apache.mina.core.session.IoSession;
import server.game.GamePacketEnum;

public class CharacterEvents {

    public static void onCharacterSelected(IoSession session, String packet) {

        try {
            int id = Integer.parseInt(packet.substring(2));
            models.Character chr = DAOFactory.character().getById(id);

            if (chr == null) {
                GamePacketEnum.SELECT_CHARACTER_ERROR.send(session);
                return;
            }

            session.setAttribute("player", chr.getPlayer());
            chr.getPlayer().setSession(session);

            World.addOnline(chr.getPlayer());

            //génération du packet ASK
            StringBuilder param = new StringBuilder();

            param.append(chr.id).append("|").append(chr.name).append("|")
                    .append(chr.level).append("|").append(chr.classId).append("|")
                    .append(chr.sexe).append("|").append(chr.gfxid).append("|")
                    .append(Utils.implode("|", chr.getPlayer().getColors())).append("|");
            
            /*for(GameItem GI : chr.getPlayer().getInventory().getItems()){
                param.append(GI.toString()).append(';');
            }*/
            param.append(chr.getPlayer().getInventory().toString());

            GamePacketEnum.SELECT_CHARACTER_OK.send(session, param.toString());
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
        GamePacketEnum.CHAT_CHANEL_ADD.send(session, p.getChanels());
        GamePacketEnum.CHARACTER_RESTRICTION.send(session, p.restriction);
        ChatEvents.onSendErrorMessage(session, 89);
        MapEvents.onArrivedInGame(session);
    }
    
    public static void onStatsChange(IoSession session, Player p){
        GamePacketEnum.STATS_PACKET.send(session, p.getStatsPacket());    
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

        String[] arr_data = packet.split("\\|");

        try {
            if (DAOFactory.character().countByAccount(acc.id) >= Config.getInt("char_per_account", 5)) {
                GamePacketEnum.CREATE_CHARACTER_FULL.send(session);
                return;
            }
            if (DAOFactory.character().nameExists(arr_data[0])) {
                GamePacketEnum.NAME_ALEREADY_EXISTS.send(session);
                return;
            }

            models.Character c = new models.Character();
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
}
