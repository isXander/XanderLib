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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public final class MathUtils {

    /**
     * Clamps value between 0 and 1 and returns value.
     *
     * @author isXander
     */
    public static float clamp01(float value) {
        if ((double)value < 0.0)
            return 0.0f;
        return (double)value > 1.0 ? 1f : value;
    }

    /**
     * Clamps value between min & max
     *
     * @param val value to clamp
     * @param min min value
     * @param max max value
     * @return clamped value
     * @author isXander
     */
    public static float clamp(float val, float min, float max) {
        if (val > max) val = max;
        else if (val < min) val = min;
        return val;
    }

    /**
     * Linearly interpolates between a and b by t.
     *
     * @param start Start value
     * @param end End value
     * @param interpolation Interpolation between two floats
     * @return interpolated value between a - b
     * @author isXander
     */
    public static float lerp(float start, float end, float interpolation) {
        return start + (end - start) * MathUtils.clamp01(interpolation);
    }

    /**
     * Returns number between 0 - 1 depending on the range and value given
     *
     * @param val the value
     * @param min minimum of what the value can be
     * @param max maximum of what the value can be
     * @return converted percentage
     * @author isXander
     */
    public static float getPercent(float val, float min, float max) {
        return (val - min) / (max - min);
    }

    /**
     * Returns the percentile of list of longs
     *
     * @param nums the list on which to calculate the percentile
     * @param percentile what percentile the calculation will output
     * @return the percentile of the nums
     * @author isXander
     */
    public static long percentile(List<Long> nums, double percentile) {
        Collections.sort(nums);
        int index = (int) Math.ceil(percentile / 100.0 * nums.size());
        return nums.get(index - 1);
    }

    /**
     * @param val value to change
     * @param places how many decimal places the number should have
     * @return x amount of places of precision of a value
     */
    public static float precision(float val, int places) {
        int mod = Math.max(places, 0);

        if (places(val) <= mod)
            return val;
        if (mod == 0)
            return (float) Math.round(val);

        return (float) Math.round(val * mod) / mod;
    }

    /**
     * @param val value to check
     * @return the number of decimal places in a number
     */
    public static int places(float val) {
        return BigDecimal.valueOf(val).scale();
    }

}
