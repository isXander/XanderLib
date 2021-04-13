/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib.hypixel.locraw;

import co.uk.isxander.xanderlib.utils.json.BetterJsonObject;

public class LocationParsed {

    public static final LocationParsed LIMBO = new LocationParsed("limbo", GameType.LIMBO, null, null);

    private String server;
    private GameType gameType;
    private String mode;
    private String map;

    public LocationParsed(String server, GameType gameType, String mode, String map) {
        this.server = server;
        this.gameType = gameType;
        this.mode = mode;
        this.map = map;
    }

    public LocationParsed(String json) {
        this(new BetterJsonObject(json));
    }

    public LocationParsed(BetterJsonObject o) {
        this(o.optString("server", "unknown"), GameType.valueOf(o.optString("gametype", "LIMBO")), o.optString("mode", null), o.optString("map", null));
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

}
