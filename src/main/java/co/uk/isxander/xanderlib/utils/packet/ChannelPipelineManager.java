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

package co.uk.isxander.xanderlib.utils.packet;

import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.packet.handler.IChannelHandlerCustom;
import io.netty.channel.ChannelPipeline;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public final class ChannelPipelineManager implements Constants {

    private static ChannelPipelineManager INSTANCE = null;

    /**
     * Returns static instance of class.
     * Creates a new instance if null.
     *
     * @return instance of class
     */
    public static ChannelPipelineManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ChannelPipelineManager();

        return INSTANCE;
    }

    /* List of custom channel handlers to add next tick */
    private final Map<IChannelHandlerCustom, Boolean> handlers;
    /* List of handler names to remove next tick */
    private final List<String> toRemoveHandlers;

    private ChannelPipelineManager() {
        handlers = new HashMap<>();
        toRemoveHandlers = new ArrayList<>();

        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Adds a Channel Handler to Minecraft's Nethandler
     * Also re-registers handler if present
     *
     * @param handler the actual handler
     */
    public void addHandler(IChannelHandlerCustom handler) {
        handlers.put(handler, false);
    }

    /**
     * Removes channel handler from nethandler
     *
     * @param handler to remove
     */
    public void removeHandler(IChannelHandlerCustom handler) {
        handlers.remove(handler);
        toRemoveHandlers.add(handler.name());
    }

    /**
     * Registers / unregisters packet handlers
     */
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (mc.getNetHandler() == null) {
            return;
        }

        ChannelPipeline pipeline = mc.getNetHandler().getNetworkManager().channel().pipeline();
        new HashMap<>(handlers).forEach((handler, added) -> {
            if (!added) {
                boolean meetsRequirements = true;
                for (String requirement : handler.requires()) {
                    if (pipeline.get(requirement) == null)
                        meetsRequirements = false;
                }

                if (meetsRequirements) {
                    try {
                        if (handler.addBefore() != null) {
                            pipeline.addBefore(handler.addBefore(), handler.name(), handler.handler());
                        } else if (handler.addAfter() != null) {
                            pipeline.addAfter(handler.addAfter(), handler.name(), handler.handler());
                        } else {
                            if (handler.first())
                                pipeline.addFirst(handler.name(), handler.handler());
                            else
                                pipeline.addLast(handler.name(), handler.handler());
                        }
                    } catch (NoSuchElementException | NullPointerException | IllegalArgumentException e) {
                        e.printStackTrace();
                    }

                }
            }
            handlers.put(handler, true);
        });

        for (String name : toRemoveHandlers) {
            if (pipeline.get(name) != null) {
                try {
                    pipeline.remove(name);
                } catch (NoSuchElementException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        toRemoveHandlers.clear();
    }

}
