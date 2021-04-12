/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib.notification;

import co.uk.isxander.xanderlib.utils.Constants;
import co.uk.isxander.xanderlib.utils.MathUtils;
import co.uk.isxander.xanderlib.utils.MouseUtils;
import co.uk.isxander.xanderlib.utils.StringUtils;
import net.apolloclient.utils.GLRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager implements Constants {

    private static final int TOAST_WIDTH = 200;
    private static final int TOAST_PADDING_WIDTH = 5;
    private static final int TOAST_PADDING_HEIGHT = 3;
    private static final int TOAST_TEXT_DISTANCE = 2;

    public static final NotificationManager INSTANCE = new NotificationManager();

    private final List<Notification> currentNotifications = new ArrayList<>();

    private NotificationManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void push(String title, String description, Runnable runnable) {
        if (title == null || description == null)
            throw new NullPointerException("Title or Description is null.");
        currentNotifications.add(new Notification(title, description, runnable));
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (event.phase != TickEvent.Phase.END)
            return;

        ScaledResolution res = new ScaledResolution(mc);
        if (currentNotifications.size() == 0)
            return;
        Notification notification = currentNotifications.get(0);

        float time = notification.time;
        float opacity = 200;

        if (time <= 1 || time >= 10) {
            float easeTime = Math.min(time, 1);
            opacity = easeTime * 200;
        }
        List<String> wrappedTitle = StringUtils.wrapTextLines(EnumChatFormatting.BOLD + notification.title, mc.fontRendererObj, TOAST_WIDTH, " ");
        List<String> wrappedText = StringUtils.wrapTextLines(notification.description, mc.fontRendererObj, TOAST_WIDTH, " ");
        int textLines = wrappedText.size() + wrappedTitle.size();
        float rectWidth = notification.width = MathUtils.lerp(notification.width, TOAST_WIDTH + (TOAST_PADDING_WIDTH * 2), event.renderTickTime / 4f);
        float rectHeight = (TOAST_PADDING_HEIGHT * 2) + (textLines * mc.fontRendererObj.FONT_HEIGHT) + ((textLines - 1) * TOAST_TEXT_DISTANCE);
        float rectX = res.getScaledWidth() / 2f - (rectWidth / 2f);
        float rectY = 5;

        float mouseX = MouseUtils.getMouseX();
        float mouseY = MouseUtils.getMouseY();
        boolean mouseOver = mouseX >= rectX && mouseX <= rectX + rectWidth && mouseY >= rectY && mouseY <= rectY + rectHeight;
        if (notification.runnable != null && mouseOver && MouseUtils.isMouseDown()) {
            notification.runnable.run();
            notification.closing = true;
            if (notification.time > 1f)
                notification.time = 1f;
        }

        opacity += notification.mouseOverAdd = MathUtils.lerp(notification.mouseOverAdd, (mouseOver ? 40 : 0), event.renderTickTime / 4f);

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GLRenderer.drawRectangle(rectX, rectY, rectWidth, rectHeight, new Color(0, 0, 0, (int)MathUtils.clamp(opacity, 5, 255)));
        if (notification.time > 0.1f) {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GLRenderer.glScissor(rectX, rectY, rectWidth, rectHeight);
            int color = new Color(255, 255, 255, (int)MathUtils.clamp(opacity, 2, 255)).getRGB();
            int i = 0;
            for (String line : wrappedTitle) {
                mc.fontRendererObj.drawString(EnumChatFormatting.BOLD + line, res.getScaledWidth() / 2f - (mc.fontRendererObj.getStringWidth(line) / 2f), rectY + TOAST_PADDING_HEIGHT + (TOAST_TEXT_DISTANCE * i) + (mc.fontRendererObj.FONT_HEIGHT * i), color, true);
                i++;
            }
            for (String line : wrappedText) {
                mc.fontRendererObj.drawString(line, res.getScaledWidth() / 2f - (mc.fontRendererObj.getStringWidth(line) / 2f), rectY + TOAST_PADDING_HEIGHT + (TOAST_TEXT_DISTANCE * i) + (mc.fontRendererObj.FONT_HEIGHT * i), color, false);
                i++;
            }
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        }

        GlStateManager.popMatrix();
        if (notification.time >= 3f) {
            notification.closing = true;
        }
        notification.time += (notification.closing ? -0.02f : 0.02f) * (event.renderTickTime * 3f);
        if (notification.closing && notification.time <= 0)
            currentNotifications.remove(notification);
    }

}
