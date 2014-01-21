importPackage(jelly.scripting.hooks);
var Element = game.objects.dep.Stats.Element;

PlayerHooks.registerGMHook(function(player){
    return player.getCell().getID() + ';' + player.orientation + ';'
            + player.getID() + ';'
            + player.getName() + ';'
            + player.getClassID() + ';'
            + player.getGfxID() + ';'
            + player.getSexe() + ';'
            + '0,'
            + '0,'
            + '0,'
            + player.getID() + ';'
            + jelly.Utils.implode(';', player.getColors()) + ';'
            + player.getGMStuff() + ';'
            + (player.getLevel() > 99 ? '1' : '0') + ';' //aura
            + ';' //emote
            + ';' //emote timer
            + ';;' // ?
            + player.restriction + ';'
            + ';;';
});

PlayerHooks.registerAsHook(function(player){
        var packet = '';

        packet += '0' + '|' + '0' + '|' + '150' + '|'; //min xp | cur xp | max xp
        packet += player.getPDVMax() + '|' + player.getPDVMax() + '|'; //cur pvd | max pdv
        packet += player.getTotalStats().get(Element.PA) + '|' + player.getTotalStats().get(Element.PM) + '|';
        packet += 18765 + '|'; //kamas
        packet += player.getBaseStats().get(Element.FORCE) + '|' + player.getBaseStats().get(Element.FORCE) + '|';
        packet += player.getBaseStats().get(Element.VITA) + '|' + player.getBaseStats().get(Element.VITA) + '|';
        packet += player.getBaseStats().get(Element.DOMMAGE) + '|' + player.getBaseStats().get(Element.DOMMAGE) + '|';
        packet += player.getBaseStats().get(Element.CHANCE) + '|' + player.getBaseStats().get(Element.CHANCE) + '|';
        packet += player.getBaseStats().get(Element.AGILITE) + '|' + player.getBaseStats().get(Element.AGILITE) + '|';
        packet += player.getBaseStats().get(Element.INTEL) + '|' + player.getBaseStats().get(Element.INTEL) + '|';
        packet += 0 + '|' + 0 + '|'; //capital | spell pts
        packet += player.getTotalStats().get(Element.PO) + '|';
        packet += "10000|10000|"; //energie
        packet += "0|"; //align ?
        packet += player.getTotalStats().get(Element.INVOC) + '|';
        packet += player.getInitiative() + '|';
        packet += player.getProspection();

        return packet.toString();
});
