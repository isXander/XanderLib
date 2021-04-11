/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib.utils;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.util.IChatComponent;

public final class ServerUtils implements Constants {

    public static void kickFromServer(IChatComponent reason) {
        if (mc.getCurrentServerData() != null) {
            mc.theWorld.sendQuittingDisconnectingPacket();
            mc.loadWorld(null);

            mc.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", reason));
        }
    }

    public static ServerAddress getCurrentServerAddress() {
        return ServerAddress.fromString(mc.getCurrentServerData().serverIP);
    }
}
