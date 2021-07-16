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

package dev.isxander.xanderlib.ui.editor;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.util.List;

public interface AbstractGuiModifier {

    default void onInitGuiPre(GuiScreen screen, List<GuiButton> buttonList) { }
    default void onInitGuiPost(GuiScreen screen, List<GuiButton> buttonList) { }

    default void onActionPerformedPre(GuiScreen screen, List<GuiButton> buttonList, GuiButton button) { }
    default void onActionPerformedPost(GuiScreen screen, List<GuiButton> buttonList, GuiButton button) { }

    default void onDrawScreenPre(GuiScreen screen, int mouseX, int mouseY, float partialTicks) { }
    default void onDrawScreenPost(GuiScreen screen, int mouseX, int mouseY, float partialTicks) { }

    default void onBackgroundDraw(GuiScreen screen, int mouseX, int mouseY) { }

    default void onKeyboardInputPre(GuiScreen screen) { }
    default void onKeyboardInputPost(GuiScreen screen) { }

    default void onMouseInputPre(GuiScreen screen) { }
    default void onMouseInputPost(GuiScreen screen) { }

}
