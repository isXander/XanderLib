/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib.utils;

import com.google.common.base.Joiner;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public final class StringUtils {

    public String shiftString(String s, int amt) {
        List<Character> chars = new ArrayList<>();
        for (char c : s.toCharArray())
            chars.add(c);

        for (int i = 0; i < amt; i++) {
            Character c = chars.get(chars.size() - 1);
            chars.remove(chars.size() - 1);
            chars.add(0, c);
        }

        return Joiner.on("").join(chars);
    }

    public static String formatBedwarsStar(int stars) {
        if (stars < 100)
            return EnumChatFormatting.GRAY + "[" + stars + "\u272B]";
        else if (stars < 200)
            return EnumChatFormatting.WHITE + "[" + stars + "\u272B]";
        else if (stars < 300)
            return EnumChatFormatting.GOLD + "[" + stars + "\u272B]";
        else if (stars < 400)
            return EnumChatFormatting.AQUA + "[" + stars + "\u272B]";
        else if (stars < 500)
            return EnumChatFormatting.DARK_GREEN + "[" + stars + "\u272B]";
        else if (stars < 600)
            return EnumChatFormatting.DARK_AQUA + "[" + stars + "\u272B]";
        else if (stars < 700)
            return EnumChatFormatting.DARK_RED + "[" + stars + "\u272B]";
        else if (stars < 800)
            return EnumChatFormatting.LIGHT_PURPLE + "[" + stars + "\u272B]";
        else if (stars < 900)
            return EnumChatFormatting.BLUE + "[" + stars + "\u272B]";
        else if (stars < 1000)
            return EnumChatFormatting.DARK_PURPLE + "[" + stars + "\u272B]";
        else {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            boolean forwards = true;
            String colors = "c6eabd5";
            for (char c : ("[" + stars + "\u272B]").toCharArray()) {
                sb.append("\u00a7").append(colors.toCharArray()[i]).append(c);

                if (i >= colors.length() - 1)
                    forwards = false;
                else if (i < 1)
                    forwards = true;

                if (forwards)
                    i++;
                else
                    i--;
            }
            return sb.toString();
        }
    }

    public static int hasDuplicateCharacters(String str, int times) {
        int repeat = 0;
        int index = -1;
        Character lastChar = null;
        for (char c : str.toCharArray()) {
            index++;

            if (lastChar == null) {
                lastChar = c;
                continue;
            }

            if (lastChar == c) {
                repeat++;
            } else {
                repeat = 0;
            }

            if (repeat >= times) {
                return index;
            }

            lastChar = c;
        }

        return -1;
    }

}
