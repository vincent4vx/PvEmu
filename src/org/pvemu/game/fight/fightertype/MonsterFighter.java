package org.pvemu.game.fight.fightertype;

import java.util.Collection;
import org.pvemu.game.fight.Fight;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.monster.MonsterTemplate;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterFighter extends AIFighter{
    final private int id;
    final private MonsterTemplate monster;

    public MonsterFighter(int id, MonsterTemplate monster, Stats baseStats, Fight fight) {
        super(baseStats, fight);
        this.id = id;
        this.monster = monster;
    }

    @Override
    protected byte AIType() {
        return monster.getModel().AI_type;
    }

    @Override
    public GameSpell getSpellById(int spellID) {
        return monster.getSpells().get(spellID);
    }
    
    @Override
    public Collection<GameSpell> getSpellList(){
        return monster.getSpells().values();
    }

    @Override
    public String getGMData() {
        return GeneratorsRegistry.getFight().generateMonsterGMPacket(this);
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getName() {
        return String.valueOf(monster.getModel().id);
    }

    @Override
    public int getInitiative() {
        return monster.getBasicStats().get(Stats.Element.INIT);
    }

    @Override
    public short getLevel() {
        return monster.getLevel();
    }

    @Override
    public short getGfxID() {
        return monster.getModel().gfxID;
    }

    public MonsterTemplate getMonster() {
        return monster;
    }

    @Override
    public String[] getColors() {
        return Utils.split(monster.getModel().colors, ",", 3);
    }

    @Override
    public byte getAlignment() {
        return monster.getModel().align;
    }

    @Override
    public boolean isReady() {
        return true;
    }
}
