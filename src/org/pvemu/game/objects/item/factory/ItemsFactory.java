/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pvemu.game.objects.item.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.inventory.Inventoryable;
import org.pvemu.game.objects.item.GameItem;
import org.pvemu.game.objects.item.ItemPosition;
import org.pvemu.game.objects.item.types.Weapon;
import org.pvemu.game.effect.EffectData;
import org.pvemu.game.effect.EffectFactory;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.models.InventoryEntry;
import org.pvemu.models.ItemTemplate;
import org.pvemu.models.dao.DAOFactory;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class ItemsFactory {
    final static private Weapon punch = new Weapon(
            new Stats(), 
            parseEffects("64#1#5#0#1d5+0"), 
            new InventoryEntry(), 
            new ItemTemplate()
    );
    
    final static private Map<Integer, Set<EffectData>> effectsByItem = new HashMap<>();

    static public enum ItemType {

        NONE/* = 0*/,
        AMULET(new AmuletFactory())/* = 1*/,
        BOW(new WeaponFactory())/* = 2*/,
        WAND(new WeaponFactory())/* = 3*/,
        STAFF(new WeaponFactory())/* = 4*/,
        DAGGER(new WeaponFactory())/* = 5*/,
        SWORD(new WeaponFactory())/* = 6*/,
        HAMMER(new WeaponFactory())/* = 7*/,
        SHOVEL(new WeaponFactory())/* = 8*/,
        RING(new RingFactory())/* = 9*/,
        BELT(new BeltFactory())/* = 10*/,
        BOOTS(new BootsFactory())/* = 11*/,
        POTION/* = 12*/,
        PARCHO_EXP/* = 13*/,
        DONS/* = 14*/,
        RESSOURCE/* = 15*/,
        HELMET(new HelmetFactory())/* = 16*/,
        MANTLE(new MantleFactory())/* = 17*/,
        PET(new PetFactory())/* = 18*/,
        AXE(new WeaponFactory())/* = 19*/,
        TOOLS(new WeaponFactory())/* = 20*/,
        PICKAXE(new WeaponFactory())/* = 21*/,
        SCYTHE(new WeaponFactory())/* = 22*/,
        DOFUS(new DofusFactory())/* = 23*/,
        QUETES/* = 24*/,
        DOCUMENT/* = 25*/,
        FM_POTION/* = 26*/,
        TRANSFORM/* = 27*/,
        BOOST_FOOD/* = 28*/,
        BENEDICTION/* = 29*/,
        MALEDICTION/* = 30*/,
        RP_BUFF/* = 31*/,
        PERSO_SUIVEUR/* = 32*/,
        PAIN/* = 33*/,
        CEREALE/* = 34*/,
        FLEUR/* = 35*/,
        PLANTE/* = 36*/,
        BIERE/* = 37*/,
        BOIS/* = 38*/,
        MINERAIS/* = 39*/,
        ALLIAGE/* = 40*/,
        POISSON/* = 41*/,
        BONBON/* = 42*/,
        POTION_OUBLIE/* = 43*/,
        POTION_METIER/* = 44*/,
        POTION_SORT/* = 45*/,
        FRUIT/* = 46*/,
        OS/* = 47*/,
        POUDRE/* = 48*/,
        COMESTI_POISSON/* = 49*/,
        PIERRE_PRECIEUSE/* = 50*/,
        PIERRE_BRUTE/* = 51*/,
        FARINE/* = 52*/,
        PLUME/* = 53*/,
        POIL/* = 54*/,
        ETOFFE/* = 55*/,
        CUIR/* = 56*/,
        LAINE/* = 57*/,
        GRAINE/* = 58*/,
        PEAU/* = 59*/,
        HUILE/* = 60*/,
        PELUCHE/* = 61*/,
        POISSON_VIDE/* = 62*/,
        VIANDE/* = 63*/,
        VIANDE_CONSERVEE/* = 64*/,
        QUEUE/* = 65*/,
        METARIA/* = 66*/,
        LEGUME/* = 68*/,
        VIANDE_COMESTIBLE/* = 69*/,
        TEINTURE/* = 70*/,
        EQUIP_ALCHIMIE/* = 71*/,
        OEUF_FAMILIER/* = 72*/,
        MAITRISE/* = 73*/,
        FEE_ARTIFICE/* = 74*/,
        PARCHEMIN_SORT/* = 75*/,
        PARCHEMIN_CARAC/* = 76*/,
        CERTIFICAT_CHANIL/* = 77*/,
        RUNE_FORGEMAGIE/* = 78*/,
        BOISSON/* = 79*/,
        OBJET_MISSION/* = 80*/,
        SAC_DOS/* = 81*/,
        SHIELD(new ShieldFactory())/* = 82*/,
        SOUL_STONE(new WeaponFactory())/* = 83*/,
        CLEFS/* = 84*/,
        PIERRE_AME_PLEINE/* = 85*/,
        POPO_OUBLI_PERCEP/* = 86*/,
        PARCHO_RECHERCHE/* = 87*/,
        PIERRE_MAGIQUE/* = 88*/,
        CADEAUX/* = 89*/,
        FANTOME_FAMILIER/* = 90*/,
        DRAGODINDE/* = 91*/,
        BOUFTOU/* = 92*/,
        OBJET_ELEVAGE/* = 93*/,
        OBJET_UTILISABLE/* = 94*/,
        PLANCHE/* = 95*/,
        ECORCE/* = 96*/,
        CERTIF_MONTURE/* = 97*/,
        RACINE/* = 98*/,
        FILET_CAPTURE/* = 99*/,
        SAC_RESSOURCE/* = 100*/,
        UNKNOWN/* = 101*/,
        ARBALETE/* = 102*/,
        PATTE/* = 103*/,
        AILE/* = 104*/,
        OEUF/* = 105*/,
        OREILLE/* = 106*/,
        CARAPACE/* = 107*/,
        BOURGEON/* = 108*/,
        OEIL/* = 109*/,
        GELEE/* = 110*/,
        COQUILLE/* = 111*/,
        PRISME/* = 112*/,
        OBJET_VIVANT/* = 113*/,
        ARME_MAGIQUE/* = 114*/,
        FRAGM_AME_SHUSHU/* = 115*/,
        POTION_FAMILIER/* = 116*/
        ;
        
        final private ItemFactoryInterface factory;

        ItemType(ItemFactoryInterface factory) {
            this.factory = factory;
        }
        
        ItemType(){
            factory = new ResourceFactory();
        }

        ItemFactoryInterface getFactory() {
            return factory;
        }

    }
    
    static public GameItem recoverItem(InventoryEntry entry){
        Stats stats = parseStats(entry.stats, true);
        ItemTemplate template = DAOFactory.item().getById(entry.item_id);
        
        if(template == null)
            return null;
        
        return getTypeOfItem(template).getFactory().newItem(stats, getEffects(template), entry, template);
    }
    
    static public GameItem createItem(Inventoryable owner, ItemTemplate template, int qu, boolean maxStats){
        InventoryEntry entry = new InventoryEntry();
        entry.item_id = template.id;
        entry.owner = owner.getID();
        entry.owner_type = owner.getOwnerType();
        entry.position = ItemPosition.DEFAULT_POSITION;
        entry.qu = qu;
        
        Stats stats = parseStats(template.statsTemplate, maxStats);
        entry.stats = GeneratorsRegistry.getObject().generateStats(stats);
        
        
        return getTypeOfItem(template).getFactory().newItem(stats, getEffects(template), entry, template);
    }
    
    static public GameItem copyItem(GameItem src, Inventoryable dest_owner, int dest_qu, byte dest_pos){
        InventoryEntry entry = new InventoryEntry();
        entry.item_id = src.getTemplate().id;
        entry.owner = dest_owner.getID();
        entry.owner_type = dest_owner.getOwnerType();
        entry.position = dest_pos;
        entry.qu = dest_qu;
        entry.stats = src.getEntry().stats;
        
        Stats stats = new Stats(src.getStats());
        
        return getTypeOfItem(src.getTemplate()).getFactory().newItem(stats, getEffects(src.getTemplate()), entry, src.getTemplate());
    }
    
    static public GameItem copyItem(GameItem src, Inventoryable dest_owner, int dest_qu){
        return copyItem(src, dest_owner, dest_qu, ItemPosition.DEFAULT_POSITION);
    }
    
    static private ItemType getTypeOfItem(ItemTemplate template){
        return ItemType.values().length < template.type ? ItemType.NONE : ItemType.values()[template.type];
    }
    
    static private Stats parseStats(String strStats, boolean useMax){
        Stats stats = new Stats();
        for(String e : strStats.split(",")){
            if(e.isEmpty()){
                continue;
            }
            String[] elem_data = Utils.split(e, "#");
            
            try{
                int elemID = Integer.parseInt(elem_data[0], 16);
                int min = Integer.parseInt(elem_data[1], 16);
                int max = Integer.parseInt(elem_data[2], 16);

                max = min > max ? min : max;

                int jet = useMax ? max : Utils.rand(min, max);

                stats.add(elemID, (short)jet);
            }catch(Exception ex){}
        }
            
        return stats;
    }
    
    private static Set<EffectData> parseEffects(String strEffets){
        Set<EffectData> effects = new HashSet<>();
        
        for(String e : Utils.split(strEffets, ",")){
            if(e.isEmpty())
                continue;
            
            EffectData data = EffectFactory.parseItemEffect(e);
            
            if(data == null)
                continue;
            
            effects.add(data);
        }
        
        return effects;
    }
    
    static public Set<EffectData> getEffects(ItemTemplate template){
        if(!effectsByItem.containsKey(template.id)){
            effectsByItem.put(template.id, parseEffects(template.statsTemplate));
        }
        
        return effectsByItem.get(template.id);
    }

    public static Weapon getPunch() {
        return punch;
    }

}
