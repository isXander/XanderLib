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

package dev.isxander.xanderlib.utils;

import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public final class MouseUtils implements Constants {

    public static int getMouseX() {
        ScaledResolution res = new ScaledResolution(mc);
        return Mouse.getX() * res.getScaledWidth() / mc.displayWidth;
    }

    public static int getMouseY() {
        ScaledResolution res = new ScaledResolution(mc);
        return res.getScaledHeight() - Mouse.getY() * res.getScaledHeight() / mc.displayHeight - 1;
    }

    public static boolean isMouseDown() {
        return Mouse.getEventButtonState();
    }

}
