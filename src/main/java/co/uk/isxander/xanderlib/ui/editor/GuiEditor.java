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

package co.uk.isxander.xanderlib.ui.editor;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.*;

public final class GuiEditor {

    private static GuiEditor INSTANCE = new GuiEditor();

    public final Map<Class<? extends GuiScreen>, ArrayList<AbstractCustomButton>> customButtons;

    private GuiEditor() {
        this.customButtons = new HashMap<>();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addButtons(Class<? extends GuiScreen> gui, AbstractCustomButton... buttons) {
        this.customButtons.putIfAbsent(gui, new ArrayList<>());
        this.customButtons.get(gui).addAll(Arrays.asList(buttons));
    }

    public void removeButtons(Class<? extends GuiScreen> gui, AbstractCustomButton... buttons) {
        List<AbstractCustomButton> b = customButtons.get(gui);
        if (b != null) {
            b.removeAll(Arrays.asList(buttons));
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onGuiInit(GuiScreenEvent.InitGuiEvent.Post event) {
        List<AbstractCustomButton> buttons = customButtons.get(event.gui.getClass());
        if (buttons != null) {
            for (AbstractCustomButton button : buttons) {
                event.buttonList.add(button.getButton());
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public void onActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event) {
        List<AbstractCustomButton> buttons = customButtons.get(event.gui.getClass());
        if (buttons != null) {
            for (AbstractCustomButton button : buttons) {
                if (event.button.equals(button.getButton())) {
                    button.onActionPerformed(event.gui);
                }
            }
        }
    }

    public static GuiEditor getInstance() {
        return INSTANCE;
    }

}
