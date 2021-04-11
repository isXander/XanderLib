package co.uk.isxander.xanderlib.utils;

import net.minecraft.util.EnumChatFormatting;

import java.util.Arrays;

public final class InlineFormatter {

    public static String get(String text, int stringLength, int maxLength, String cutoffString) {
        int colorCount = 0;
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == '\u00a7' && "0123456789abcdefklmnor".contains(Character.toString(chars[i + 1]).toLowerCase())) {
                colorCount++;
                // Skip the color code because we already checked
                i++;
            }
        }

        stringLength += colorCount * 2;
        maxLength += colorCount * 2;

        char[] empty = new char[maxLength];
        Arrays.fill(empty, ' ');

        for (int i = 0; i < empty.length; i++) {
            if (i >= stringLength - cutoffString.length()) {
                for (int j = 0; j < cutoffString.length(); j++) {
                    empty[i + j] = cutoffString.charAt(j);
                }
                break;
            }
            if (i >= chars.length) {
                break;
            }
            empty[i] = chars[i];
        }

        return new String(empty);
    }

}
