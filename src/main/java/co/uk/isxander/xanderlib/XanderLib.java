/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib;

import co.uk.isxander.xanderlib.notification.NotificationManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = XanderLib.MOD_ID, name = XanderLib.MOD_NAME, version = XanderLib.MOD_VER, clientSideOnly = true)
public final class XanderLib {

    public static final String MOD_NAME = "XanderLib";
    public static final String MOD_VER = "0.1";
    public static final String MOD_ID = "xanderlib";

    public static final Logger LOGGER = LogManager.getLogger("XanderLib");

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        NotificationManager.INSTANCE.push("XanderLib", "XanderLib is not yet completed and should not be used in production mods.", () -> {
            NotificationManager.INSTANCE.push("Follow up", "Notification toasts are also clickable!", null);
        });
    }

}
