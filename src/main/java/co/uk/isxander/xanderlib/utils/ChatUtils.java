package co.uk.isxander.xanderlib.utils;

import net.minecraft.util.ChatComponentText;

public final class ChatUtils implements Constants {

    public static void printChatMessage(String message) {
        mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
    }

}
