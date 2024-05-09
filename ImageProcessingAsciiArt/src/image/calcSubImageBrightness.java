package image;

import java.awt.*;

/**
 * This class is in charge of calculating the brightness of the subImages.
 * The brightness of the subImages is calculated by averaging the brightness of all the pixels in the
 * subImage.
 * The brightness of a pixel is calculated by the following formula:
 * brightness = 0.2126 * red + 0.7152 * green + 0.0722 * blue
 * The average brightness of the subImages is calculated by the following formula:
 * averageBrightness = (sumBrightness / (subImageWidth * subImageHeight)) / MAX_RGB
 * Where sumBrightness is the sum of the brightness of all the pixels in the subImage,  subImageWidth
 * is the width of the subImage,
 * subImageHeight is the height of the subImage and MAX_RGB is the maximum value of a color channel.
 * The brightness of the subImages is stored in an array.
 * The array is initialized with the average
 *
 * @author Noam Shabat and Samuel Hayat
 * @version Java 11
 */

public class calcSubImageBrightness {
    private static final int MAX_RGB = 255;
    private final Color[][][] subImages;
    private final double[] greySubImages;


    /**
     * This constructor will initialize the subImages that we want to calculate the brightness of.
     *
     * @param subImages the subImages that we want to calculate the brightness of them.
     */
    public calcSubImageBrightness(Color[][][] subImages) {
        this.subImages = subImages;
        this.greySubImages = new double[subImages.length];
        initializeGreySubImages();
    }

    /**
     * This method will initialize the greySubImages array with the average brightness of the subImages.
     */
    private void initializeGreySubImages() {
        for (int i = 0; i < subImages.length; i++) {
            double sumBrightness = 0;
            for (int j = 0; j < subImages[i].length; j++) {
                for (int k = 0; k < subImages[i][j].length; k++) {
                    sumBrightness += greyPixel(subImages[i][j][k]);
                }
            }
            greySubImages[i] = getSumAverageBrightness(sumBrightness);
        }
    }

    /**
     * This method will return the brightness of a gray-colored version of the giving pixel.
     *
     * @param color the color of the pixel.
     * @return the brightness of the pixel.
     */
    private double greyPixel(Color color) {
        return color.getRed() * 0.2126 + color.getGreen() * 0.7152 + color.getBlue() * 0.0722;
    }

    /**
     * This method will return the average brightness of the subImages.
     *
     * @param sumBrightness the sum of the brightness of the subImages.
     * @return the average brightness of the subImages.
     */
    private double getSumAverageBrightness(double sumBrightness) {
        return (sumBrightness / (subImages[0].length * subImages[0][0].length)) / MAX_RGB;
    }

    /**
     * This method will return the brightness of the subImages.
     *
     * @return the brightness of the subImages.
     */
    public double[] getSubImageBrightness() {
        return greySubImages;
    }
}
