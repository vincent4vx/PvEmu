/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.pvemu.game.effect;

import java.util.Map.Entry;
import org.pvemu.common.Loggin;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.FightFactory;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.fight.fightertype.InvocationFighter;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.monster.MonsterFactory;
import org.pvemu.game.objects.monster.MonsterTemplate;
import org.pvemu.network.game.GamePacketEnum;
import org.pvemu.network.game.output.GameSendersRegistry;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class InvocationEffect extends EnvironmentEffect{

    @Override
    public short id() {
        return 181;
    }

    @Override
    public void applyToFight(EffectData data, Fight fight, Fighter caster, short cell) {
        MonsterTemplate template = MonsterFactory.getMonsterGrades(data.getMin()).getByLevel((short)data.getMax());
        
        if(template == null){
            Loggin.debug("Invocation not found %d at lvl %d", data.getMin(), data.getMax());
            return;
        }
        
        InvocationFighter invoc = FightFactory.newInvocation(template, caster, fight);
        fight.addInvocation(invoc, cell);
        GameSendersRegistry.getEffect().invocate(fight, caster, invoc);
        GameSendersRegistry.getGameAction().unidentifiedGameActionToFight(
                fight,
                (short)999,
                caster.getID(),
                GamePacketEnum.FIGHT_TURN_LIST.getPacket() + GeneratorsRegistry.getFight().generateTurnList(fight.getFighters())
        );
    }

    @Override
    public int getEfficiency(EffectData data, Fight fight, Fighter caster, short cell) {
        MonsterTemplate template = MonsterFactory.getMonsterGrades(data.getMin()).getByLevel((short)data.getMax());
        
        if(template == null){
            return 0;
        }
        
        double coef = 0;
        
        coef += template.getBasicStats().get(Stats.Element.AGILITE) / 100D;
        coef += template.getBasicStats().get(Stats.Element.FORCE) / 100D;
        coef += template.getBasicStats().get(Stats.Element.INTEL) / 100D;
        coef += template.getBasicStats().get(Stats.Element.CHANCE) / 100D;
        coef += template.getBasicStats().get(Stats.Element.VITA) / 200D;
        coef *= 1 + caster.getLevel() / 100D;
        
        coef += template.getBasicStats().get(Stats.Element.PA);
        coef += template.getBasicStats().get(Stats.Element.PM);
        
        
        return (int)(100 * coef);
    }
    
}
