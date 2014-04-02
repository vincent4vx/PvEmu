PvEmu
=====

Emulateur dofus en java créé par __v4vx__

## Objetifs

Cet émulateur a été créé principalement pour m'entrainer, et utiliser différents design pattern, et pas vraiment pour faire un serveur, ou qu'il soit vraiment exploitable et débug 100%.
Je l'ai dev dans l'optique qu'il soit le plus propre possible, le plus modulable et performant.
Pour ce faire j'ai ajouté une API de scripting javascript, en utilisant l'excellentissime moteur rhino de Mozilla.

## Fonctionnalités

- Apache mina comme fw réseau
- API scripting js (mozilla rhino)
- système de packets très extensible
- (future) possibiliter de faire tourner plusieurs versions de dofus 1.x.x
- Lazy-loading
- Système de commande beaucoup plus flexible
- Mono serveur
- la plupart des actions, fonctionnalités peuvent être étendues et modifiés via l'API de scripting

## API de scripting

### Ajout de packet :

```js
network.game.GameInputHandler.instance().registerPacket({
    id: function(){
        return 'AP'; //id du packet (pa exemple generation du nom)
    },
    perform: function(extra, session){
        //action à faire
        Shell.println('Using API !');
        
        var name = '';
        var voy = 'aeiouy';
        var cons = 'bcdfghjklmnpqrstvwxz';
        var useVoy = true;
        for(var i = 0; i < 12; ++i){
            if(useVoy)
                name += voy.charAt(Utils.rand(0, voy.length));
            else
                name += cons.charAt(Utils.rand(0, cons.length));
            useVoy = !useVoy;
        }

        GamePacketEnum.CHARACTER_GENERATOR_NAME.send(session, name);
    }
});
```

### Ajout d'une commande :

```js
CommandsHandler.instance().registerCommand(JavaAdapter(Command, {
    name : function(){
        return "useless"; //nom de la commande
    },
    perform: function(args, asker){
        asker.write("I am useless !");
    }
}));
```

### Modification d'un générateur de packets :

```js
var generators = network.generators;
var Element = game.objects.dep.Stats.Element;

var PlayerGenerator = JavaAdapter(generators.PlayerGenerator, {
    generateGM: function(player){
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
    },
    generateAs: function(player){
            var packet = '';

            packet += '0' + '|' + '0' + '|' + '150' + '|'; //cur xp | min xp | max xp
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

            return packet;
    }
});

generators.GeneratorsRegistry.setPlayer(PlayerGenerator);
```

### Ajout d'un canal de discution

```js
game.chat.ChatHandler.instance().registerChannel({
    id: function(){
        return '&'; //[id] du canal (BM[id]|[message])
    },
    post: function(msg, player){
        GamePacketEnum.CHAT_MESSAGE_OK.sendToAll('|' + player.getID() + '|' + player.getName() + '|' + msg);
        Shell.println("Message envoyé à tous !");
    },
    condition: function(){
        return new jelly.filters.YesFilter();
    }
});
```

### Execution de scripts :

```js
API.execute("monScript"); // exécute un fichier js (ne pas mettre l'extension !)
API.execDir("monDir/monSousDir"); //exécute tout les scripts du dossier indiqué
```

## Remerciements

- Blackrush pour ses conseils java
- Sébastien NEDJAR prof (de java) à l'IUT d'Aix-en-Provence
- Mohammed pour son soutien
- Ankama-games pour leur super jeu

