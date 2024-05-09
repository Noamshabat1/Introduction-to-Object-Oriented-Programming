package image;

import java.awt.*;

/**
 * This class is in charge of padding the image to the next power of two.
 * It will fill the new pixels with the original image and the rest with the default color.
 *
 * @author Noam Shabat and Samuel Hayat
 * @version Java 11
 */
public class PaddingImage {
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private final Color[][] pixels;
    private final Image originalImage;
    private final int width;
    private final int height;
    private final int originalWidth;
    private final int originalHeight;

    /**
     * This constructor will initialize the new padded image with the new width and height from the
     * original image.
     *
     * @param originalImage the original image that we want to padded.
     */
    public PaddingImage(Image originalImage) {
        this.originalImage = originalImage;
        this.originalWidth = originalImage.getWidth();
        this.originalHeight = originalImage.getHeight();

        this.width = calculateNextPowerOfTwo(originalWidth);
        this.height = calculateNextPowerOfTwo(originalHeight);

        this.pixels = new Color[this.height][this.width];
    }

    /**
     * This method will calculate the next power of two of a given number.
     *
     * @param number the number that we want to calculate the next power of two of.
     * @return the next power of two of the given number (2^x).
     */
    private int calculateNextPowerOfTwo(int number) {
        return (int) Math.pow(2, Math.ceil(Math.log(number) / Math.log(2))); // 2^(ciel(log(num)/log(2)))
    }

    /**
     * This method will fill the pixels of the new image with the original image and the rest with
     * the default color.
     *
     * @param originalWidth  the original width of the image.
     * @param originalHeight the original height of the image.
     * @return A new image that with the needed padding.
     */
    private Color[][] fillPixels(int originalWidth, int originalHeight) {
        int widthMargin = (this.width - originalWidth) / 2;
        int heightMargin = (this.height - originalHeight) / 2;


        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                if (isWithinMargin(row, col, heightMargin, widthMargin)) {
                    this.pixels[row][col] = DEFAULT_COLOR;
                } else {
                    int imageY = col - widthMargin;
                    int imageX = row - heightMargin;
                    this.pixels[row][col] = this.originalImage.getPixel(imageX, imageY);
                }
            }
        }
        return this.pixels;
    }

    /**
     * This method will determent if a pixel is in the new width or height of the image.
     *
     * @param row          the row of the cur pixel.
     * @param col          the col of the cur pixel.
     * @param heightMargin the margin of the height.
     * @param widthMargin  the margin of the width.
     * @return true if the pixel is in the margin, false otherwise (in the new width or height).
     */
    private boolean isWithinMargin(int row, int col, int heightMargin, int widthMargin) {
        return row < heightMargin || row >= (this.height - heightMargin) ||
                col < widthMargin || col >= (this.width - widthMargin);
    }


    /**
     * This method will return the new width of the image
     *
     * @return the new width of the image
     */
    public int getNewWidth() {
        return this.width;
    }

    /**
     * This method will return the new height of the image
     *
     * @return the new height of the image
     */
    public int getNewHeight() {
        return this.height;
    }

    /**
     * This method is the method responsible to activate the class and creat the padded image save.
     *
     * @return the padded image
     */
    public Color[][] getPaddedImage() {
        return fillPixels(originalWidth, originalHeight);
    }
}