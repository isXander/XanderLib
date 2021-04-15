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

package co.uk.isxander.xanderlib.ui;

import co.uk.isxander.xanderlib.utils.Constants;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public final class GuiHandler implements Constants {

    private static GuiHandler INSTANCE = null;

    private GuiScreen screen;

    private GuiHandler() {
        this.screen = null;
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void open(GuiScreen screen) {
        this.screen = screen;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (screen != null) {
            mc.displayGuiScreen(screen);
            screen = null;
        }
    }

    public static GuiHandler getInstance() {
        if (INSTANCE == null)
            INSTANCE = new GuiHandler();

        return INSTANCE;
    }

}
