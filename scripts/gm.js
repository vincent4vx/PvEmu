importPackage(jelly.scripting.hooks);

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
