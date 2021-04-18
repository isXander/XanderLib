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

package co.uk.isxander.xanderlib.hypixel.locraw;

public enum GameType {
    /* Bedwars */
    BEDWARS("Bedwars"),
    /* Skyblock */
    SKYBLOCK("Skyblock"),
    /* Prototype Games */
    PROTOTYPE("Prototype"),
    /* Arcade */
    ARCADE("Arcade"),
    /* The Pit */
    PIT("The Pit"),
    /* Main Lobby */
    MAIN("Main Lobby"),
    /* Skywars */
    SKYWARS("Skywars"),
    /* Murder Mystery */
    MURDER_MYSTERY("Murder Mystery"),
    /* Housing */
    HOUSING("Housing"),
    /* Build Battle */
    BUILD_BATTLE("Build Battle"),
    /* Duels */
    DUELS("Duels"),
    /* UHC Champions */
    UHC("UHC Champions"),
    /* Tnt Games */
    TNTGAMES("TNT Games"),
    /* Classic Games */
    LEGACY("Classic Games"),
    /* Cops and Crims */
    MCGO("Cops and Crims"),
    /* Blitz SG */
    SURVIVAL_GAMES("Blitz SG"),
    /* Mega Walls */
    WALLS3("Mega Walls"),
    /* Smash Heroes */
    SUPER_SMASH("Smash Heroes"),
    /* Warlords */
    BATTLEGROUNDS("Warlords"),
    /* Limbo - the afk house */
    LIMBO("Limbo");

    private final String friendlyName;
    GameType(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String friendlyName() {
        return friendlyName;
    }

    public static GameType getType(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return LIMBO;
        }
    }

    // TODO: 13/04/2021 find out what tournament hall gametype is
}
