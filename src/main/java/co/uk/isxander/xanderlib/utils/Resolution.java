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

import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

/**
 * Now you have no excuse to create about 20 ScaledResolution instances per frame (:
 */
public class Resolution implements Constants {

    private static ScaledResolution res = null;
    private static Resolution instance;

    public static ScaledResolution get() {
        if (instance == null) {
            MinecraftForge.EVENT_BUS.register(instance = new Resolution());
        }

        if (res == null) {
            return res = new ScaledResolution(mc);
        }

        return res;
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        res = new ScaledResolution(mc);
    }

}
