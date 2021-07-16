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

import com.google.common.base.Joiner;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class StringUtils {

    /**
     * Shifts a string a certain number of places
     *
     * @param s string to shift
     * @param amt amount of places to shift
     */
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

    /**
     * Formats bedwars star level into Hypixel's format
     */
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

    /**
     * @param str string to check
     * @param times how many times the character has to appear in a string before it is counted as a duplicate
     * @return the amount of duplicate characters in a string
     */
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

    /**
     * Wraps text according to the character length per line
     *
     * This function is lenient as each line can go above the maximum
     * character length in order to complete a word
     *
     * @param text text to wrap
     * @param charLength the desired character length of each line
     */
    public static String wrapTextLenient(String text, int charLength) {
        StringBuilder sb = new StringBuilder();
        int lineLength = 0;
        boolean needsLineBreak = false;
        for (char c : text.toCharArray()) {
            lineLength += 1;
            if (c == '\n') lineLength = 0;
            if (lineLength > charLength) {
                needsLineBreak = true;
            }
            if (needsLineBreak && c == ' ') {
                lineLength = 0;
                sb.append('\n');
                needsLineBreak = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * Wraps text according to the font renderer's string width
     *
     * @param text text to wrap
     * @param fontRenderer font renderer that measures string width
     * @param lineWidth maximum line width
     * @param split word splitter
     * @return wrapped text
     */
    public static String wrapTextFR(String text, FontRenderer fontRenderer, int lineWidth, String split) {
        // split with line ending too
        String[] words = text.split("(" + split + "|\n)");
        // current line width
        int lineLength = 0;
        // string concatenation in loop is bad
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            // add a the word splitter after the word every time except the last word
            if (i != words.length - 1) {
                word += split;
            }

            // length of next word
            int wordLength = fontRenderer.getStringWidth(word);

            if (lineLength + wordLength <= lineWidth) { // if the current line length plus this next word is less than the maximum line width
                // if the condition is met, we can just append the word to the current line as it is small enough
                output.append(word);
                lineLength += wordLength;
            } else if (wordLength <= lineWidth) { // the word is not big enough to be larger than the whole line max width
                // make a new line before adding the word
                output.append("\n").append(word);
                // the next line has just been made and has been populated with only one word. reset line length and add the word we just added
                lineLength = wordLength;
            } else {
                // the single word will not fit so run the function again with just this word
                // and tell it that every character is it's own word
                output.append(wrapTextFR(word, fontRenderer, lineWidth, "")).append(split);
            }
        }

        return output.toString();
    }

    /**
     * Wraps text using font renderer and adds each line to a list
     *
     * @see StringUtils#wrapTextLinesFR(String, FontRenderer, int, String)
     */
    public static List<String> wrapTextLinesFR(String text, FontRenderer fontRenderer, int lineWidth, String split) {
        String wrapped = wrapTextFR(text, fontRenderer, lineWidth, split);
        if (wrapped.equals("")) {
            return new ArrayList<>();
        }

        return Arrays.asList(wrapped.split("\n"));
    }

    /**
     * Counts how many times text appears in a string
     *
     * @param text string to check
     * @param toCheck what to check for
     */
    public static int count(String text, String toCheck) {
        int count = 0;

        for (int i = 0; i < text.length(); i++) {
            if (text.substring(i).startsWith(toCheck)) {
                count++;
                i += toCheck.length() - 1;
            }
        }

        return count;
    }

    /**
     * Makes the first character of a string upper-case
     *
     * @param original original text
     */
    public static String firstUpper(String original) {
        if (original.length() == 1) return original.toUpperCase();
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();
    }

    /**
     * Repeats text some amount of times
     * Backported from later Java versions
     *
     * @param text text to repeat
     * @param amount amount of times to repeat
     */
    public static String repeat(String text, int amount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= amount; i++) {
            sb.append(text);
        }

        return sb.toString();
    }

    /**
     * Converts 'SOME_ENUM' to 'Some Enum'
     */
    public static String capitalizeEnum(String in) {
        String out = in.toLowerCase();
        boolean lastSpace = true;
        List<String> chars = new ArrayList<>();
        for (char c : out.toCharArray()) {
            chars.add(c + "");
        }

        for (int i = 0; i < chars.size(); i++) {
            String c = chars.get(i);
            if (lastSpace) {
                chars.set(i, c.toUpperCase());
            }

            lastSpace = c.equals(" ");
        }

        StringBuilder sb = new StringBuilder();
        for (String s : chars)
            sb.append(s);

        return sb.toString();
    }

}
