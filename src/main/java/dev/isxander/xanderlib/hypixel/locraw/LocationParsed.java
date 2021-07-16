/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 * If you have any questions or concerns, please create
 * an issue on the github page that can be found here
 * https://github.com/isXander/XanderLib
 *
 * If you have a private concern, please contact
 * isXander @ business.isxander@gmail.com
 */

package dev.isxander.xanderlib.hypixel.locraw;

import dev.isxander.xanderlib.utils.json.BetterJsonObject;

public final class LocationParsed {

    public static final LocationParsed LIMBO = new LocationParsed("limbo", GameType.LIMBO, null, null, null);
    public static final LocationParsed UNKNOWN = new LocationParsed(null, GameType.UNKNOWN, null, null, null);

    private String server;
    private GameType gameType;
    private String mode;
    private String map;
    private String lobbyName;

    public LocationParsed(String server, GameType gameType, String mode, String map, String lobyName) {
        this.server = server;
        this.gameType = gameType;
        this.mode = mode;
        this.map = map;
        this.lobbyName = lobyName;
    }

    public LocationParsed(String json) {
        this(new BetterJsonObject(json));
    }

    public LocationParsed(BetterJsonObject o) {
        this(o.optString("server", "unknown"), GameType.getType(o.optString("gametype", "UNKNOWN")), o.optString("mode", null), o.optString("map", null), o.optString("lobbyname", null));
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public boolean isLobby() {
        return mode == null || map == null;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public void setLobbyName(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    @Override
    public String toString() {
        return "LocationParsed{" +
                "server='" + server + '\'' +
                ", gameType=" + gameType +
                ", mode='" + mode + '\'' +
                ", map='" + map + '\'' +
                '}';
    }
}
