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

package dev.isxander.xanderlib;

import dev.isxander.xanderlib.event.PacketEvent;
import dev.isxander.xanderlib.hypixel.locraw.LocrawManager;
import dev.isxander.xanderlib.ui.editor.GuiEditor;
import dev.isxander.xanderlib.utils.Constants;
import dev.isxander.xanderlib.utils.packet.ChannelPipelineManager;
import dev.isxander.xanderlib.utils.packet.adapters.SharableChannelInboundHandlerAdapter;
import dev.isxander.xanderlib.utils.packet.adapters.SharableChannelOutboundHandlerAdapter;
import dev.isxander.xanderlib.utils.packet.handler.CustomChannelHandlerFactory;
import io.netty.channel.*;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class XanderLib implements Constants {

    public static final String MOD_NAME = "XanderLib";
    public static final String MOD_VER = "@version@";
    public static final String MOD_ID = "xanderlib";

    public static final Logger LOGGER = LogManager.getLogger("XanderLib");

    private static XanderLib instance = new XanderLib();

    private LocrawManager locrawManager;
    private GuiEditor guiEditor;
    private ChannelPipelineManager channelPipelineManager;

    private boolean initialised = false;

    public void initPhase() {
        if (initialised)
            return;
        initialised = true;

        locrawManager = new LocrawManager();
        guiEditor = new GuiEditor();
        channelPipelineManager = new ChannelPipelineManager();

        getChannelPipelineManager().addHandler(CustomChannelHandlerFactory.newInstance()
                .setName("xanderlib_packet_listener_inbound")
                .setHandler(new SharableChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        super.channelRead(ctx, msg);
                        if (msg instanceof Packet)
                            MinecraftForge.EVENT_BUS.post(new PacketEvent.Incoming((Packet<?>) msg));
                    }
                })
                .setAddBefore("packet_handler")
                .build());
        getChannelPipelineManager().addHandler(CustomChannelHandlerFactory.newInstance()
                .setName("xanderlib_packet_listener_outbound")
                .setHandler(new SharableChannelOutboundHandlerAdapter() {
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        super.write(ctx, msg, promise);
                        if (msg instanceof Packet)
                            MinecraftForge.EVENT_BUS.post(new PacketEvent.Outgoing((Packet<?>) msg));
                    }
                })
                .setAddBefore("packet_handler")
                .build());
    }

    public static XanderLib getInstance() {
        return instance;
    }

    public LocrawManager getLocrawManager() {
        return locrawManager;
    }

    public GuiEditor getGuiEditor() {
        return guiEditor;
    }

    public ChannelPipelineManager getChannelPipelineManager() {
        return channelPipelineManager;
    }
}
