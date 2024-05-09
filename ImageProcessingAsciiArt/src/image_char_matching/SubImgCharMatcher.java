package image_char_matching;

import ascii_art.Git;
import java.lang.Math;
import java.util.*;

/**
 * A class that is used to match a character to a brightness
 * It's based on the principle of momentum that we learned in class.
 * We choose this name because it's funny, and it explains quite well how it is supposed to work
 * It's used to gain runtime and avoid unnecessary operations.
 *
 * @author Noam Shabat and Samuel Hayat
 * @version Java 11
 */
public class SubImgCharMatcher {
    private final HashMap<Character, Double> fullBrightnessCharMap = new HashMap<>();
    private final Git git;
    private final PriorityQueue<Double> minBrightnessQueue =
            new PriorityQueue<>(Comparator.naturalOrder());
    private final PriorityQueue<Double> maxBrightnessQueue =
            new PriorityQueue<>(Comparator.reverseOrder());
    private final Set<Character> charSet;

    /**
     * A constructor that initializes the brightness of the characters and the charset.
     *
     * @param charSet the set of characters that will be used to initialize the brightness of the
     *                characters.
     */
//    public SubImgCharMatcher(Git git, char[] charSet) {
    public SubImgCharMatcher(char[] charSet) {
        this.git = new Git();
        this.calculateAllCharacterBrightness();

        this.charSet = new HashSet<>();
        for (Character character : charSet) {
            this.charSet.add(character);
            this.minBrightnessQueue.add(git.restoreCharBrightness(character));
            this.maxBrightnessQueue.add(git.restoreCharBrightness(character));
        }
        calculateAllCharacterFullBrightness();
    }


    /**
     * A function that calculates the full brightness of a character for all characters.
     */
    private void calculateAllCharacterFullBrightness() {
        for (char character : this.charSet) {
            fullBrightnessCharMap.put(character, calculateUniqueCharFullBrightness(character));
        }
    }

    /**
     * A function that calculates the partial brightness of a character for all characters.
     */
    private void calculateAllCharacterBrightness() {
        for (char ch = ' '; ch <= '~'; ch++) {
//            allCharsBrightnessCharMap.put(ch, calculateUniqueCharPartialBrightness(ch));
            git.saveCharacterBrightness(ch, calculateUniqueCharPartialBrightness(ch));
        }
    }

    /**
     * A function that returns the character that corresponds to the brightness of the image.
     *
     * @param brightness the brightness that will be used to find the character.
     * @return the character that corresponds to the brightness of the image.
     */
    public char getCharByImageBrightness(double brightness) {

        // Get a random char, which one is not important
        double minDiff = 2; // big maximum difference
        char champ = ' '; // just to initialize

        for (char character : charSet) {
            if (Math.abs(fullBrightnessCharMap.get(character) - brightness) < Math.abs(minDiff)) {
                champ = character;
                minDiff = Math.abs(fullBrightnessCharMap.get(character) - brightness);
            }
        }
        return champ ;
    }

    /**
     * A function that adds a character to the map and updates the min and max queues.
     *
     * @param character the character that will be added to the map.
     */
    public void addChar(char character) {
        double partialBrightness = git.restoreCharBrightness(character);
        this.charSet.add(character);

        if (minBrightnessQueue.isEmpty() || maxBrightnessQueue.isEmpty()){
            minBrightnessQueue.add(partialBrightness);
            maxBrightnessQueue.add(partialBrightness);
            calculateAllCharacterFullBrightness();
        }
        else if (partialBrightness < minBrightnessQueue.peek()) {
            minBrightnessQueue.add(partialBrightness);
            maxBrightnessQueue.add(partialBrightness);
            fullBrightnessCharMap.clear();
            calculateAllCharacterFullBrightness();

        } else if (partialBrightness > maxBrightnessQueue.peek()) {
            minBrightnessQueue.add(partialBrightness);
            maxBrightnessQueue.add(partialBrightness);
            fullBrightnessCharMap.clear();
            calculateAllCharacterFullBrightness();

        } else {
            minBrightnessQueue.add(partialBrightness);
            maxBrightnessQueue.add(partialBrightness);
            double fullBrightness = calculateUniqueCharFullBrightness(character);
            fullBrightnessCharMap.put(character, fullBrightness);
        }
    }

    /**
     * A function that removes a character from the map and updates the min and max queues.
     *
     * @param character the character that will be removed from the map.
     */
    public void removeChar(char character) {
        charSet.remove(character);
        double partialBrightness = git.restoreCharBrightness(character);
        if (partialBrightness == minBrightnessQueue.peek()) {
            fullBrightnessCharMap.clear();
            calculateAllCharacterFullBrightness();
        } else if (partialBrightness == maxBrightnessQueue.peek()) {
            fullBrightnessCharMap.clear();
            calculateAllCharacterFullBrightness();
        } else {
            fullBrightnessCharMap.remove(character);
        }
    }

    /**
     * A function that clears the map and the min and max queues.
     */
    public void clear() {
        this.charSet.clear();
        this.fullBrightnessCharMap.clear();
        this.minBrightnessQueue.clear();
        this.maxBrightnessQueue.clear();
    }

    /**
     * A function that calculates the partial brightness of a character for all characters.
     *
     * @param character the character that will be calculated for all characters.
     * @return the partial brightness of the character for all characters.
     */
    private double calculateUniqueCharPartialBrightness(char character) {
        //charImage is 16x16 array of bool
        boolean[][] charImage = CharConverter.convertToBoolArray(character);
        double sumTruePixels = 0;
        for (int i = 0; i < charImage.length; i++) {
            for (int j = 0; j < charImage[0].length; j++) {
                if (charImage[i][j]) {
                    sumTruePixels++;
                }
            }
        }
        return sumTruePixels / Math.pow(charImage.length, 2);
    }

    /**
     * A function that calculates the full brightness of a character for all characters.
     *
     * @param character the character that will be calculated for all characters.
     * @return the full brightness of the character for all characters.
     */
    private double calculateUniqueCharFullBrightness(char character) {
        return (git.restoreCharBrightness(character) - minBrightnessQueue.peek()) /
                (maxBrightnessQueue.peek() - minBrightnessQueue.peek());
    }
}