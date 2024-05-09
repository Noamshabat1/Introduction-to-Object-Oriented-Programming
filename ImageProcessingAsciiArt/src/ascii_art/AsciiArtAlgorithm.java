package ascii_art;

import image.SubImage;
import image.calcSubImageBrightness;
import image_char_matching.SubImgCharMatcher;

import java.awt.*;

/**
 * This class used by the shell class to create an Ascii image.
 *
 * @author Noam Shabat and Samuel Hayat
 * @version Java 11
 */
public class AsciiArtAlgorithm {

    // the image that we want to convert to ascii art
    private final Color[][] image;
    // the charMatcher that we are using to convert from brightness to chars
    private final SubImgCharMatcher charMatcher;
    // the resolution of the subImages or in other words the number of chars per row
    private final int resolution;

    /**
     * This constructor will initialize the image, the charMatcher and the resolution.
     *
     * @param image       the image that we want to convert to ascii art
     * @param charMatcher the charMatcher that we are using to convert from brightness to chars
     * @param resolution  the resolution of the subImages
     */
    public AsciiArtAlgorithm(Color[][] image, SubImgCharMatcher charMatcher, int resolution) {
        this.image = image;
        this.charMatcher = charMatcher;
        this.resolution = resolution;
    }

    /**
     * This function is in charge of transforming the image into an ascii image
     *
     * @return a 2D char array that represents the ascii image
     */
    public char[][] run() {

        int width = image.length;
        int height = image[0].length;
        int subImageSize = width / resolution;
        int newHeight = height / subImageSize;

        SubImage subImage = new SubImage(image, resolution);
        Color[][][] subImages = subImage.getSubImages();

        calcSubImageBrightness calcSubImageBrightness = new calcSubImageBrightness(subImages);

        double[] imageBrightness = calcSubImageBrightness.getSubImageBrightness();

        return convertImageToAsciiChars(imageBrightness, newHeight);
    }

    /**
     * A function that takes an array of subImages brightness and convert it into an array of characters
     * based on the brightness of the sub images
     *
     * @param imageBrightness The array of sub image brightness
     * @return a 2D char array that represents the ascii image
     */
    private char[][] convertImageToAsciiChars(double[] imageBrightness, int newHeight) {
        char[][] asciiImage = new char[newHeight][resolution];

        for (int row = 0; row < newHeight; row++) {
            for (int col = 0; col < resolution; col++) {
                int brightnessIndex = row * newHeight + col; // Corrected from numberOfRows to next
                if (brightnessIndex < imageBrightness.length) {
                    asciiImage[row][col] =
                            charMatcher.getCharByImageBrightness(imageBrightness[brightnessIndex]);
                }
            }
        }
        return asciiImage;
    }
}