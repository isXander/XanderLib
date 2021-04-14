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
    BEDWARS,
    /* Skyblock */
    SKYBLOCK,
    /* Prototype Games */
    PROTOTYPE,
    /* Arcade */
    ARCADE,
    /* The Pit */
    PIT,
    /* Main Lobby */
    MAIN,
    /* Skywars */
    SKYWARS,
    /* Murder Mystery */
    MURDER_MYSTERY,
    /* Housing */
    HOUSING,
    /* Build Battle */
    BUILD_BATTLE,
    /* Duels */
    DUELS,
    /* UHC Champions */
    UHC,
    /* Tnt Games */
    TNTGAMES,
    /* Classic Games */
    LEGACY,
    /* Cops and Crims */
    MCGO,
    /* Blitz SG */
    SURVIVAL_GAMES,
    /* Mega Walls */
    WALLS3,
    /* Smash Heroes */
    SUPER_SMASH,
    /* Warlords */
    BATTLEGROUNDS,
    /* Limbo - the afk house */
    LIMBO;

    public static GameType getType(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException e) {
            return LIMBO;
        }
    }

    // TODO: 13/04/2021 find out what tournament hall gametype is
}
