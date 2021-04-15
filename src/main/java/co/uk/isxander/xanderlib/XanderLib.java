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
import co.uk.isxander.xanderlib.ui.editor.AbstractCustomButton;
import co.uk.isxander.xanderlib.ui.editor.GuiEditor;
import co.uk.isxander.xanderlib.ui.notification.NotificationManager;
import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.packet.ChannelPipelineManager;
import co.uk.isxander.xanderlib.utils.packet.handler.CustomChannelHandlerFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = XanderLib.MOD_ID, name = XanderLib.MOD_NAME, version = XanderLib.MOD_VER, clientSideOnly = true)
public final class XanderLib implements Constants {

    public static final String MOD_NAME = "XanderLib";
    public static final String MOD_VER = "0.1";
    public static final String MOD_ID = "xanderlib";

    public static final Logger LOGGER = LogManager.getLogger("XanderLib");

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        ScaledResolution res = new ScaledResolution(mc);
        GuiEditor.getInstance().addButtons(GuiOptions.class, new AbstractCustomButton() {
            @Override
            protected GuiButton button() {
                return new GuiButtonExt(990, res.getScaledWidth() - 75, 0, 75, 20, "XanderLib");
            }

            @Override
            public void onActionPerformed(GuiScreen screen) {
                NotificationManager.INSTANCE.push("XanderLib", "This feature has not yet been added in this release.");
            }
        });

        ChannelPipelineManager.getInstance().addHandler(CustomChannelHandlerFactory.newInstance()
                .setName("xanderlib_packet_listener_inbound")
                .setHandler(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        super.channelRead(ctx, msg);
                        if (msg instanceof Packet)
                            MinecraftForge.EVENT_BUS.post(new PacketEvent.Incoming((Packet<?>) msg));
                    }
                })
                .build());
        ChannelPipelineManager.getInstance().addHandler(CustomChannelHandlerFactory.newInstance()
                .setName("xanderlib_packet_listener_outbound")
                .setHandler(new ChannelOutboundHandlerAdapter() {
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        super.write(ctx, msg, promise);
                        if (msg instanceof Packet)
                            MinecraftForge.EVENT_BUS.post(new PacketEvent.Outgoing((Packet<?>) msg));
                    }
                })
                .build());
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        // Create the instance
        LocrawManager.getInstance();
        // Pre-Release Notice
        NotificationManager.INSTANCE.push("XanderLib", "XanderLib is not yet completed and should not be used in production mods.", () -> {
            NotificationManager.INSTANCE.push("Follow up", "Notification toasts are also clickable!", null);
        });
    }

}
