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

package co.uk.isxander.xanderlib.utils;

import net.minecraft.client.gui.FontRenderer;

import java.awt.Color;
import java.util.List;

public final class GuiUtils implements Constants {

    public static void drawCenteredString(FontRenderer fontRendererIn, String text, float x, float y, int color, boolean shadow) {
        fontRendererIn.drawString(text, x - fontRendererIn.getStringWidth(text) / 2f, y, color, shadow);
    }

    public static void drawChromaString(FontRenderer fontRendererIn, String text, float x, float y, boolean shadow, boolean centered) {
        if (centered)
            x -= fontRendererIn.getStringWidth(text) / 2f;

        for (char c : text.toCharArray()) {
            int i = getChroma(x, y).getRGB();
            String tmp = String.valueOf(c);
           fontRendererIn.drawString(tmp, x, y, i, shadow);
            x += fontRendererIn.getStringWidth(tmp);
        }
    }

    public static void drawWrappedCenteredString(FontRenderer fontRendererIn, String text, float x, float y, int color, boolean shadow, int width) {
        List<String> lines = StringUtils.wrapTextLines(text, fontRendererIn, width, " ");
        int i = 0;
        for (String line : lines) {
            GuiUtils.drawCenteredString(fontRendererIn, line, x, y + (fontRendererIn.FONT_HEIGHT * i) + (2 * i), color, shadow);
            i++;
        }
    }

    public static void drawWrappedChromaString(FontRenderer fontRendererIn, String text, float x, float y, boolean shadow, int width, boolean centered) {
        List<String> lines = StringUtils.wrapTextLines(text, fontRendererIn, width, " ");
        int i = 0;
        for (String line : lines) {
            GuiUtils.drawChromaString(fontRendererIn, line, x, y + (fontRendererIn.FONT_HEIGHT * i) + (2 * i), shadow, centered);
            i++;
        }
    }

    public static Color getChroma(double x, double y) {
        float v = 2000.0f;
        return new Color(Color.HSBtoRGB((float)((System.currentTimeMillis() - x * 10.0 * 1.0 - y * 10.0 * 1.0) % v) / v, 0.8f, 0.8f));
    }

}
