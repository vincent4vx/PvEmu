importPackage(jelly.scripting.hooks);
var Element = game.objects.dep.Stats.Element;

PlayerHooks.registerGMHook(function(player){
        var packet = new StringBuilder();

        packet.append(player.getCell().getID()).append(";").append(player.orientation).append(";");
        packet.append(player.getID()).append(";");
        packet.append(player.getName()).append(";");
        packet.append(player.getClassID()).append(";");
        packet.append(player.getGfxID()).append(";");
        packet.append(player.getSexe()).append(";");
        packet.append("0,");//1,0,0,4055064
        packet.append("0,");
        packet.append("0,");
        packet.append(player.getID()).append(";");
        packet.append(jelly.Utils.implode(";", player.getColors())).append(";");
        packet.append(player.getGMStuff()).append(";");
        packet.append(player.getLevel()>99?"1":"0").append(";");//Aura
        packet.append(";");//Emote
        packet.append(";");//Emote timer
        packet.append(";;");
        packet.append(player.restriction).append(";");//TODO: Restriction
        packet.append(";");
        packet.append(";");

        return packet.toString();
});

PlayerHooks.registerAsHook(function(player){
        var packet = new StringBuilder();

        packet.append(0).append('|').append(0).append('|').append(150).append('|'); //min xp | cur xp | max xp
        packet.append(new Short(player.getPDVMax())).append('|').append(player.getPDVMax()).append('|'); //cur pvd | max pdv
        packet.append(player.getTotalStats().get(Element.PA)).append('|').append(player.getTotalStats().get(Element.PM)).append('|');
        packet.append(18765).append('|'); //kamas
        packet.append(player.getBaseStats().get(Element.FORCE)).append('|').append(player.getBaseStats().get(Element.FORCE)).append('|');
        packet.append(player.getBaseStats().get(Element.VITA)).append('|').append(player.getBaseStats().get(Element.VITA)).append('|');
        packet.append(player.getBaseStats().get(Element.DOMMAGE)).append('|').append(player.getBaseStats().get(Element.DOMMAGE)).append('|');
        packet.append(player.getBaseStats().get(Element.CHANCE)).append('|').append(player.getBaseStats().get(Element.CHANCE)).append('|');
        packet.append(player.getBaseStats().get(Element.AGILITE)).append('|').append(player.getBaseStats().get(Element.AGILITE)).append('|');
        packet.append(player.getBaseStats().get(Element.INTEL)).append('|').append(player.getBaseStats().get(Element.INTEL)).append('|');
        packet.append(0).append('|').append(0).append('|'); //capital | spell pts
        packet.append(player.getTotalStats().get(Element.PO)).append('|');
        packet.append("10000|10000|"); //energie
        packet.append("0|"); //align ?
        packet.append(player.getTotalStats().get(Element.INVOC)).append('|');
        packet.append(player.getInitiative()).append('|');
        packet.append(player.getProspection());

        return packet.toString();
});
