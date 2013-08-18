/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import game.objects.Player;
import game.objects.dep.ClassData;
import java.util.Random;
import jelly.Config;
import models.dao.DAOFactory;
import server.game.GamePacketEnum;

/**
 *
 * @author vincent
 */
public class Character implements jelly.database.Model {

    public int id;
    public String name;
    public int accountId;
    public int level = 1;
    public int color1;
    public int color2;
    public int color3;
    public int gfxid;
    public byte sexe;
    public byte classId;
    public int lastMap;
    public int lastCell;
    
    private Player _player = null;

    /**
     * Génération d'un nom aléatoire (béta, non totalement satisfaisant)
     *
     * @return
     */
    public static String generateName() {
        String name = "";
        Random rand = new Random();

        int size = rand.nextInt(6) + 4;

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

        name = prefix[rand.nextInt(prefix.length - 1)];

        while (name.length() < size) {
            if (cons.contains(name.substring(name.length() - 1))) {
                name += voy.charAt(rand.nextInt(voy.length()));
                if (name.length() > size) {
                    break;
                }
                name += s1[rand.nextInt(s1.length)];
            } else {
                name += cons.charAt(rand.nextInt(cons.length()));
                if (name.length() > size) {
                    break;
                }
                name += s2[rand.nextInt(s2.length)];
            }
        }

        //resize le nom à la bonne taille (de 4 à 10)
        name = name.substring(0, size);

        return name;
    }

    /**
     * Lancé lors du packet AA
     *
     * @param acc
     * @param data
     * @return
     */
    public static GamePacketEnum onCharacterAdd(Account acc, String data) {

        String[] arr_data = data.split("\\|");

        try {
            if (DAOFactory.character().countByAccount(acc.id) >= Config.getInt("char_per_account", 5)) {
                return GamePacketEnum.CREATE_CHARACTER_FULL;
            }
            if (DAOFactory.character().nameExists(arr_data[0])) {
                return GamePacketEnum.NAME_ALEREADY_EXISTS;
            }

            Character c = new Character();
            c.accountId = acc.id;
            c.name = arr_data[0];
            c.classId = Byte.parseByte(arr_data[1]);
            c.sexe = Byte.parseByte(arr_data[2]);
            c.color1 = Integer.parseInt(arr_data[3]);
            c.color2 = Integer.parseInt(arr_data[4]);
            c.color3 = Integer.parseInt(arr_data[5]);
            c.gfxid = ClassData.getCharacterGfxID(c);

            if (!DAOFactory.character().create(c)) {
                return GamePacketEnum.CREATE_CHARACTER_ERROR;
            }
            acc.addCharacter(c);
        } catch (ArrayIndexOutOfBoundsException e) {
            return GamePacketEnum.CREATE_CHARACTER_ERROR;
        }

        return GamePacketEnum.CREATE_CHARACTER_OK;
    }

    /**
     * retourne les données du personnage pour packet ALK
     * @return 
     */
    public String getForALK() {
        StringBuilder perso = new StringBuilder();
        perso.append("|");
        perso.append(id).append(";");
        perso.append(name).append(";");
        perso.append(level).append(";");
        perso.append(gfxid).append(";");
        perso.append((color1 != -1 ? Integer.toHexString(color1) : "-1")).append(";");
        perso.append((color2 != -1 ? Integer.toHexString(color2) : "-1")).append(";");
        perso.append((color3 != -1 ? Integer.toHexString(color3) : "-1")).append(";");
        perso.append("").append(";");
        perso.append(0).append(";");
        perso.append("1;");//ServerID
        perso.append(";");//DeathCount	this.deathCount;
        perso.append(";");//LevelMax
        return perso.toString();
    }
    
    public Player getPlayer(){
        if(_player == null){
            _player = new Player(this);
        }
        return _player;
    }

    public void clear() {
        id = 0;
    }

    public int getPk() {
        return id;
    }
}
