package models;

import game.objects.GameMap;
import jelly.database.Model;

public class MapModel implements Model {

    public short id;
    public String date;
    public byte width, heigth;
    public String places;
    public String key;
    public String mapData;
    public String monsters;
    public byte capabilities;
    public String mappos;
    public byte numgroup, groupmaxsize;
    private GameMap _gameMap = null;

    public GameMap getGameMap() {
        if (_gameMap == null) {
            _gameMap = new GameMap(this);
        }
        return _gameMap;
    }

    @Override
    public int getPk() {
        return id;
    }

    @Override
    public void clear() {
    }
}
