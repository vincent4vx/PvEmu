package org.pvemu.game.objects.map;

import org.pvemu.game.objects.Player;
import org.pvemu.game.objects.map.GameMap;
import org.pvemu.game.objects.map.MapCell;
import org.pvemu.jelly.Loggin;
import org.pvemu.network.events.ChatEvents;

public class InteractiveObject {
    private int objID;
    private GameMap _map;
    private MapCell _cell;
    
    public InteractiveObject(int objID, MapCell cell, GameMap map){
        this.objID = objID;
        _cell = cell;
        _map = map;
    }
    
    public int getID(){
        return objID;
    }
    
    /**
     * Démarre une MapAction
     * @param p 
     */
    public void startAction(Player p, int action){
        if(!isValidObjectForAction(objID, action)){
            return;
        }
        
        switch(action){
            case 44: //sauvegarde de la position
                p.setStartPos(new short[]{_map.getID(), _cell.getID()});
                ChatEvents.onSendInfoMessage(p.getSession(), 6);
                break;
            default:
                Loggin.debug("MapAction non reconnue : %d (map = %d, cell = %d, IO = %d)", action, _map.getID(), _cell.getID(), objID);
        }
    }

    private static boolean isValidObjectForAction(int objID, int action) {
        switch (action) {
            //Moudre et egrenner - Paysan
            case 122:
            case 47:
                return objID == 7007;
            //Faucher Blé
            /*case 45:
                switch (objID) {
                    case 7511://Blé
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Faucher Orge
            case 53:
                switch (objID) {
                    case 7515://Orge
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;

            //Faucher Avoine
            case 57:
                switch (objID) {
                    case 7517://Avoine
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Faucher Houblon
            case 46:
                switch (objID) {
                    case 7512://Houblon
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Faucher Lin
            case 50:
            case 68:
                switch (objID) {
                    case 7513://Lin
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Faucher Riz
            case 159:
                switch (objID) {
                    case 7550://Riz
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Faucher Seigle
            case 52:
                switch (objID) {
                    case 7516://Seigle
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Faucher Malt
            case 58:
                switch (objID) {
                    case 7518://Malt
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Faucher Chanvre - Cueillir Chanvre
            case 69:
            case 54:
                switch (objID) {
                    case 7514://Chanvre
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Scier - Bucheron
            case 101:
                return objID == 7003;
            //Couper Frêne
            case 6:
                switch (objID) {
                    case 7500://Frêne
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Châtaignier
            case 39:
                switch (objID) {
                    case 7501://Châtaignier
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Noyer
            case 40:
                switch (objID) {
                    case 7502://Noyer
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Chêne
            case 10:
                switch (objID) {
                    case 7503://Chêne
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Oliviolet
            case 141:
                switch (objID) {
                    case 7542://Oliviolet
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Bombu
            case 139:
                switch (objID) {
                    case 7541://Bombu
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Erable
            case 37:
                switch (objID) {
                    case 7504://Erable
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Bambou
            case 154:
                switch (objID) {
                    case 7553://Bambou
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper If
            case 33:
                switch (objID) {
                    case 7505://If
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Merisier
            case 41:
                switch (objID) {
                    case 7506://Merisier
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Ebène
            case 34:
                switch (objID) {
                    case 7507://Ebène
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Kalyptus
            case 174:
                switch (objID) {
                    case 7557://Kalyptus
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Charme
            case 38:
                switch (objID) {
                    case 7508://Charme
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Orme
            case 35:
                switch (objID) {
                    case 7509://Orme
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Bambou Sombre
            case 155:
                switch (objID) {
                    case 7554://Bambou Sombre
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Couper Bambou Sacré
            case 158:
                switch (objID) {
                    case 7552://Bambou Sacré
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Puiser
            case 102:
                switch (objID) {
                    case 7519://Puits
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Polir
            case 48:
                return objID == 7005;//7510
            //Moule/Fondre - Mineur
            case 32:
                return objID == 7002;
            //Miner Fer
            case 24:
                switch (objID) {
                    case 7520://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Cuivre
            case 25:
                switch (objID) {
                    case 7522://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Bronze
            case 26:
                switch (objID) {
                    case 7523://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Kobalte
            case 28:
                switch (objID) {
                    case 7525://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Manga
            case 56:
                switch (objID) {
                    case 7524://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Sili
            case 162:
                switch (objID) {
                    case 7556://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Etain
            case 55:
                switch (objID) {
                    case 7521://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Argent
            case 29:
                switch (objID) {
                    case 7526://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Bauxite
            case 31:
                switch (objID) {
                    case 7528://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Or
            case 30:
                switch (objID) {
                    case 7527://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Miner Dolomite
            case 161:
                switch (objID) {
                    case 7555://Miner
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Fabriquer potion - Alchimiste
            case 23:
                return objID == 7019;
            //Cueillir Trèfle
            case 71:
                switch (objID) {
                    case 7533://Trèfle
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Cueillir Menthe
            case 72:
                switch (objID) {
                    case 7534://Menthe
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Cueillir Orchidée
            case 73:
                switch (objID) {
                    case 7535:// Orchidée
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Cueillir Edelweiss
            case 74:
                switch (objID) {
                    case 7536://Edelweiss
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Cueillir Graine de Pandouille
            case 160:
                switch (objID) {
                    case 7551://Graine de Pandouille
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Vider - Pêcheur
            case 133:
                return objID == 7024;
            //Pêcher Petits poissons de mer
            case 128:
                switch (objID) {
                    case 7530://Petits poissons de mer
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Petits poissons de rivière
            case 124:
                switch (objID) {
                    case 7529://Petits poissons de rivière
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Pichon
            case 136:
                switch (objID) {
                    case 7544://Pichon
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Ombre Etrange
            case 140:
                switch (objID) {
                    case 7543://Ombre Etrange
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Poissons de rivière
            case 125:
                switch (objID) {
                    case 7532://Poissons de rivière
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Poissons de mer
            case 129:
                switch (objID) {
                    case 7531://Poissons de mer
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Gros poissons de rivière
            case 126:
                switch (objID) {
                    case 7537://Gros poissons de rivière
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Gros poissons de mers
            case 130:
                switch (objID) {
                    case 7538://Gros poissons de mers
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Poissons géants de rivière
            case 127:
                switch (objID) {
                    case 7539://Poissons géants de rivière
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;
            //Pêcher Poissons géants de mer
            case 131:
                switch (objID) {
                    case 7540://Poissons géants de mer
                        return _object.getState() == Constants.IOBJECT_STATE_FULL;
                }
                return false;*/
            //Boulanger
            case 109://Pain
            case 27://Bonbon
                return objID == 7001;
            //Poissonier
            case 135://Faire un poisson (mangeable)
                return objID == 7022;
            //Chasseur
            case 134:
                return objID == 7023;
            //Boucher
            case 132:
                return objID == 7025;
            case 157:
                return (objID == 7030 || objID == 7031);
            case 44://Sauvegarder le Zaap
            case 114://Utiliser le Zaap
                switch (objID) {
                    //Zaaps
                    case 7000:
                    case 7026:
                    case 7029:
                    case 4287:
                        return true;
                }
                return false;

            case 175://Accéder
            case 176://Acheter
            case 177://Vendre
            case 178://Modifier le prix de vente
                switch (objID) {
                    //Enclos
                    case 6763:
                    case 6766:
                    case 6767:
                    case 6772:
                        return true;
                }
                return false;

            //Se rendre à incarnam
            case 183:
                switch (objID) {
                    case 1845:
                    case 1853:
                    case 1854:
                    case 1855:
                    case 1856:
                    case 1857:
                    case 1858:
                    case 1859:
                    case 1860:
                    case 1861:
                    case 1862:
                    case 2319:
                        return true;
                }
                return false;

            //Enclume magique
            case 1:
            case 113:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
                return objID == 7020;

            //Enclume
            case 19:
            case 143:
            case 145:
            case 144:
            case 142:
            case 146:
            case 67:
            case 21:
            case 65:
            case 66:
            case 20:
            case 18:
                return objID == 7012;

            //Costume Mage
            case 167:
            case 165:
            case 166:
                return objID == 7036;

            //Coordo Mage
            case 164:
            case 163:
                return objID == 7037;

            //Joai Mage
            case 168:
            case 169:
                return objID == 7038;

            //Bricoleur
            case 171:
            case 182:
                return objID == 7039;

            //Forgeur Bouclier
            case 156:
                return objID == 7027;

            //Coordonier
            case 13:
            case 14:
                return objID == 7011;

            //Tailleur (Dos)
            case 123:
            case 64:
                return objID == 7015;


            //Sculteur
            case 17:
            case 16:
            case 147:
            case 148:
            case 149:
            case 15:
                return objID == 7013;

            //Tailleur (Haut)
            case 63:
                return (objID == 7014 || objID == 7016);
            //Atelier : Créer Amu // Anneau
            case 11:
            case 12:
                return (objID >= 7008 && objID <= 7010);
            //Maison
            case 81://Vérouiller
            case 84://Acheter
            case 97://Entrer
            case 98://Vendre
            case 108://Modifier le prix de vente
                return (objID >= 6700 && objID <= 6776);
            //Coffre	
            case 104://Ouvrir
            case 105://Code
                return (objID == 7350 || objID == 7351 || objID == 7353);
            case 153:
                return objID == 7352;
            case 151: //Etabli pyrotechnique
                //SocketManager.GAME_SEND_MESSAGE_TO_MAP(perso.get_curCarte(), "L'établi est endommagé! Lances échange au PNJ pour confectionner des fées!", Ancestra.CONFIG_MOTD_COLOR);
                return objID == 7028;
            case 62://fontaine de jouvence
                return objID == 7004;
            //Action ID non trouvé
            default:
                Loggin.debug("MapActionID %d inconnue. IO : %d ", action, objID);
                return false;
        }
    }
}
