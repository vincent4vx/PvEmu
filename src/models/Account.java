package models;

import java.util.ArrayList;
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
    protected ArrayList<Character> _characters = null;
    private boolean waiting = false;
    private IoSession _session;

    public ArrayList<Character> getCharacters() {
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
        getCharacters().add(c);
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
        for (Character c : getCharacters()) {
            packet.append(c.getForALK());
        }

        return packet.toString();
    }

    public String onSelectServer() {
        StringBuilder param = new StringBuilder();

        param.append(Config.getString("ip", "127.0.0.1")).append(":");
        param.append(Config.getString("game_port", "5555")).append(";");
        param.append(id);
        waiting = true;

        return param.toString();
    }

    /**
     * Si le compte est en attente de connexion au game server
     *
     * @return
     */
    public boolean isWaiting() {
        return waiting;
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
