package com.oldofus.game.objects.dep;

import com.oldofus.game.objects.Player;
import java.util.TreeMap;

public class ClassData {

    //stats
    public static final short BASE_PA = 6;
    public static final short BONUS_PA_LVL100 = 1;
    public static final short BASE_PM = 3;
    public static final short BASE_PROS = 100;
    public static final short PROS_BONUS_ENU = 20;
    public static final short BASE_PODS = 1000;
    public static final short BASE_INVOC = 1;
    public static final short BASE_INIT = 100;
    public static final short BASE_VITA = 50;
    public static final short VITA_PER_LVL = 5;
    //classes
    public static final byte CLASS_FECA = 1;
    public static final byte CLASS_OSAMODAS = 2;
    public static final byte CLASS_ENUTROF = 3;
    public static final byte CLASS_SRAM = 4;
    public static final byte CLASS_XELOR = 5;
    public static final byte CLASS_ECAFLIP = 6;
    public static final byte CLASS_ENIRIPSA = 7;
    public static final byte CLASS_IOP = 8;
    public static final byte CLASS_CRA = 9;
    public static final byte CLASS_SADIDA = 10;
    public static final byte CLASS_SACRIEUR = 11;
    public static final byte CLASS_PANDAWA = 12;
    public static final byte SEX_MALE = 0;
    public static final byte SEX_FEMALE = 1;

    /**
     * Retourne le GfxID suivant la classe et le sexe
     *
     * @param classID
     * @param sexe
     * @return
     */
    public static Short getCharacterGfxID(byte classID, byte sexe) {
        return (short)(classID * 10 + sexe);
    }

    /**
     * Retourne le GfxID (de base) du personnage
     *
     * @param c
     * @return
     */
    public static Short getCharacterGfxID(com.oldofus.models.Character c) {
        return (short)(c.classId * 10 + c.sexe);
    }

    /**
     * Retourne la map de départ en fonctionne de la classe
     *
     * @param classID
     * @return
     */
    public static short[] getStartMap(byte classID) {
        short mapID = 10298;
        short cellID = 314;
        switch (classID) {
            case CLASS_FECA:
                mapID = 10300;
                cellID = 308;
                break;
            case CLASS_OSAMODAS:
                mapID = 10284;
                cellID = 372;
                break;
            case CLASS_ENUTROF:
                mapID = 10299;
                cellID = 286;
                break;
            case CLASS_SRAM:
                mapID = 10285;
                cellID = 234;
                break;
            case CLASS_XELOR:
                mapID = 10298;
                cellID = 300;
                break;
            case CLASS_ECAFLIP:
                mapID = 10276;
                cellID = 250;
                break;
            case CLASS_ENIRIPSA:
                mapID = 10283;
                cellID = 270;
                break;
            case CLASS_IOP:
                mapID = 10294;
                cellID = 278;
                break;
            case CLASS_CRA:
                mapID = 10292;
                cellID = 300;
                break;
            case CLASS_SADIDA:
                mapID = 10279;
                cellID = 270;
                break;
            case CLASS_SACRIEUR:
                mapID = 10296;
                cellID = 244;
                break;
            case CLASS_PANDAWA:
                mapID = 10289;
                cellID = 263;
                break;
        }

        return new short[]{mapID, cellID};
    }

    /**
     * Retourne la map de la statue à astrub
     *
     * @param classID
     * @return
     */
    public static short[] getStatuesPos(byte classID) {
        short mapID, cellID;
        switch (classID) {
            case CLASS_FECA:
                mapID = 7398;
                cellID = 284;
                break;
            case CLASS_OSAMODAS:
                mapID = 7545;
                cellID = 297;
                break;
            case CLASS_ENUTROF:
                mapID = 7442;
                cellID = 255;
                break;
            case CLASS_SRAM:
                mapID = 7392;
                cellID = 282;
                break;
            case CLASS_XELOR:
                mapID = 7332;
                cellID = 312;
                break;
            case CLASS_ECAFLIP:
                mapID = 7446;
                cellID = 284;
                break;
            case CLASS_ENIRIPSA:
                mapID = 7361;
                cellID = 192;
                break;
            case CLASS_IOP:
                mapID = 7427;
                cellID = 267;
                break;
            case CLASS_CRA:
                mapID = 7378;
                cellID = 324;
                break;
            case CLASS_SADIDA:
                mapID = 7395;
                cellID = 357;
                break;
            case CLASS_SACRIEUR:
                mapID = 7336;
                cellID = 198;
                break;
            case CLASS_PANDAWA:
                mapID = 8035;
                cellID = 340;
                break;
            default:
                mapID = 7427;
                cellID = 267;
        }

        return new short[]{mapID, cellID};
    }

    /**
     * Retourne les stats de base en fonction de la classe et du lvl
     *
     * @param p
     * @return
     */
    public static void setStartStats(Player p) {
        Stats s = p.getBaseStats();
        s.add(Stats.Element.PA, BASE_PA);
        if (p.getLevel() >= 100) {
            s.add(Stats.Element.PA, BONUS_PA_LVL100);
        }
        s.add(Stats.Element.PM, BASE_PM);
        s.add(Stats.Element.INIT, BASE_INIT);
        s.add(Stats.Element.INVOC, BASE_INVOC);
        s.add(Stats.Element.PODS, BASE_PODS);
        s.add(Stats.Element.PROSPEC, BASE_PROS);
        if (p.getClassID() == CLASS_ENUTROF) {
            s.add(Stats.Element.PROSPEC, PROS_BONUS_ENU);
        }
    }

    /*public static TreeMap<Integer, SortStats> getStartSorts(int classID) {
        TreeMap<Integer, SortStats> start = new TreeMap<Integer, SortStats>();
        switch (classID) {
            case CLASS_FECA:
                start.put(3, World.getSort(3).getStatsByLevel(1));//Attaque Naturelle
                start.put(6, World.getSort(6).getStatsByLevel(1));//Armure Terrestre
                start.put(17, World.getSort(17).getStatsByLevel(1));//Glyphe Agressif
                break;
            case CLASS_SRAM:
                start.put(61, World.getSort(61).getStatsByLevel(1));//Sournoiserie
                start.put(72, World.getSort(72).getStatsByLevel(1));//Invisibilité
                start.put(65, World.getSort(65).getStatsByLevel(1));//Piege sournois
                break;
            case CLASS_ENIRIPSA:
                start.put(125, World.getSort(125).getStatsByLevel(1));//Mot Interdit
                start.put(128, World.getSort(128).getStatsByLevel(1));//Mot de Frayeur
                start.put(121, World.getSort(121).getStatsByLevel(1));//Mot Curatif
                break;
            case CLASS_ECAFLIP:
                start.put(102, World.getSort(102).getStatsByLevel(1));//Pile ou Face
                start.put(103, World.getSort(103).getStatsByLevel(1));//Chance d'ecaflip
                start.put(105, World.getSort(105).getStatsByLevel(1));//Bond du felin
                break;
            case CLASS_CRA:
                start.put(161, World.getSort(161).getStatsByLevel(1));//Fleche Magique
                start.put(169, World.getSort(169).getStatsByLevel(1));//Fleche de Recul
                start.put(164, World.getSort(164).getStatsByLevel(1));//Fleche Empoisonnée(ex Fleche chercheuse)
                break;
            case CLASS_IOP:
                start.put(143, World.getSort(143).getStatsByLevel(1));//Intimidation
                start.put(141, World.getSort(141).getStatsByLevel(1));//Pression
                start.put(142, World.getSort(142).getStatsByLevel(1));//Bond
                break;
            case CLASS_SADIDA:
                start.put(183, World.getSort(183).getStatsByLevel(1));//Ronce
                start.put(200, World.getSort(200).getStatsByLevel(1));//Poison Paralysant
                start.put(193, World.getSort(193).getStatsByLevel(1));//La bloqueuse
                break;
            case CLASS_OSAMODAS:
                start.put(34, World.getSort(34).getStatsByLevel(1));//Invocation de tofu
                start.put(21, World.getSort(21).getStatsByLevel(1));//Griffe Spectrale
                start.put(23, World.getSort(23).getStatsByLevel(1));//Cri de l'ours
                break;
            case CLASS_XELOR:
                start.put(82, World.getSort(82).getStatsByLevel(1));//Contre
                start.put(81, World.getSort(81).getStatsByLevel(1));//Ralentissement
                start.put(83, World.getSort(83).getStatsByLevel(1));//Aiguille
                break;
            case CLASS_PANDAWA:
                start.put(686, World.getSort(686).getStatsByLevel(1));//Picole
                start.put(692, World.getSort(692).getStatsByLevel(1));//Gueule de bois
                start.put(687, World.getSort(687).getStatsByLevel(1));//Poing enflammé
                break;
            case CLASS_ENUTROF:
                start.put(51, World.getSort(51).getStatsByLevel(1));//Lancer de Piece
                start.put(43, World.getSort(43).getStatsByLevel(1));//Lancer de Pelle
                start.put(41, World.getSort(41).getStatsByLevel(1));//Sac animé
                break;
            case CLASS_SACRIEUR:
                start.put(432, World.getSort(432).getStatsByLevel(1));//Pied du Sacrieur
                start.put(431, World.getSort(431).getStatsByLevel(1));//Chatiment Forcé
                start.put(434, World.getSort(434).getStatsByLevel(1));//Attirance
                break;
        }
        return start;
    }*/
}
