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
import co.uk.isxander.xanderlib.ui.GuiHandler;
import co.uk.isxander.xanderlib.ui.editor.AbstractGuiModifier;
import co.uk.isxander.xanderlib.ui.editor.GuiEditor;
import co.uk.isxander.xanderlib.ui.notification.NotificationManager;
import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.packet.ChannelPipelineManager;
import co.uk.isxander.xanderlib.utils.packet.adapters.*;
import co.uk.isxander.xanderlib.utils.packet.handler.CustomChannelHandlerFactory;
import co.uk.isxander.xanderlib.utils.texturemanager.ModifiedTextureManager;
import io.netty.channel.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod(modid = XanderLib.MOD_ID, name = XanderLib.MOD_NAME, version = XanderLib.MOD_VER, clientSideOnly = true)
public final class XanderLib implements Constants {

    public static final String MOD_NAME = "XanderLib";
    public static final String MOD_VER = "0.3";
    public static final String MOD_ID = "xanderlib";

    public static final Logger LOGGER = LogManager.getLogger("XanderLib");

    @Mod.Instance(MOD_ID)
    private static XanderLib instance;

    private LocrawManager locrawManager;
    private GuiHandler guiHandler;
    private NotificationManager notificationManager;
    private GuiEditor guiEditor;
    private ChannelPipelineManager channelPipelineManager;
    private ModifiedTextureManager modifiedTextureManager;

    private boolean initialised = false;

    public void initPhase() {
        if (initialised)
            return;
        initialised = true;

        modifiedTextureManager = new ModifiedTextureManager();
        locrawManager = new LocrawManager();
        guiHandler = new GuiHandler();
        guiEditor = new GuiEditor();
        notificationManager = new NotificationManager();
        channelPipelineManager = new ChannelPipelineManager();

        getGuiEditor().addModifier(GuiOptions.class, new AbstractGuiModifier() {
            @Override
            public void onInitGuiPost(GuiScreen screen, List<GuiButton> buttonList) {
                ScaledResolution res = new ScaledResolution(mc);
                buttonList.add(new GuiButtonExt(990, res.getScaledWidth() - 75, 0, 75, 20, "XanderLib"));
            }

            @Override
            public void onActionPerformedPost(GuiScreen screen, List<GuiButton> buttonList, GuiButton button) {
                if (button.id == 990)
                    getNotificationManager().push("XanderLib", "This feature has not yet been added in this release.");
            }
        });

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

    public ModifiedTextureManager getModifiedTextureManager() {
        return modifiedTextureManager;
    }

    public LocrawManager getLocrawManager() {
        return locrawManager;
    }

    public GuiHandler getGuiHandler() {
        return guiHandler;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public GuiEditor getGuiEditor() {
        return guiEditor;
    }

    public ChannelPipelineManager getChannelPipelineManager() {
        return channelPipelineManager;
    }
}
