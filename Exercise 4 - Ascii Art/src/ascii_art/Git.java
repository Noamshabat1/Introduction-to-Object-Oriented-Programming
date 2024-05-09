package ascii_art;

import java.util.HashMap;
import java.util.HashSet;

/**
 * A class that is used as a save to gain runtime and avoid unnecessary operations.
 * It's based on the principe of momentum that we learned in class.
 * We choose this name because its funny, and it explains quit well how it is supposed to work
 *
 * @author Noam Shabat and Samuel Hayat
 * @version Java 11
 */
public class Git {
    // The map that stores the partial brightness value of a character
    private final HashMap<String, SaveImage> saveMap = new HashMap<>();

    // The map that stores the asciiArt images already created based on their names
    private final HashMap<Character, Double> savedPartialBrightnessMap = new HashMap<>();

    /**
     * A private class that is used to store the images in the database
     */
    private static class SaveImage {
        private final String fileName;
        private final int resolution;
        private final HashSet<Character> charSet = new HashSet<>();
        private final char[][] image;

        /**
         * A constructor that initializes the image with the given parameters
         *
         * @param fileName The name of the file
         * @param intValue The resolution of the image
         * @param charSet  The set of characters that are allowed to be used in the image
         * @param image    The image that we want to save
         */
        public SaveImage(String fileName, int intValue, HashSet<Character> charSet, char[][] image) {
            this.fileName = fileName;
            this.resolution = intValue;
            this.copyCharacter(charSet);
            this.image = image;
        }

        /**
         * A function that copies the characters from the given set to the current set
         *
         * @param newCharSet The set of characters that we want to copy
         */
        private void copyCharacter(HashSet<Character> newCharSet) {
            this.charSet.addAll(newCharSet);
        }

        // Getters

        /**
         * A function that returns the name of the file
         *
         * @return The name of the file
         */
        public String getStringValue() {
            return fileName;
        }

        /**
         * A function that returns the resolution of the image
         *
         * @return The resolution of the image
         */
        public int getResolution() {
            return resolution;
        }

        /**
         * A function that returns the set of characters that are allowed to be used in the image
         *
         * @return The set of characters that are allowed to be used in the image
         */
        public HashSet<Character> getAllowedCharacters() {
            return charSet;
        }

        /**
         * A function that returns the image
         *
         * @return The image
         */
        public char[][] getImage() {
            return image;
        }
    }

    /**
     * This function is in charge to save a character and his brightness to the database
     *
     * @param character  The character we want to save
     * @param brightness The brightness value of the character
     */
    public void saveCharacterBrightness(char character, double brightness) {
        savedPartialBrightnessMap.put(character, brightness);
    }

    /**
     * This function returns the brightness value of a character.
     *
     * @param character The character we want to the brightness of
     * @return The Brightness value of the character
     */
    public double restoreCharBrightness(char character) {
        return savedPartialBrightnessMap.get(character);
    }

    /**
     * A function that saves an image to the database
     *
     * @param fileName          The name of the image
     * @param resolution        The resolution of the image
     * @param allowedCharacters The set of characters that are allowed to be used in the image
     * @param output            The image that we want to save
     */
    public void saveImageData(String fileName, int resolution, HashSet<Character> allowedCharacters,
                              char[][] output) {
        SaveImage saveImage = new SaveImage(fileName, resolution, allowedCharacters, output);
        saveMap.put(fileName, saveImage);
    }

    /**
     * Retrieves an ascii image from the database.
     * This function assumes that you checked if the designed image is already in the database
     *
     * @param saveName          The name of the image
     * @param resolution        The resolution of the image
     * @param allowedCharacters The set of characters that are allowed to be used in the image
     * @return the image associated with the given name
     */
    public char[][] restoreImage(String saveName, int resolution, HashSet<Character> allowedCharacters) {

        for (var save : saveMap.values()) {
            if (save.getStringValue().equals(saveName) && save.getResolution() == resolution
                    && compareSets(allowedCharacters, save.getAllowedCharacters())) {
                return save.getImage();
            }
        }
        return null;
    }

    /**
     * A function that compares two images. It returns true if the image is already in the database
     *
     * @param fileName          The name of the image.
     * @param resolution        The resolution of the image.
     * @param allowedCharacters The set of characters that are allowed to be used in the image.
     * @return True if the image is already in the database, otherwise False
     */
    public boolean containsImage(String fileName, int resolution, HashSet<Character> allowedCharacters) {
        for (SaveImage save : saveMap.values()) {
            // Assuming CustomData has a method getAllowedCharacters() to return the HashSet<Character>
            if (save.getStringValue().equals(fileName) && save.getResolution() == resolution
                    && compareSets(allowedCharacters, save.getAllowedCharacters())) {
                return true; // Found a match, return true immediately
            }
        }
        return false; // No match found after looping, return false
    }

    /**
     * A function that compares two sets of characters
     *
     * @param set1 The first set of characters
     * @param set2 The second set of characters
     * @return True if the sets are equal, otherwise False
     */
    private boolean compareSets(HashSet<Character> set1, HashSet<Character> set2) {
        if (set1.size() != set2.size()) {
            return false;
        }

        for (char s : set1) {
            if (!set2.contains(s)) {
                return false;
            }
        }
        return true;
    }
}


