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

import net.minecraft.util.MathHelper;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public final class ImageUtils {

    /**
     * Rotates an image 180 degrees to appear upside-down
     *
     * @deprecated This method does exactly the same thing as rotate(img, 180)
     * @param image image to flip
     * @return flipped image
     */
    @Deprecated
    public static BufferedImage flipVertically(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < newImage.getWidth(); x++) {
            for (int y1 = 0, y2 = newImage.getHeight() - 1; y1 < newImage.getHeight(); y1++, y2--) {
                newImage.setRGB(x, y1, image.getRGB(x, y2));
            }
        }
        return newImage;
    }

    /**
     * Mirrors an image on the horizontal axis
     *
     * @param image before image
     * @return after image
     */
    public static BufferedImage mirror(BufferedImage image) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int y = 0; y < newImage.getHeight(); y++) {
            for (int x1 = 0, x2 = newImage.getWidth() - 1; x1 < newImage.getWidth(); x1++, x2--) {
                newImage.setRGB(x1, y, image.getRGB(x2, y));
            }
        }
        return newImage;
    }

    /**
     * Rotates an image by x degrees using desired interpolation
     *
     * @param image before image
     * @param degrees amount of degrees to rotate
     * @param interpolationType the type of interpolation to be used when rotating {@link AffineTransformOp}
     * @return rotated image
     */
    public static BufferedImage rotate(BufferedImage image, int degrees, int interpolationType) {
        float rads = (float)Math.toRadians(degrees);
        float sin = MathHelper.abs(MathHelper.sin(rads));
        float cos = MathHelper.abs(MathHelper.cos(rads));
        int w = MathHelper.floor_float(image.getWidth() * cos + image.getHeight() * sin);
        int h = MathHelper.floor_float(image.getHeight() * cos + image.getWidth() * sin);
        BufferedImage rotated = new BufferedImage(w, h, image.getType());
        AffineTransform at = new AffineTransform();
        at.translate(w / 2f, h / 2f);
        at.rotate(rads, 0, 0);
        at.translate(-image.getWidth() / 2f, -image.getHeight() / 2f);
        AffineTransformOp rotateOp = new AffineTransformOp(at, interpolationType);
        return rotateOp.filter(image, rotated);
    }

    /**
     * Rotates an image by x degrees using Bilinear interpolation
     *
     * @param image before image
     * @param degrees amount of degrees to rotate
     * @return rotated image
     */
    public static BufferedImage rotate(BufferedImage image, int degrees) {
        return rotate(image, degrees, AffineTransformOp.TYPE_BILINEAR);
    }

}
