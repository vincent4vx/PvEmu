package models;

import java.net.InetSocketAddress;
import java.util.HashMap;
import jelly.Config;
import jelly.Utils;
import models.dao.*;
import org.apache.mina.core.session.IoSession;

public class Account implements jelly.database.Model {

    public int id;
    public String account;
    public String pass;
    public String pseudo;
    public byte level;
    public String question;
    public String response;
    protected HashMap<Integer, Character> _characters = null;
    private boolean waiting = false;
    private IoSession _session;
    private String current_ip;

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
        String decrypt = Utils.decryptPacket(password, key);
        return pass.equals(decrypt);
    }

    /**
     * Liste des personnage (packet ALK)
     *
     * @return
     */
    public String getCharactersList() {
        waiting = false;
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
     * Met le compte en attente
     */
    public void setWaiting(){
        waiting = true;
    }

    /**
     * Si le compte est en attente de connexion au game server
     *
     * @return
     */
    public boolean isWaiting(String ip) {
        return waiting && ip.equals(current_ip);
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
        waiting = false;
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
