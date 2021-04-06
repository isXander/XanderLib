/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib.utils.packet;

import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.packet.handler.IChannelHandlerCustom;
import io.netty.channel.ChannelPipeline;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public final class PacketPipelineManager implements Constants {

    private static PacketPipelineManager INSTANCE = null;

    /**
     * Returns static instance of class.
     * Creates a new instance if null.
     *
     * @return instance of class
     */
    public static PacketPipelineManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PacketPipelineManager();

        return INSTANCE;
    }

    /* List of custom channel handlers to add next tick */
    private final List<IChannelHandlerCustom> toAddHandlers;
    /* List of handler names to remove next tick */
    private final List<String> toRemoveHandlers;

    private PacketPipelineManager() {
        toAddHandlers = new ArrayList<>();
        toRemoveHandlers = new ArrayList<>();

        MinecraftForge.EVENT_BUS.register(this);
    }

    /**
     * Adds a Channel Handler to Minecraft's Nethandler
     *
     * @param handler the actual handler
     */
    public void addHandler(IChannelHandlerCustom handler) {
        toAddHandlers.add(handler);
    }

    /**
     * Removes channel handler from nethandler
     *
     * @param name of handler to remove
     */
    public void removeHandler(String name) {
        toRemoveHandlers.add(name);
    }

    /**
     * Registers / unregisters packet handlers
     */
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ChannelPipeline pipeline = mc.getNetHandler().getNetworkManager().channel().pipeline();
        for (IChannelHandlerCustom handler : toAddHandlers) {
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
        toAddHandlers.clear();

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
