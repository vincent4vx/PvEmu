package game.objects.dep;

import game.objects.Player;

public class ClassData {

    //stats
    public static final int BASE_PA = 6;
    public static final int BONUS_PA_LVL100 = 1;
    public static final int BASE_PM = 3;
    public static final int BASE_PROS = 100;
    public static final int PROS_BONUS_ENU = 20;
    public static final int BASE_PODS = 1000;
    public static final int BASE_INVOC = 1;
    public static final int BASE_INIT = 100;
    public static final int BASE_VITA = 50;
    public static final int VITA_PER_LVL = 5;
    //classes
    public static final int CLASS_FECA = 1;
    public static final int CLASS_OSAMODAS = 2;
    public static final int CLASS_ENUTROF = 3;
    public static final int CLASS_SRAM = 4;
    public static final int CLASS_XELOR = 5;
    public static final int CLASS_ECAFLIP = 6;
    public static final int CLASS_ENIRIPSA = 7;
    public static final int CLASS_IOP = 8;
    public static final int CLASS_CRA = 9;
    public static final int CLASS_SADIDA = 10;
    public static final int CLASS_SACRIEUR = 11;
    public static final int CLASS_PANDAWA = 12;
    public static final int SEX_MALE = 0;
    public static final int SEX_FEMALE = 1;

    /**
     * Retourne le GfxID suivant la classe et le sexe
     *
     * @param classID
     * @param sexe
     * @return
     */
    public static int getCharacterGfxID(int classID, int sexe) {
        return classID * 10 + sexe;
    }

    /**
     * Retourne le GfxID (de base) du personnage
     *
     * @param c
     * @return
     */
    public static int getCharacterGfxID(models.Character c) {
        return c.classId * 10 + c.sexe;
    }

    /**
     * Retourne la map de départ en fonctionne de la classe
     *
     * @param classID
     * @return
     */
    public static short[] getStartMap(int classID) {
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
}
