package org.pvemu.game.objects.item;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.pvemu.game.objects.inventory.InventoryAble;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.dao.DAOFactory;

public class GameItem implements Cloneable {

    //constantes de type
    public static final byte TYPE_AMULETTE = 1;
    public static final byte TYPE_ARC = 2;
    public static final byte TYPE_BAGUETTE = 3;
    public static final byte TYPE_BATON = 4;
    public static final byte TYPE_DAGUES = 5;
    public static final byte TYPE_EPEE = 6;
    public static final byte TYPE_MARTEAU = 7;
    public static final byte TYPE_PELLE = 8;
    public static final byte TYPE_ANNEAU = 9;
    public static final byte TYPE_CEINTURE = 10;
    public static final byte TYPE_BOTTES = 11;
    public static final byte TYPE_POTION = 12;
    public static final byte TYPE_PARCHO_EXP = 13;
    public static final byte TYPE_DONS = 14;
    public static final byte TYPE_RESSOURCE = 15;
    public static final byte TYPE_COIFFE = 16;
    public static final byte TYPE_CAPE = 17;
    public static final byte TYPE_FAMILIER = 18;
    public static final byte TYPE_HACHE = 19;
    public static final byte TYPE_OUTIL = 20;
    public static final byte TYPE_PIOCHE = 21;
    public static final byte TYPE_FAUX = 22;
    public static final byte TYPE_DOFUS = 23;
    public static final byte TYPE_QUETES = 24;
    public static final byte TYPE_DOCUMENT = 25;
    public static final byte TYPE_FM_POTION = 26;
    public static final byte TYPE_TRANSFORM = 27;
    public static final byte TYPE_BOOST_FOOD = 28;
    public static final byte TYPE_BENEDICTION = 29;
    public static final byte TYPE_MALEDICTION = 30;
    public static final byte TYPE_RP_BUFF = 31;
    public static final byte TYPE_PERSO_SUIVEUR = 32;
    public static final byte TYPE_PAIN = 33;
    public static final byte TYPE_CEREALE = 34;
    public static final byte TYPE_FLEUR = 35;
    public static final byte TYPE_PLANTE = 36;
    public static final byte TYPE_BIERE = 37;
    public static final byte TYPE_BOIS = 38;
    public static final byte TYPE_MINERAIS = 39;
    public static final byte TYPE_ALLIAGE = 40;
    public static final byte TYPE_POISSON = 41;
    public static final byte TYPE_BONBON = 42;
    public static final byte TYPE_POTION_OUBLIE = 43;
    public static final byte TYPE_POTION_METIER = 44;
    public static final byte TYPE_POTION_SORT = 45;
    public static final byte TYPE_FRUIT = 46;
    public static final byte TYPE_OS = 47;
    public static final byte TYPE_POUDRE = 48;
    public static final byte TYPE_COMESTI_POISSON = 49;
    public static final byte TYPE_PIERRE_PRECIEUSE = 50;
    public static final byte TYPE_PIERRE_BRUTE = 51;
    public static final byte TYPE_FARINE = 52;
    public static final byte TYPE_PLUME = 53;
    public static final byte TYPE_POIL = 54;
    public static final byte TYPE_ETOFFE = 55;
    public static final byte TYPE_CUIR = 56;
    public static final byte TYPE_LAINE = 57;
    public static final byte TYPE_GRAINE = 58;
    public static final byte TYPE_PEAU = 59;
    public static final byte TYPE_HUILE = 60;
    public static final byte TYPE_PELUCHE = 61;
    public static final byte TYPE_POISSON_VIDE = 62;
    public static final byte TYPE_VIANDE = 63;
    public static final byte TYPE_VIANDE_CONSERVEE = 64;
    public static final byte TYPE_QUEUE = 65;
    public static final byte TYPE_METARIA = 66;
    public static final byte TYPE_LEGUME = 68;
    public static final byte TYPE_VIANDE_COMESTIBLE = 69;
    public static final byte TYPE_TEINTURE = 70;
    public static final byte TYPE_EQUIP_ALCHIMIE = 71;
    public static final byte TYPE_OEUF_FAMILIER = 72;
    public static final byte TYPE_MAITRISE = 73;
    public static final byte TYPE_FEE_ARTIFICE = 74;
    public static final byte TYPE_PARCHEMIN_SORT = 75;
    public static final byte TYPE_PARCHEMIN_CARAC = 76;
    public static final byte TYPE_CERTIFICAT_CHANIL = 77;
    public static final byte TYPE_RUNE_FORGEMAGIE = 78;
    public static final byte TYPE_BOISSON = 79;
    public static final byte TYPE_OBJET_MISSION = 80;
    public static final byte TYPE_SAC_DOS = 81;
    public static final byte TYPE_BOUCLIER = 82;
    public static final byte TYPE_PIERRE_AME = 83;
    public static final byte TYPE_CLEFS = 84;
    public static final byte TYPE_PIERRE_AME_PLEINE = 85;
    public static final byte TYPE_POPO_OUBLI_PERCEP = 86;
    public static final byte TYPE_PARCHO_RECHERCHE = 87;
    public static final byte TYPE_PIERRE_MAGIQUE = 88;
    public static final byte TYPE_CADEAUX = 89;
    public static final byte TYPE_FANTOME_FAMILIER = 90;
    public static final byte TYPE_DRAGODINDE = 91;
    public static final byte TYPE_BOUFTOU = 92;
    public static final byte TYPE_OBJET_ELEVAGE = 93;
    public static final byte TYPE_OBJET_UTILISABLE = 94;
    public static final byte TYPE_PLANCHE = 95;
    public static final byte TYPE_ECORCE = 96;
    public static final byte TYPE_CERTIF_MONTURE = 97;
    public static final byte TYPE_RACINE = 98;
    public static final byte TYPE_FILET_CAPTURE = 99;
    public static final byte TYPE_SAC_RESSOURCE = 100;
    public static final byte TYPE_ARBALETE = 102;
    public static final byte TYPE_PATTE = 103;
    public static final byte TYPE_AILE = 104;
    public static final byte TYPE_OEUF = 105;
    public static final byte TYPE_OREILLE = 106;
    public static final byte TYPE_CARAPACE = 107;
    public static final byte TYPE_BOURGEON = 108;
    public static final byte TYPE_OEIL = 109;
    public static final byte TYPE_GELEE = 110;
    public static final byte TYPE_COQUILLE = 111;
    public static final byte TYPE_PRISME = 112;
    public static final byte TYPE_OBJET_VIVANT = 113;
    public static final byte TYPE_ARME_MAGIQUE = 114;
    public static final byte TYPE_FRAGM_AME_SHUSHU = 115;
    public static final byte TYPE_POTION_FAMILIER = 116;
    //FIN types
        
    //Constants positision
    public static final byte POS_NO_EQUIPED = -1;
    public static final byte POS_AMULETTE = 0;
    public static final byte POS_ARME = 1;
    public static final byte POS_ANNEAU1 = 2;
    public static final byte POS_CEINTURE = 3;
    public static final byte POS_ANNEAU2 = 4;
    public static final byte POS_BOTTES = 5;
    public static final byte POS_COIFFE = 6;
    public static final byte POS_CAPE = 7;
    public static final byte POS_FAMILIER = 8;
    public static final byte POS_DOFUS1 = 9;
    public static final byte POS_DOFUS2 = 10;
    public static final byte POS_DOFUS3 = 11;
    public static final byte POS_DOFUS4 = 12;
    public static final byte POS_DOFUS5 = 13;
    public static final byte POS_DOFUS6 = 14;
    public static final byte POS_BOUCLIER = 15;
    public static final byte POS_DRAGODINDE = 16;
    public static final byte POS_CANDY = 20;
    public static final byte POS_CANDY1 = 21;
    public static final byte POS_CANDY2 = 22;
    public static final byte POS_CANDY3 = 23;
    public static final byte POS_CANDY4 = 24;
    public static final byte POS_CANDY5 = 25;
    public static final byte POS_CANDY6 = 26;
    public static final byte POS_CANDY7 = 27;
    //FIN position
    //FIN constantes
    private InventoryEntry inventoryEntry;
    private ItemStats itemStats;
    private int id;

    public GameItem(InventoryEntry I) {
        inventoryEntry = I;
        itemStats = I.getItemStats();
        id = I.id;
    }

    /**
     * Crée l'item et l'ajoute dans la bdd
     *
     * @param owner
     * @param stats
     * @param qu
     */
    public GameItem(InventoryAble owner, ItemStats stats, int qu, byte pos) {
        inventoryEntry = new InventoryEntry();
        inventoryEntry.item_id = stats.getID();
        inventoryEntry.owner = owner.getID();
        inventoryEntry.owner_type = owner.getOwnerType();
        inventoryEntry.position = pos;
        inventoryEntry.stats = stats.statsToString();
        inventoryEntry.qu = qu;
        DAOFactory.inventory().create(inventoryEntry);
        itemStats = stats;
        itemStats.setPosition(pos);
        id = inventoryEntry.id;
        //ObjectEvents.onAdd(owner.getSession(), this);
    }

    public ItemStats getItemStats() {
        return itemStats;
    }

    public InventoryEntry getInventoryEntry() {
        return inventoryEntry;
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

        ret.append(Integer.toHexString(id)).append('~').append(Integer.toHexString(inventoryEntry.item_id)).append('~')
                .append(Integer.toHexString(inventoryEntry.qu)).append('~').append(inventoryEntry.position == -1 ? "" : Integer.toHexString(inventoryEntry.position))
                .append('~').append(inventoryEntry.stats);

        return ret.toString();
    }

    /**
     * Retourne le nombre de pods pris pas ces items
     *
     * @return
     */
    public int getPods() {
        return itemStats.getTemplate().pods * inventoryEntry.qu;
    }

    /**
     * Si possible d'équiper l'item à la position donné
     *
     * @param pos
     * @return
     */
    public boolean canMove(byte pos) {
        if (pos == POS_NO_EQUIPED) {
            return true;
        }
        switch (itemStats.getTemplate().type) {
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
            case TYPE_SAC_DOS:
            case TYPE_CAPE:
                return pos == POS_CAPE;
            case TYPE_COIFFE:
                return pos == POS_COIFFE;
            case TYPE_ANNEAU:
                return pos == POS_ANNEAU1 || pos == POS_ANNEAU2;
            case TYPE_BOTTES:
                return pos == POS_BOTTES;
            case TYPE_CEINTURE:
                return pos == POS_CEINTURE;
            case TYPE_FAMILIER:
                return pos == POS_FAMILIER;
            case TYPE_DOFUS:
                return pos == POS_DOFUS1 || pos == POS_DOFUS2 || pos == POS_DOFUS3 || pos == POS_DOFUS4 || pos == POS_DOFUS5 || pos == POS_DOFUS6;
            case TYPE_POTION:
            case TYPE_PAIN:
            case TYPE_PARCHO_EXP:
            case TYPE_BOOST_FOOD:
            case TYPE_GELEE:
                return pos >= 35 && pos <= 48;
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
        switch (itemStats.getTemplate().type) {
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
     * Change la position
     * @param pos 
     */
    public void changePos(byte pos){
        inventoryEntry.position = pos;
        itemStats.setPosition(pos);
        DAOFactory.inventory().update(inventoryEntry);
    }
    
    /**
     * Supprime l'objet
     */
    public void delete(){
        DAOFactory.inventory().delete(inventoryEntry);
        inventoryEntry = null;
        itemStats = null;
        id = 0;
    }
    
    /**
     * Change la quantité d'objets
     * @param newQu 
     */
    public void changeQuantity(int newQu){
        inventoryEntry.qu = newQu;
        DAOFactory.inventory().update(inventoryEntry);
    }
    
    /**
     * Ajoute des objets
     * @param qu 
     */
    public void addQuantity(int qu){
        changeQuantity(inventoryEntry.qu + qu);
    }
    
    /**
     * Clone l'item courrant
     * @return 
     */
    @Override
    public GameItem clone(){
        try {
            GameItem clone = (GameItem)super.clone();
            clone.itemStats = itemStats.clone();
            
            return clone;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(GameItem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
