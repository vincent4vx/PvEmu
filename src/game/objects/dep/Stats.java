package game.objects.dep;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Stats {
    public enum Element{
        //basiques
        PA(new int[]{111, 120}, new int[]{101, 168}),
        PM(new int[]{78, 128}, new int[]{169, 127}),
        
        //primaires
        FORCE(new int[]{118}, new int[]{157}),
        INTEL(new int[]{126}, new int[]{155}),
        AGILITE(new int[]{119}, new int[]{154}),
        CHANCE(new int[]{123}, new int[]{152}),
        SAGESSE(new int[]{124}, new int[]{156}),
        VITA(new int[]{125, 110}, new int[]{153}),
        
        //secondaires
        DOMMAGE(new int[]{112}, new int[]{145, 220}),
        PERDOM(new int[]{138, 142}, new int[]{}),
        MAITRISE(new int[]{165}, new int[]{}),
        TRAP_DOM(new int[]{225}, new int[]{}),
        TRAP_PERDOM(new int[]{226}, new int[]{}),
        CC(new int[]{115}, new int[]{171}),
        EC(new int[]{122}, new int[]{}),
        PO(new int[]{117}, new int[]{116}),
        INIT(new int[]{174}, new int[]{175}),
        SOIN(new int[]{178}, new int[]{179}),
        
        //resistances
        
        
        //autres
        PODS(new int[]{158}, new int[]{159}),
        PROSPEC(new int[]{176}, new int[]{177}),
        INVOC(new int[]{182}, new int[]{}),
        ;
            
        private int[] add_id;
        private int[] rem_id;
        
        Element(int[] add_id, int[] rem_id){
            this.add_id = add_id;
            this.rem_id = rem_id;
        }
        
        /**
         * Si l'élément a une valeur négative
         * @param id
         * @return 
         */
        public boolean isRemove(int id){
            if(rem_id.length == 0){
                return false;
            }
            for(int i : rem_id){
                if(i == id){
                    return true;
                }
            }
            return false;
        }
        
        public int getId(boolean isRemove){
            if(isRemove && rem_id.length != 0){
                return rem_id[0];
            }else if(add_id.length != 0){
                return add_id[0];
            }
            return -1;
        }
    }
    
    private static final HashMap<Integer, Element> intToElement = new HashMap<>();
    private ConcurrentHashMap<Element, Integer> stats = new ConcurrentHashMap<>();
    
    /**
     * Ajoute qu Element aux stats courentes
     * @param e
     * @param qu
     * @return 
     */
    public Stats add(Element e, int qu){
        if(stats.containsKey(e)){
            qu += stats.get(e);
        }
        
        stats.put(e, qu);
        
        return this;
    }
    
    /**
     * Ajoute l'élément d'id elemId aux stats courentes
     * @param elemId
     * @param qu
     * @return 
     */
    public Stats add(int elemId, int qu){
        Element e = intToElement.get(qu);
        
        if(e != null){
            if(e.isRemove(elemId)){
                qu = -qu;
            }
            add(e, qu);
        }
            
        return this;
    }
    
    /**
     * Retourne la quatité total de l'élément e
     * @param e
     * @return 
     */
    public int get(Element e){
        if(e != null && stats.containsKey(e)){
            return stats.get(e);
        }
        return 0;
    }
    
    /**
     * Retourne la quantité total de l'élément associé à elemId
     * @param elemId
     * @return 
     */
    public int get(int elemId){
        Element e = intToElement.get(elemId);
        return get(e);
    }
    
    public static void loadElements(){
        System.out.println("Chargement des élements...");
        int num = 0;
        for(Element e : Element.values()){
            for(int id : e.add_id){
                intToElement.put(id, e);
                num++;
            }
            for(int id : e.rem_id){
                intToElement.put(id, e);
                num++;
            }
        }
        System.out.println(num + " éléments chargées !");
    }
}
