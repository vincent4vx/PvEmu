package game.objects.dep;

public class ClassData {

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
}
