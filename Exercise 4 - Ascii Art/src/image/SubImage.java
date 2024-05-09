package image;

import java.awt.*;

/**
 * This class is in charge of extracting the subImages from the image.
 * The subImages are extracted by dividing the image into subImages of the same size.
 * The subImages are stored in a 3D array.
 *
 * @author Noam Shabat and Samuel Hayat
 * @version Java 11
 */
public class SubImage {
    private final Color[][] paddedImage;
    private final int subImagesPerRow;
    private final int subImagesPerCol;
    private final int subImageSize;


    /**
     * This constructor will initialize the paddedImage and the resolution of the subImages.
     *
     * @param paddedImage the image that we want to extract the subImages from it.
     * @param resolution  the resolution of the subImages or in other words the number of subImages per row.
     */
    public SubImage(Color[][] paddedImage, int resolution) {
        this.paddedImage = paddedImage;
        this.subImageSize = paddedImage[0].length / resolution;
        this.subImagesPerRow = paddedImage.length / subImageSize;
        this.subImagesPerCol = paddedImage[0].length / subImageSize;
    }

    /**
     * This method will return the subImages of the image.
     *
     * @return the subImages of the image.
     */
    public Color[][][] getSubImages() {
        Color[][][] subImages =
                new Color[this.subImagesPerRow * this.subImagesPerCol][this.subImageSize][this.subImageSize];

        // Iterate over each sub-image's starting point
        for (int row = 0; row < this.subImagesPerRow; row++) {
            for (int col = 0; col < this.subImagesPerCol; col++) {
                // Calculate the index for storing the sub-image
                int index = row * this.subImagesPerCol + col;
                // Extract the sub-image
                subImages[index] = extractSubImage(this.paddedImage, row * this.subImageSize,
                        col * this.subImageSize, this.subImageSize);
            }
        }
        return subImages;
    }

    /**
     * This method will extract a sub-image from the image.
     *
     * @param image        the image that we want to extract the sub-image from it.
     * @param startX       the x coordinate of the starting point of the sub-image.
     * @param startY       the y coordinate of the starting point of the sub-image.
     * @param subImageSize the size of the sub-image.
     * @return the sub-image.
     */
    private Color[][] extractSubImage(Color[][] image, int startX, int startY, int subImageSize) {
        Color[][] subImage = new Color[subImageSize][subImageSize];
        for (int i = 0; i < subImageSize; i++) {
            for (int j = 0; j < subImageSize; j++) {
                // Map sub-image indices to original image indices
                int x = startX + i;
                int y = startY + j;
                subImage[i][j] = image[x][y];
            }
        }
        return subImage;
    }
}