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

import co.uk.isxander.xanderlib.XanderLib;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.*;

// FIXME: 14/04/2021 make it work
public class GuiUtils {

    public static final GuiUtils INSTANCE = new GuiUtils();

    private final Map<Class<? extends GuiScreen>, ArrayList<GuiButton>> customButtons;

    private GuiUtils() {
        this.customButtons = new HashMap<>();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addButton(Class<? extends GuiScreen> guiClass, GuiButton... buttons) {
        customButtons.putIfAbsent(guiClass, new ArrayList<>());
        customButtons.get(guiClass).addAll(Arrays.asList(buttons));
    }

    public void removeButton(Class<? extends GuiScreen> guiClass, GuiButton... buttons) {
        if (customButtons.containsKey(guiClass)) {
            customButtons.get(guiClass).removeAll(Arrays.asList(buttons));
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        System.out.println(event.gui.getClass().getName());
        List<GuiButton> buttons = customButtons.get(event.gui.getClass());
        if (buttons != null) {
            try {
                Class<GuiScreen> screen = getGuiScreenSuperclass(event.gui.getClass());
                Field buttonListField = ReflectionHelper.findField(screen, "buttonList", "field_146292_n");
                buttonListField.setAccessible(true);
                List<GuiButton> buttonList = (List<GuiButton>) buttonListField.get(event.gui);
                XanderLib.LOGGER.info("Injected buttons to '" + event.gui.getClass().getSimpleName() + "' -> " + buttons);
                buttonList.addAll(buttons);
                buttonListField.set(event.gui, buttonList);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static Class<GuiScreen> getGuiScreenSuperclass(Class<? extends GuiScreen> clazz) {
        Class<?> screen = clazz;
        while (!screen.getName().equals(GuiScreen.class.getName())) {
            screen = screen.getSuperclass();
        }
        return (Class<GuiScreen>) screen;
    }

}
