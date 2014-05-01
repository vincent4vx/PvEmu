package org.pvemu.game.fight.ai;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.pvemu.game.effect.Effect;
import org.pvemu.game.effect.EffectData;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.fightertype.AIFighter;
import org.pvemu.game.gameaction.fight.FightActionsRegistry;
import org.pvemu.game.objects.map.MapUtils;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.jelly.Loggin;
import org.pvemu.jelly.utils.Crypt;
import org.pvemu.jelly.utils.Pair;
import org.pvemu.jelly.utils.Pathfinding;
import org.pvemu.network.game.output.GameSendersRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
final public class AIUtils {
    static private interface EfficiencyCoefficient{
        float coefficient(Effect.EffectType ET);
    }
    
    static public Fighter getNearestEnnemy(Fight fight, AIFighter fighter){
        Fighter target = null;
        int dist = Integer.MAX_VALUE;
        
        for(Fighter f : fight.getFighters()){
            Loggin.debug("found %s", f);
            if(f.getTeam() == fighter.getTeam())
                continue;
            
            int curDist = MapUtils.getDistanceBetween(
                    fight.getFightMap().getMap(), 
                    fighter.getCellId(), 
                    f.getCellId()
            );
            
            if(target == null || dist > curDist){
                dist = curDist;
                target = f;
            }
        }
        
        return target;
    }
    
    static public boolean moveNear(Fight fight, AIFighter fighter, Fighter target){
        if(fight == null || fighter == null || target == null)
            return false;
        
        Loggin.debug("try to move near %s", target);
        
        if(fighter.getNumPM() <= 0)
            return false;
        
        if(MapUtils.isAdjacentCells(fighter.getCellId(), target.getCellId()))
            return false;
        
        Collection<Short> path;
        
        try{
            path = Pathfinding.findPath(fight, fighter.getCellId(), target.getCellId());
        }catch(Exception e){
            Loggin.error("Cannot find path", e);
            return false;
        }
        
        if(path == null)
            return false;
        
        return move(fight, fighter, path);
    }
    
    static private GameSpell getBestSpellForDest(Fight fight, AIFighter fighter, short dest, EfficiencyCoefficient EC){
        GameSpell best = null;
        int efficiency = 0;
        
        for(GameSpell spell : fighter.getSpellList()){
            if(!fighter.canUseSpell(spell, dest))
                continue;
            
            int sum = getSpellEfficiency(fight, fighter, spell, dest, EC);
            
            if(sum > efficiency){
                best = spell;
                efficiency = sum;
            }
        }
        
        if(best == null || efficiency <= 0)
            return null;
        
        Loggin.debug("%s selected (efficiency=%d)", best.getModel().name, efficiency);
        
        return best;
    }
    
    static public GameSpell getBestAttackSpellForDest(Fight fight, AIFighter fighter, short dest){
        return getBestSpellForDest(fight, fighter, dest, new EfficiencyCoefficient() {
            @Override
            public float coefficient(Effect.EffectType ET) {
                if(ET == Effect.EffectType.ATTACK)
                    return 1;
                
                return ET.isFriendEffect() ? -1 : .5f;
            }
        });
    }
    
    static public GameSpell getBestHealSpellForDest(Fight fight, AIFighter fighter, short dest){
        return getBestSpellForDest(fight, fighter, dest, new EfficiencyCoefficient() {
            @Override
            public float coefficient(Effect.EffectType ET) {
                if(ET == Effect.EffectType.HEAL)
                    return 1;
                
                return ET.isFriendEffect() ? .5f : -1;
            }
        });
    }
    
    static public GameSpell getBestBuffSpellForDest(Fight fight, AIFighter caster, short dest){
        return getBestSpellForDest(fight, caster, dest, new EfficiencyCoefficient() {
            @Override
            public float coefficient(Effect.EffectType ET) {
                if(ET == Effect.EffectType.BUFF)
                    return 1;
                
                return ET.isFriendEffect() ? .5f : -1;
            }
        });
    }
    
    static public boolean attackTarget(Fight fight, AIFighter caster, Fighter target){
        if(caster.getNumPA() <= 0)
            return false;
        
        GameSpell spell = getBestAttackSpellForDest(
                fight, 
                caster, 
                target.getCellId()
        );
        
        return launchSpell(fight, caster, spell, target.getCellId());
    }
    
    static public boolean healTarget(Fight fight, AIFighter caster, Fighter target){
        if(caster.getNumPA() <= 0)
            return false;
        
        GameSpell spell = getBestHealSpellForDest(
                fight, 
                caster, 
                target.getCellId()
        );
        
        return launchSpell(fight, caster, spell, target.getCellId());
    }
    
    static public boolean launchSpell(Fight fight, AIFighter caster, GameSpell spell, short dest){
        if(spell == null)
            return false;
        
        boolean ret = caster.castSpell(spell, dest);
        
        if(!ret)
            return false;
        
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){}
        return true;
    }
    
    static private int getSpellEfficiency(Fight fight, Fighter caster, GameSpell spell, short dest, EfficiencyCoefficient EC){
        int efficiency = 0;
        
        for(EffectData ed : spell.getEffects()){
            efficiency += ed.getEffect().getEfficiency(ed, fight, caster, dest) * EC.coefficient(ed.getEffect().getEffectType());
        }
        
        return efficiency / spell.getPACost();
    }
    
    static private Pair<Short, Integer> getBestTargetForSpell(Fight fight, Fighter caster, GameSpell spell, EfficiencyCoefficient EC){
        boolean isAreaSpell = false;
        
        for(EffectData ed : spell.getEffects()){
            if(ed.getArea().charAt(0) != 'P' && ed.getArea().charAt(1) != 'a'){
                isAreaSpell = true;
                break;
            }
        }
        
        if(!isAreaSpell){
            Fighter target = null;
            int efficiency = 0;
            
            for(Fighter fighter : fight.getFighters()){
                int curEff = getSpellEfficiency(fight, caster, spell, fighter.getCellId(), EC);
                
                if(curEff > efficiency){
                    target = fighter;
                    efficiency = curEff;
                }
            }
            
            if(target == null)
                return null;
            
            return new Pair<>(target.getCellId(), efficiency);
        }
        
        Collection<Short> cells = MapUtils.parseCellList(
                "C" + Crypt.HASH[spell.getPOMax()], 
                fight.getFightMap().getMap()
        );
        
        int efficiency = 0;
        short bestCell = -1;
        
        for(short cell : cells){
            if(!caster.canUseSpell(spell, cell))
                continue;
            
            int curEff = getSpellEfficiency(fight, caster, spell, cell, EC);
            
            if(curEff > efficiency){
                bestCell = cell;
                efficiency = curEff;
            }
        }
        
        if(bestCell == -1)
            return null;
        
        return new Pair<>(bestCell, efficiency);
    }
    
    static private Pair<Short, GameSpell> getBestSpellTarget(Fight fight, AIFighter fighter, EfficiencyCoefficient EC){
        GameSpell bestSpell = null;
        Pair<Short, Integer> bestTarget = new Pair<>((short)-1, 0);
        
        for(GameSpell spell : fighter.getSpellList()){
            if(spell.getPACost() > fighter.getNumPA())
                continue;
            
            Pair<Short, Integer> target = getBestTargetForSpell(fight, fighter, spell, EC);
            
            if(target == null) //no valid target for this spell
                continue;
            
            if(bestTarget.getSecond() < target.getSecond()){
                bestSpell = spell;
                bestTarget = target;
            }
        }
        
        if(bestSpell == null)
            return null;
        
        return new Pair<>(bestTarget.getFirst(), bestSpell);
    }
    
    static public boolean attack(Fight fight, AIFighter fighter){
        Pair<Short, GameSpell> spellTarget = getBestSpellTarget(fight, fighter, new EfficiencyCoefficient() {
            @Override
            public float coefficient(Effect.EffectType ET) {
                if(ET == Effect.EffectType.ATTACK)
                    return 1;
                
                return ET.isFriendEffect() ? -1 : .5f;
            }
        });
        
        if(spellTarget == null)
            return false;
        
        return launchSpell(fight, fighter, spellTarget.getSecond(), spellTarget.getFirst());
    }
    
    static public void leavePlaceForFriends(Fight fight, AIFighter fighter){  //TODO: test if its necessary
        if(fighter.getNumPM() < 1)
            return;
        
        Set<Short> adjencentCells = MapUtils.getAdjencentCells(fight.getFightMap().getMap(), fighter.getCellId());
        
        short dest = -1;
        
        for(short cell : adjencentCells){
            if(fight.canMove(fighter, cell, (short)1)){
                dest = cell;
                break;
            }
        }
        
        if(dest == -1)
            return;
        
        Set<Short> path = new HashSet<>();
        path.add(fighter.getCellId());
        path.add(dest);
        move(fight, fighter, path);
    }
    
    static public boolean move(Fight fight, AIFighter fighter, Collection<Short> path){
        short last = fighter.getCellId();
        short pm = -1;
        StringBuilder strPath = new StringBuilder(3 * fighter.getNumPM());
        boolean first = true;
        for(short cell : path){
            if(first){
                strPath.append('a');
                first = false;
            }else{
                strPath.append(MapUtils.getDirBetweenTwoCase(
                        last, 
                        cell, 
                        fight.getFightMap().getMap(), 
                        true
                ));
            }
            strPath.append(Crypt.cellID_To_Code(cell));
            last = cell;
            
            if(++pm >= fighter.getNumPM())
                break;
        }
        
        if(!fight.canMove(fighter, last, pm)){
            Loggin.debug("%s can't move", fighter);
            return false;
        }
        
        GameSendersRegistry.getGameAction().gameActionToFight(
                fight, 
                1, 
                FightActionsRegistry.WALK,
                fighter.getID(),
                strPath.toString()
        );
        
        try{
            Thread.sleep(800 + 200 * pm);
        }catch(InterruptedException e){}
        
        fighter.removePM(pm);
        GameSendersRegistry.getEffect().removePMOnWalk(fight, fighter.getID(), pm);
        
        fight.getFightMap().moveFighter(fighter, last);
        Loggin.debug("[AI/Fight] %s arrived on cell %d", fighter, last);
        return true;
    }
}
