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
import net.minecraft.client.gui.ScaledResolution;

import java.awt.Color;
import java.util.List;

public final class GuiUtils implements Constants {

    public static final String[] COLOR_CODES = { "0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f" };

    public static void drawString(FontRenderer fontRendererIn, String text, float x, float y, boolean shadow, boolean bordered, boolean chroma, boolean centered, int color) {
        if (bordered) {
            drawBorderedString(fontRendererIn, text, x, y, centered, chroma, color);
        } else if (chroma) {
            drawChromaString(fontRendererIn, text, x, y, shadow, centered);
        } else if (centered) {
            drawCenteredString(fontRendererIn, text, x, y, color, shadow);
        } else {
            fontRendererIn.drawString(text, x, y, color, shadow);
        }
    }

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

    public static void drawWrappedString(FontRenderer fontRendererIn, String text, float x, float y, int color, boolean shadow, int width, boolean centered) {
        ScaledResolution res = new ScaledResolution(mc);
        List<String> lines = StringUtils.wrapTextLinesFR(text, fontRendererIn, width, " ");
        int i = 0;
        for (String line : lines) {
            float lineY = y + (fontRendererIn.FONT_HEIGHT * i) + (2 * i);
            if (centered) {
                GuiUtils.drawCenteredString(fontRendererIn, line, x, lineY, color, shadow);
            } else {
                fontRendererIn.drawString(line, x, lineY, color, shadow);
            }

            i++;
        }
    }

    public static void drawWrappedChromaString(FontRenderer fontRendererIn, String text, float x, float y, boolean shadow, int width, boolean centered) {
        List<String> lines = StringUtils.wrapTextLinesFR(text, fontRendererIn, width, " ");
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

    public static void drawBorderedString(FontRenderer fontRendererIn, String text, float x, float y, boolean centered, boolean chroma, int color) {
        String noColors = GuiUtils.stripColorFormattingCodes(text);

        drawString(fontRendererIn, noColors, x + 1, y, false, false, false, centered, (color >> 24) << 24);
        drawString(fontRendererIn, noColors, x - 1, y, false, false, false, centered, (color >> 24) << 24);
        drawString(fontRendererIn, noColors, x, y + 1, false, false, false, centered, (color >> 24) << 24);
        drawString(fontRendererIn, noColors, x, y - 1, false, false, false, centered, (color >> 24) << 24);
        drawString(fontRendererIn, text, x, y, false, false, chroma, centered, color);
    }

    public static String stripFormattingCodes(String text, String formatCode) {
        while (text.contains(formatCode)) {
            text = text.replace(formatCode + text.charAt(text.indexOf(formatCode) + 1), "");
        }

        return text;
    }

    public static String stripFormattingCodes(String text) {
        return stripFormattingCodes(text, "\u00A7");
    }

    public static String stripColorFormattingCodes(String text, String formatCode) {
        for (String code : COLOR_CODES) {
            text = text.replace(formatCode + code, formatCode + "r");
            text = text.replace((formatCode + code).toUpperCase(), formatCode + "r");
        }

        return text;
    }

    public static String stripColorFormattingCodes(String text) {
        return stripFormattingCodes(text, "\u00A7");
    }

}
