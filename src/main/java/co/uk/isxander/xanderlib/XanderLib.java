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

package co.uk.isxander.xanderlib;

import co.uk.isxander.xanderlib.event.PacketEvent;
import co.uk.isxander.xanderlib.hypixel.locraw.LocrawManager;
import co.uk.isxander.xanderlib.ui.editor.GuiEditor;
import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.packet.ChannelPipelineManager;
import co.uk.isxander.xanderlib.utils.packet.adapters.*;
import co.uk.isxander.xanderlib.utils.packet.handler.CustomChannelHandlerFactory;
import io.netty.channel.*;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = XanderLib.MOD_ID, name = XanderLib.MOD_NAME, version = XanderLib.MOD_VER, clientSideOnly = true)
public final class XanderLib implements Constants {

    public static final String MOD_NAME = "XanderLib";
    public static final String MOD_VER = "0.8";
    public static final String MOD_ID = "xanderlib";

    public static final Logger LOGGER = LogManager.getLogger("XanderLib");

    @Mod.Instance(MOD_ID)
    private static XanderLib instance;

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

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        initPhase();
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {

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
