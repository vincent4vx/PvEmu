package org.pvemu.game.fight.fightertype;

import org.pvemu.game.fight.Fight;
import org.pvemu.game.fight.Fighter;
import org.pvemu.game.objects.dep.Stats;
import org.pvemu.game.objects.item.types.Weapon;
import org.pvemu.game.objects.monster.MonsterTemplate;
import org.pvemu.game.objects.spell.GameSpell;
import org.pvemu.jelly.utils.Utils;
import org.pvemu.network.generators.GeneratorsRegistry;

/**
 *
 * @author Vincent Quatrevieux <quatrevieux.vincent@gmail.com>
 */
public class MonsterFighter extends Fighter{
    final private int id;
    final private MonsterTemplate monster;

    public MonsterFighter(int id, MonsterTemplate monster, Stats baseStats, Fight fight) {
        super(baseStats, fight);
        this.id = id;
        this.monster = monster;
    }

    @Override
    public void startTurn() {
        super.startTurn();
        (new Thread(){
            @Override
            public void run(){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                }
                fight.nextFighter();
            }
        }).start();
    }
    

    @Override
    public boolean canUseWeapon(Weapon weapon, short dest) {
        throw new UnsupportedOperationException("A monster doesn't have any weapon");
    }

    @Override
    public boolean canUseSpell(GameSpell spell, short dest) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GameSpell getSpellById(int spellID) {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public short getInitiative() {
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
    public boolean isReady() {
        return true;
    }
    
    
}
