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

package co.uk.isxander.xanderlib.utils;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.regex.Pattern;

public final class MinecraftUtils implements Constants {

    // credit to modcore for the regex
    public static final Pattern HYPIXEL_IP = Pattern.compile("^(?:(?:(?:.+\\.)?hypixel\\.net)|(?:209\\.222\\.115\\.\\d{1,3})|(?:99\\.198\\.123\\.[123]?\\d?))\\.?(?::\\d{1,5}\\.?)?$", Pattern.CASE_INSENSITIVE);

    public static boolean isHypixel() {
        ServerData serverData = mc.getCurrentServerData();
        return serverData != null && HYPIXEL_IP.matcher(serverData.serverIP).find();
    }

    /**
     * @deprecated Recommended to use {@link co.uk.isxander.xanderlib.ui.notification.NotificationManager} instead
     */
    public static void addChat(String prefix, String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(EnumChatFormatting.RED + prefix + " " + EnumChatFormatting.RESET + message));
    }

    /**
     * @deprecated Recommended to use {@link co.uk.isxander.xanderlib.ui.notification.NotificationManager} instead
     */
    public static void addChat(String message) {
        addChat("XanderLib", message);
    }

    public static boolean isDevelopmentEnvironment() {
        Object o = Launch.blackboard.get("fml.deobfuscatedEnvironment");
        return o != null && (Boolean) o;
    }

}
