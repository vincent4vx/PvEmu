package game.objects;

import game.objects.dep.ItemStats;
import models.InventoryEntry;
import models.dao.DAOFactory;

public class GameItem {

    //constantes de type
    public static final int TYPE_AMULETTE = 1;
    public static final int TYPE_ARC = 2;
    public static final int TYPE_BAGUETTE = 3;
    public static final int TYPE_BATON = 4;
    public static final int TYPE_DAGUES = 5;
    public static final int TYPE_EPEE = 6;
    public static final int TYPE_MARTEAU = 7;
    public static final int TYPE_PELLE = 8;
    public static final int TYPE_ANNEAU = 9;
    public static final int TYPE_CEINTURE = 10;
    public static final int TYPE_BOTTES = 11;
    public static final int TYPE_POTION = 12;
    public static final int TYPE_PARCHO_EXP = 13;
    public static final int TYPE_DONS = 14;
    public static final int TYPE_RESSOURCE = 15;
    public static final int TYPE_COIFFE = 16;
    public static final int TYPE_CAPE = 17;
    public static final int TYPE_FAMILIER = 18;
    public static final int TYPE_HACHE = 19;
    public static final int TYPE_OUTIL = 20;
    public static final int TYPE_PIOCHE = 21;
    public static final int TYPE_FAUX = 22;
    public static final int TYPE_DOFUS = 23;
    public static final int TYPE_QUETES = 24;
    public static final int TYPE_DOCUMENT = 25;
    public static final int TYPE_FM_POTION = 26;
    public static final int TYPE_TRANSFORM = 27;
    public static final int TYPE_BOOST_FOOD = 28;
    public static final int TYPE_BENEDICTION = 29;
    public static final int TYPE_MALEDICTION = 30;
    public static final int TYPE_RP_BUFF = 31;
    public static final int TYPE_PERSO_SUIVEUR = 32;
    public static final int TYPE_PAIN = 33;
    public static final int TYPE_CEREALE = 34;
    public static final int TYPE_FLEUR = 35;
    public static final int TYPE_PLANTE = 36;
    public static final int TYPE_BIERE = 37;
    public static final int TYPE_BOIS = 38;
    public static final int TYPE_MINERAIS = 39;
    public static final int TYPE_ALLIAGE = 40;
    public static final int TYPE_POISSON = 41;
    public static final int TYPE_BONBON = 42;
    public static final int TYPE_POTION_OUBLIE = 43;
    public static final int TYPE_POTION_METIER = 44;
    public static final int TYPE_POTION_SORT = 45;
    public static final int TYPE_FRUIT = 46;
    public static final int TYPE_OS = 47;
    public static final int TYPE_POUDRE = 48;
    public static final int TYPE_COMESTI_POISSON = 49;
    public static final int TYPE_PIERRE_PRECIEUSE = 50;
    public static final int TYPE_PIERRE_BRUTE = 51;
    public static final int TYPE_FARINE = 52;
    public static final int TYPE_PLUME = 53;
    public static final int TYPE_POIL = 54;
    public static final int TYPE_ETOFFE = 55;
    public static final int TYPE_CUIR = 56;
    public static final int TYPE_LAINE = 57;
    public static final int TYPE_GRAINE = 58;
    public static final int TYPE_PEAU = 59;
    public static final int TYPE_HUILE = 60;
    public static final int TYPE_PELUCHE = 61;
    public static final int TYPE_POISSON_VIDE = 62;
    public static final int TYPE_VIANDE = 63;
    public static final int TYPE_VIANDE_CONSERVEE = 64;
    public static final int TYPE_QUEUE = 65;
    public static final int TYPE_METARIA = 66;
    public static final int TYPE_LEGUME = 68;
    public static final int TYPE_VIANDE_COMESTIBLE = 69;
    public static final int TYPE_TEINTURE = 70;
    public static final int TYPE_EQUIP_ALCHIMIE = 71;
    public static final int TYPE_OEUF_FAMILIER = 72;
    public static final int TYPE_MAITRISE = 73;
    public static final int TYPE_FEE_ARTIFICE = 74;
    public static final int TYPE_PARCHEMIN_SORT = 75;
    public static final int TYPE_PARCHEMIN_CARAC = 76;
    public static final int TYPE_CERTIFICAT_CHANIL = 77;
    public static final int TYPE_RUNE_FORGEMAGIE = 78;
    public static final int TYPE_BOISSON = 79;
    public static final int TYPE_OBJET_MISSION = 80;
    public static final int TYPE_SAC_DOS = 81;
    public static final int TYPE_BOUCLIER = 82;
    public static final int TYPE_PIERRE_AME = 83;
    public static final int TYPE_CLEFS = 84;
    public static final int TYPE_PIERRE_AME_PLEINE = 85;
    public static final int TYPE_POPO_OUBLI_PERCEP = 86;
    public static final int TYPE_PARCHO_RECHERCHE = 87;
    public static final int TYPE_PIERRE_MAGIQUE = 88;
    public static final int TYPE_CADEAUX = 89;
    public static final int TYPE_FANTOME_FAMILIER = 90;
    public static final int TYPE_DRAGODINDE = 91;
    public static final int TYPE_BOUFTOU = 92;
    public static final int TYPE_OBJET_ELEVAGE = 93;
    public static final int TYPE_OBJET_UTILISABLE = 94;
    public static final int TYPE_PLANCHE = 95;
    public static final int TYPE_ECORCE = 96;
    public static final int TYPE_CERTIF_MONTURE = 97;
    public static final int TYPE_RACINE = 98;
    public static final int TYPE_FILET_CAPTURE = 99;
    public static final int TYPE_SAC_RESSOURCE = 100;
    public static final int TYPE_ARBALETE = 102;
    public static final int TYPE_PATTE = 103;
    public static final int TYPE_AILE = 104;
    public static final int TYPE_OEUF = 105;
    public static final int TYPE_OREILLE = 106;
    public static final int TYPE_CARAPACE = 107;
    public static final int TYPE_BOURGEON = 108;
    public static final int TYPE_OEIL = 109;
    public static final int TYPE_GELEE = 110;
    public static final int TYPE_COQUILLE = 111;
    public static final int TYPE_PRISME = 112;
    public static final int TYPE_OBJET_VIVANT = 113;
    public static final int TYPE_ARME_MAGIQUE = 114;
    public static final int TYPE_FRAGM_AME_SHUSHU = 115;
    public static final int TYPE_POTION_FAMILIER = 116;
    //FIN types
    //Constants positision
    public static final int POS_NO_EQUIPED = -1;
    public static final int POS_AMULETTE = 0;
    public static final int POS_ARME = 1;
    public static final int POS_ANNEAU1 = 2;
    public static final int POS_CEINTURE = 3;
    public static final int POS_ANNEAU2 = 4;
    public static final int POS_BOTTES = 5;
    public static final int POS_COIFFE = 6;
    public static final int POS_CAPE = 7;
    public static final int POS_FAMILIER = 8;
    public static final int POS_DOFUS1 = 9;
    public static final int POS_DOFUS2 = 10;
    public static final int POS_DOFUS3 = 11;
    public static final int POS_DOFUS4 = 12;
    public static final int POS_DOFUS5 = 13;
    public static final int POS_DOFUS6 = 14;
    public static final int POS_BOUCLIER = 15;
    public static final int POS_DRAGODINDE = 16;
    public static final int POS_CANDY = 20;
    public static final int POS_CANDY1 = 21;
    public static final int POS_CANDY2 = 22;
    public static final int POS_CANDY3 = 23;
    public static final int POS_CANDY4 = 24;
    public static final int POS_CANDY5 = 25;
    public static final int POS_CANDY6 = 26;
    public static final int POS_CANDY7 = 27;
    //FIN position
    //FIN constantes
    private InventoryEntry _inventory;
    private ItemStats _itemStats;
    private int id;

    public GameItem(InventoryEntry I) {
        _inventory = I;
        _itemStats = I.getItemStats();
        id = I.id;
    }

    /**
     * Crée l'item et l'ajoute dans la bdd
     *
     * @param owner
     * @param stats
     * @param qu
     */
    public GameItem(Player owner, ItemStats stats, int qu) {
        _inventory = new InventoryEntry();
        _inventory.item_id = stats.getID();
        _inventory.owner = owner.getID();
        _inventory.owner_type = 1;
        _inventory.position = -1;
        _inventory.stats = stats.statsToString();
        DAOFactory.inventory().create(_inventory);
    }

    public ItemStats getItemStats() {
        return _itemStats;
    }

    public InventoryEntry getInventory() {
        return _inventory;
    }

    /**
     * Retourne de l'id DANS l'inventaire. Utilisez getItemStats().getID(); pour
     * avoir l'id de l'item
     *
     * @return
     */
    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        ret.append(Integer.toHexString(id)).append('~').append(Integer.toHexString(_inventory.item_id)).append('~')
                .append(Integer.toHexString(_inventory.qu)).append('~').append(_inventory.position == -1 ? "" : Integer.toHexString(_inventory.position))
                .append('~').append(_inventory.stats);

        return ret.toString();
    }

    /**
     * Retourne le nombre de pods pris pas ces items
     *
     * @return
     */
    public int getPods() {
        return _itemStats.getTemplate().pods * _inventory.qu;
    }

    /**
     * Si possible d'équiper l'item à la position donné
     *
     * @param pos
     * @return
     */
    public boolean canMove(int pos) {
        if (pos == -1) {
            return true;
        }
        switch (_itemStats.getTemplate().type) {
            case TYPE_AMULETTE:
                return pos == POS_AMULETTE;
            case TYPE_ARC:
            case TYPE_BAGUETTE:
            case TYPE_BATON:
            case TYPE_DAGUES:
            case TYPE_EPEE:
            case TYPE_MARTEAU:
            case TYPE_PELLE:
                return pos == POS_ARME;
            default:
                return false;
        }
    }

    /**
     * Test si c'est un équipement ou non (potions, ect...)
     *
     * @return
     */
    public boolean isWearable() {
        switch (_itemStats.getTemplate().type) {
            case TYPE_AMULETTE:
            case TYPE_ARC:
            case TYPE_BAGUETTE:
            case TYPE_BATON:
            case TYPE_DAGUES:
            case TYPE_EPEE:
            case TYPE_MARTEAU:
            case TYPE_PELLE:
            case TYPE_ANNEAU:
            case TYPE_CEINTURE:
            case TYPE_BOTTES:
            case TYPE_COIFFE:
            case TYPE_CAPE:
            case TYPE_FAMILIER:
            case TYPE_HACHE:
            case TYPE_OUTIL:
            case TYPE_PIOCHE:
            case TYPE_FAUX:
            case TYPE_DOFUS:
            case TYPE_SAC_DOS:
            case TYPE_BOUCLIER:
            case TYPE_PIERRE_AME:
            case TYPE_OBJET_VIVANT:
            case TYPE_ARME_MAGIQUE:
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Déplace l'objet et le sauvegarde
     * @param pos 
     */
    public void move(byte pos){
        if(!canMove(pos)){
            return;
        }
        _inventory.position = pos;
        _itemStats.setPosition(pos);
        DAOFactory.inventory().update(_inventory);
    }
    
    /**
     * Supprime l'objet
     */
    public void delete(){
        DAOFactory.inventory().delete(_inventory);
        _itemStats = null;
        id = 0;
    }
}
