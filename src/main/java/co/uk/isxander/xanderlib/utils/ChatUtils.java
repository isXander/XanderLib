/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib.utils;

import net.minecraft.util.ChatComponentText;

public final class ChatUtils implements Constants {

    public static void printChatMessage(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }

}
