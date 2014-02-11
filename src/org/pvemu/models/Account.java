package org.pvemu.models;

import org.pvemu.models.dao.DAOFactory;
import org.pvemu.game.objects.Player;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.jelly.Utils;
import org.pvemu.jelly.utils.Crypt;
import org.apache.mina.core.session.IoSession;

public class Account implements org.pvemu.jelly.database.Model {

    public int id;
    public String account;
    public String pass;
    public String pseudo;
    public byte level;
    public String question;
    public String response;
    protected HashMap<Integer, Character> _characters = null;
    private IoSession _session;
    private String current_ip;
    
    /**
     * Compte en attente de connexion au game
     */
    private static ConcurrentHashMap<String, Account> pendingAccounts = new ConcurrentHashMap<>();
    private String waitingTicket = null;
    private Player waitingCharacter = null;

    public HashMap<Integer, Character> getCharacters() {
        if (_characters == null) {
            _characters = DAOFactory.character().getByAccountId(id);
        }
        return _characters;
    }

    /**
     * Ajoute un personnage
     *
     * @param c
     */
    public void addCharacter(Character c) {
        getCharacters().put(c.id, c);
    }

    public boolean passValid(String password, String key) {
        //String decrypt = Utils.decryptPacket(password, key);
        String cryptPass = Crypt.encodePacket(pass, key);
        return password.equals(cryptPass);
    }

    /**
     * Liste des personnage (packet ALK)
     *
     * @return
     */
    public String getCharactersList() {
        if (getCharacters().isEmpty()) {
            return "0";
        }

        StringBuilder packet = new StringBuilder();

        packet.append(getCharacters().size());
        for (Character c : getCharacters().values()) {
            packet.append(c.getForALK());
        }

        return packet.toString();
    }
    
    /**
     * Sélectionne un personnage du compte
     * @param id
     * @return 
     */
    public Character getCharacter(int id){
        return getCharacters().get(id);
    }
    
    /**
     * Supprime le personnage
     * @param id 
     */
    public void deleteCharacter(int id){
        DAOFactory.character().delete(_characters.remove(id));
    }
    
    /**
     * Met le compte en attente et retourne le ticket de sécurité
     */
    public String setWaiting(){
        waitingTicket = Utils.str_aleat(6);
        pendingAccounts.put(waitingTicket, this);
        
        return waitingTicket;
    }
    
    /**
     * Retourne le compte en attente ayant pour ticket "ticket"
     * @param ticket
     * @return 
     */
    public static Account getWaitingAccount(String ticket){
        return pendingAccounts.get(ticket);
    }

    /**
     * Si le compte est en attente de connexion au game server
     *
     * @return
     */
    public boolean isWaiting(String ip) {
        return waitingTicket != null && ip.equals(current_ip);
    }
    
    /**
     * Supprime le compte de la liste des comptes en attente
     */
    public void removeWaiting(){
        pendingAccounts.remove(waitingTicket);
        waitingTicket = null;
        
    }
    
    /**
     * Met en attente le personnage (Dofus < 1.10.0)
     * @param p 
     */
    public void setWaitingCharacter(Player p){
        waitingCharacter = p;
    }
    
    /**
     * Retourne le personnage en attente et le retire de la mise en attente
     * (Dofus < 1.10.0)
     * @return 
     */
    public Player getWaitingCharacter(){
        Player p = waitingCharacter;
        waitingCharacter = null;
        return p;
    }

    /**
     * Retourne la session (si elle existe)
     *
     * @return
     */
    public IoSession getSession() {
        return _session;
    }

    /**
     * Attache la session au compte
     *
     * @param session
     */
    public void setSession(IoSession session) {
        _session = session;
        session.setAttribute("account", this);
        InetSocketAddress ISA = (InetSocketAddress)session.getRemoteAddress();
        current_ip = ISA.getAddress().getHostAddress();
    }

    /**
     * lors de la déconnexion, supprime la session
     */
    public void removeSession() {
        _session = null;
    }

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
        id = 0;
        account = null;
        pass = null;
        pseudo = null;
        level = 0;
        question = null;
        response = null;
    }
}
