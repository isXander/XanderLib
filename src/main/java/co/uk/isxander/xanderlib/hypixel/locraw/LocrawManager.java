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

import co.uk.isxander.xanderlib.XanderLib;
import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.json.BetterJsonObject;
import co.uk.isxander.xanderlib.utils.json.JsonUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public final class LocrawManager implements Constants {

    private static final LocrawManager INSTANCE = new LocrawManager();

    private LocationParsed currentLocation = LocationParsed.LIMBO;
    private boolean waitingForLocraw = false;

    private LocrawManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        switch (message) {
            // Hypixel sends either of these messages when you switch lobbies
            case "                         ":
            case "       ":
                waitingForLocraw = true;
                mc.thePlayer.sendChatMessage("/locraw");
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
                XanderLib.LOGGER.info("Switched GameType to: " + currentLocation.getGameType().name());
            }
        }
    }

    public static LocrawManager getInstance() {
        return INSTANCE;
    }

    public LocationParsed getCurrentLocation() {
        return currentLocation;
    }

}
