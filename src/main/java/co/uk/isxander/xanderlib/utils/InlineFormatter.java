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

import java.util.Arrays;

public final class InlineFormatter {

    /**
     * Warning: only works well with monospaced font
     *
     * @param text the text to format
     * @param stringLength the max text length
     * @param maxLength the max length of the output string
     * @param cutoffString cuts off the text if it is above the string length and appends this string
     * @return a string that is a specified length long, populated with whitespaces if the input text is smaller.
     */
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
