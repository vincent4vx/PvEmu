package org.pvemu.models;

import org.pvemu.models.dao.DAOFactory;
import org.pvemu.game.objects.Player;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import org.pvemu.jelly.Utils;
import org.pvemu.jelly.utils.Crypt;
import org.apache.mina.core.session.IoSession;
import org.pvemu.jelly.filters.AbstractFilterable;
import org.pvemu.network.SessionAttributes;

public class Account extends AbstractFilterable implements org.pvemu.jelly.database.Model {

    public int id;
    public String account;
    public String pass;
    public String pseudo;
    public byte level;
    public String question;
    public String response;
    protected HashMap<Integer, Character> characters = null;
    private IoSession session;
    private String current_ip;
    
    /**
     * Compte en attente de connexion au game
     */
    static final private ConcurrentHashMap<String, Account> pendingAccounts = new ConcurrentHashMap<>();
    private String waitingTicket = null;
    private Player waitingCharacter = null;

    public HashMap<Integer, Character> getCharacters() {
        if (characters == null) {
            characters = DAOFactory.character().getByAccountId(id);
        }
        return characters;
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
    /*public String getCharactersList() {
        if (getCharacters().isEmpty()) {
            return "0";
        }

        StringBuilder packet = new StringBuilder();

        packet.append(getCharacters().size());
        for (Character c : getCharacters().values()) {
            packet.append(c.getForALK());
        }

        return packet.toString();
    }*/
    
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
        DAOFactory.character().delete(characters.remove(id));
    }
    
    /**
     * Met le compte en attente et retourne le ticket de sécurité
     * @return L'id aléatoire du compte
     */
    public String setWaiting(){
        waitingTicket = Utils.str_aleat(32);
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

    public String getCurrent_ip() {
        return current_ip;
    }

    /**
     * Si le compte est en attente de connexion au game server
     * @param ip ip qui doit correspondre à l'ip courrante du compte
     * @return true si il est en attente, false sinon
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
        return session;
    }

    /**
     * Attache la session au compte
     *
     * @param session
     */
    public void setSession(IoSession session) {
        this.session = session;
        //session.setAttribute("account", this);
        SessionAttributes.ACCOUNT.setValue(this, session);
        InetSocketAddress ISA = (InetSocketAddress)session.getRemoteAddress();
        current_ip = ISA.getAddress().getHostAddress();
    }

    /**
     * lors de la déconnexion, supprime la session
     */
    public void removeSession() {
        session = null;
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
