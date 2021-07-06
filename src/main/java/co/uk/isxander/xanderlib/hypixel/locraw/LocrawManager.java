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

import club.sk1er.mods.core.util.MinecraftUtils;
import club.sk1er.mods.core.util.Multithreading;
import co.uk.isxander.xanderlib.XanderLib;
import co.uk.isxander.xanderlib.event.NewLocationEvent;
import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.json.BetterJsonObject;
import co.uk.isxander.xanderlib.utils.json.JsonUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

public final class LocrawManager implements Constants {

    private LocationParsed currentLocation = LocationParsed.LIMBO;
    private boolean waitingForLocraw = false;

    public LocrawManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        if (MinecraftUtils.isHypixel()) {
            String message = event.message.getUnformattedText();

            switch (message) {
                // Hypixel sends either of these messages when you switch lobbies
                case "                         ":
                case "       ":
                    enqueueUpdate(500);
                    break;
                case "You are sending too many commands! Please try again in a few seconds.":
                    enqueueUpdate(5000);
                    break;
                // User has gone to limbo.
                case "You are AFK. Move around to return from AFK.":
                    currentLocation = LocationParsed.LIMBO;
            }

            if (JsonUtils.isValidJson(message)) {
                BetterJsonObject json = new BetterJsonObject(message);
                if (json.getData() != null && json.has("server")) {
                    if (waitingForLocraw) {
                        waitingForLocraw = false;
                        event.setCanceled(true);
                    }
                    currentLocation = new LocationParsed(json);
                    MinecraftForge.EVENT_BUS.post(new NewLocationEvent(currentLocation));
                    XanderLib.LOGGER.info("New Location: " + currentLocation);
                }
            }
        } else {
            currentLocation = LocationParsed.UNKNOWN;
        }
    }

    public void enqueueUpdate(long delayMs) {
        if (!waitingForLocraw) {
            waitingForLocraw = true;
            Multithreading.schedule(() -> {
                mc.thePlayer.sendChatMessage("/locraw");
            }, delayMs, TimeUnit.MILLISECONDS);
        }
    }

    public LocationParsed getCurrentLocation() {
        return currentLocation;
    }

}
