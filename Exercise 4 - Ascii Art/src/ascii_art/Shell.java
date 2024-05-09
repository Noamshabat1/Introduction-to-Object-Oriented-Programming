package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.PaddingImage;
import image_char_matching.SubImgCharMatcher;

import java.awt.*;
import java.io.IOException;
import java.util.HashSet;

/**
 * This class is used as the main entry of the program, She is in charge to set the
 * parameters and to run activate the ascii algorithm
 *
 * @author Noam Shabat and Samuel Hayat
 * @version Java 11
 */
public class Shell {
    // The prefix that is printed before an user input
    private static final String COMMAND_PREFIX = ">>> ";
    //User command for quitting
    private static final String EXIT_COMMAND = "exit";
    //User command for displaying chars
    private static final String CHARS_COMMAND = "chars";

    //User command for adding or remove character
    private static final String ADD_COMMAND = "add";
    private static final String ADD_ERROR_MESSAGE = "Did not add due to incorrect format.";
    private static final String REMOVE_COMMAND = "remove";
    private static final String REMOVE_ERROR_MESSAGE = "Did not remove due to incorrect format.";
    private static final String ALL_KEYWORD = "all";
    private static final String SPACE_KEYWORD = "space";

    //User command for starting the algorithm
    private static final String ASCII_ART_COMMAND = "asciiArt";
    private static final String NO_CHARS_LOADED_MESSAGE_ERROR = "Did not execute. Charset is empty.";

    //User command for changing the image
    private static final String IMAGE_COMMAND = "image";
    private static final String IMAGE_LOADING_ERROR_MESSAGE = "Did not execute due to problem with " +
            "image file.";

    // User commands for resolution
    private static final String RES_COMMAND = "res";
    private static final String UP_KEYWORD = "up";
    private static final String DOWN_KEYWORD = "down";
    private static final String RESOLUTION_CHANGE_MESSAGE = "Resolution set to ";
    private static final String WIDTH_CHANGE_ERROR_MESSAGE = "Did not change resolution due to " +
            "exceeding boundaries.";
    private static final String RES_ERROR_MESSAGE = "Did not change resolution due to incorrect " +
            "format.";

    // Users command for output changes
    private static final String OUTPUT_COMMAND = "output";
    private static final String CONSOLE_COMMAND = "console";
    private static final String HTML_COMMAND = "html";
    private static final String OUTPUT_ERROR_MESSAGE = "Did not change output method due to incorrect " +
            "format.";
    private static final String INCORRECT_COMMAND_MESSAGE = "Did not execute due to " +
            "incorrect command.";
    private static final int MAX_DEFAULT_CHAR_VALUE = 10;
    private static final String CAT_JPEG = "cat.jpeg";
    private boolean usingConsole = true;

    // Constants for character set operations
    private static final char MIN_CHAR_ASCII_VALUE = 32;
    private static final char MAX_CHAR_ASCII_VALUE = 127;
    private static final char INIT_CHARS_BEGINNING = '0';
    private static final char INIT_CHARS_ENDING = '9';

    // Default values for HTML output
    private static final String DEFAULT_FILE_NAME = "out.html";
    private static final String DEFAULT_FONT = "Courier New";
    private final char SPACE = ' ';


    private final Git git;
    private String fileName;
    private Color[][] currentImage;
    private final SubImgCharMatcher charMatcher;
    private AsciiOutput asciiOutput;

    // Image Parameters
    private final HashSet<Character> allowedCharacters = new HashSet<>();
    private int minCharsInRow;
    private int maxCharsInRow;
    private int resolution;
    private static final int DEFAULT_RESOLUTION = 128;


    /**
     * Constructor for the Shell class that initializes the git, the file name, and the resolution.
     */
    public Shell() {

        this.fileName = CAT_JPEG;

        Image image = null;
        try {
            image = new Image(fileName);

        } catch (IOException e) {
            System.err.println("Failed to open the image file: " + CAT_JPEG );
            System.err.println("Error details: " + e.getMessage());
        }

        this.git = new Git();

        resolution = DEFAULT_RESOLUTION;

        char[] charset = getIntRangeAsChars();
        charMatcher = new SubImgCharMatcher(charset);
        for (char c : charset) {
            allowedCharacters.add(c);
        }
        // Init the image
        initImage(image);

        //Init output method html or console
        this.asciiOutput = new ConsoleAsciiOutput();
    }

    /**
     * This method initializes the image and the resolution.
     * it is padding the image that it receives and prepare it to be executed by the asciiArt algorithm.
     *
     * @param image the image to be processed
     */
    private void initImage(Image image) {
        PaddingImage paddingImage = new PaddingImage(image);
        int width = paddingImage.getNewWidth();
        int height = paddingImage.getNewHeight();
        currentImage = paddingImage.getPaddedImage();

        // init resolution
        minCharsInRow = Math.max(1, width / height);
        maxCharsInRow = width;
    }

    /**
     * This method will return the range of characters from 0 to 9 in a list of chars
     *
     * @return An array of char with the range of 0 to 9
     */
    private char[] getIntRangeAsChars() {

        char[] charList = new char[MAX_DEFAULT_CHAR_VALUE];
        int counter = 0;
        for (char ch = INIT_CHARS_BEGINNING; ch <= INIT_CHARS_ENDING; ch++) {

            charList[counter] = ch;
            counter++;
        }
        return charList;
    }

    /**
     * This method will run the shell and process the user input.
     */
    public void run() {
        String command;
        while (true) {
            try {
                System.out.print(COMMAND_PREFIX);
                command = KeyboardInput.readLine();

                if (EXIT_COMMAND.equals(command)) {
                    return;
                }

                if (!command.isEmpty()) {
                    processCommand(command);
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(IMAGE_LOADING_ERROR_MESSAGE);
            }
        }
    }

    /**
     * This method will process the command that the user entered and execute the appropriate action.
     *
     * @param command the command that the user entered.
     */
    private void processCommand(String command) throws IllegalArgumentException, IOException {

        switch (command) {
            case CHARS_COMMAND:
                displayAllowedCharacters();
                break;

            case ASCII_ART_COMMAND:
                renderAsciiArt();
                break;

            default:
                handleExtendedCommands(command);
                break;
        }
    }

    /**
     * This method will display the allowed characters to the user.
     */
    private void displayAllowedCharacters() {
        allowedCharacters.forEach(character -> System.out.print(character + " "));
        System.out.println();
    }

    /**
     * This function is in charge to activate the asciiArt algorithm with the specified settings and
     * to display the processed image to the designed output.
     *
     * @throws IllegalStateException Throws an error in case this function is called with no character
     *                               loaded for.
     */
    private void renderAsciiArt() throws IllegalStateException {

        if (git.containsImage(fileName, resolution, allowedCharacters)) {
            asciiOutput.out(git.restoreImage(fileName, resolution, allowedCharacters));
            return;
        }

        if (allowedCharacters.isEmpty()) {
            throw new IllegalStateException(NO_CHARS_LOADED_MESSAGE_ERROR);
        }

        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(currentImage, charMatcher, resolution);
        char[][] out = asciiArtAlgorithm.run();
        git.saveImageData(fileName, resolution, allowedCharacters, out);
        asciiOutput.out(out);
    }

    /**
     * This method will output the ascii art to the console or to a html file respectfully to the
     * usingConsole value.
     */
    private void outputAscii() {
        if (usingConsole) {
            asciiOutput = new ConsoleAsciiOutput();
        } else {
            asciiOutput = new HtmlAsciiOutput(DEFAULT_FILE_NAME, DEFAULT_FONT);
        }
    }

    /**
     * This method will handle the extended commands that the user can enter.
     *
     * @param command the command that the user entered.
     */
    private void handleExtendedCommands(String command) throws IllegalArgumentException, IOException {
        String[] parts = command.split(" ");
        if (parts.length > 2 || parts.length == 0) {
            throw new IllegalArgumentException(INCORRECT_COMMAND_MESSAGE);
        }
        if (parts.length == 2) {
            String action = parts[0];
            String argument = parts[1];
            switch (action) {
                case ADD_COMMAND: // add a | add all | add space | add m-p | add p-m
                    addCharacter(argument);
                    break;
                case REMOVE_COMMAND: // remove a | remove all | remove space | remove m-p | remove p-m
                    removeCharacter(argument);
                    break;
                case RES_COMMAND: // res up | res down
                    adjustResolution(argument);
                    break;
                case IMAGE_COMMAND: // image (path)
                    loadNewImage(argument);
                    break;
                case OUTPUT_COMMAND: // output console | output html
                    chooseOutputType(argument);
                    break;
                default:
                    throw new IllegalArgumentException(INCORRECT_COMMAND_MESSAGE);
            }
        } else { // parts.length == 1
            // In this case of 1 arg and we are in res
            if (parts[0].equals(RES_COMMAND)) {
                throw new IllegalArgumentException(RES_ERROR_MESSAGE);
            }
            // Its case of "output" and then nothing
            if (parts[0].equals(OUTPUT_COMMAND)) {
                throw new IllegalArgumentException(OUTPUT_ERROR_MESSAGE);
            } else {
                throw new IllegalArgumentException(INCORRECT_COMMAND_MESSAGE);
            }
        }
    }

    /**
     * This method will add a character to the list of allowed characters and to the charMatcher.
     *
     * @param operationType the operation type that the user entered (a | all | space | m-p | p-m).
     */
    private void addCharacter(String operationType) throws IllegalArgumentException {
        // Tav bodded
        if (operationType.length() == 1) {
            allowedCharacters.add(operationType.charAt(0));
            charMatcher.addChar(operationType.charAt(0));
            return;
        }
        // add all
        if (operationType.equals(ALL_KEYWORD)) {
            for (int i = MIN_CHAR_ASCII_VALUE; i < MAX_CHAR_ASCII_VALUE; i++) {
                allowedCharacters.add((char) i);
                charMatcher.addChar((char) i);
            }
            return;
        }
        //add space
        if (operationType.equals(SPACE_KEYWORD)) {
            allowedCharacters.add(SPACE);
            charMatcher.addChar(SPACE);
            return;
        }
        // add m-p | add p-m
        if (operationType.length() == 3 && operationType.charAt(1) == '-') {
            char firstChar = operationType.charAt(0);
            char secondChar = operationType.charAt(2);
            if (firstChar > secondChar) { //swap the chars
                char temp = firstChar;
                firstChar = secondChar;
                secondChar = temp;
            }
            for (int i = firstChar; i < secondChar + 1; i++) {
                allowedCharacters.add((char) i);
                charMatcher.addChar((char) i);
            }
            return;
        }
        throw new IllegalArgumentException(ADD_ERROR_MESSAGE);
    }

    /**
     * This method will remove a character from the list of allowed characters and from the charMatcher.
     *
     * @param operationType the operation type that the user entered (a | all | space | m-p | p-m).
     */
    private void removeCharacter(String operationType) throws IllegalArgumentException {

        // Remove a single char
        if (operationType.length() == 1) {
            allowedCharacters.remove(operationType.charAt(0));
            charMatcher.removeChar(operationType.charAt(0));
            return;
        }
        // Remove all
        if (operationType.equals(ALL_KEYWORD)) {
            allowedCharacters.clear();
            charMatcher.clear();
            return;
        }
        // Remove space
        if (operationType.equals(SPACE_KEYWORD)) {
            allowedCharacters.remove(SPACE);
            charMatcher.removeChar(SPACE);
            return;
        }
        // Remove m-p | Remove p-m
        if (operationType.length() == 3 && operationType.charAt(1) == '-') {
            char firstChar = operationType.charAt(0);
            char secondChar = operationType.charAt(2);

            if (firstChar > secondChar) { //swap the chars
                char temp = firstChar;
                firstChar = secondChar;
                secondChar = temp;
            }

            for (int i = firstChar; i < secondChar + 1; i++) {
                allowedCharacters.remove((char) i);
                charMatcher.removeChar((char) i);
            }
            return;
        }
        throw new IllegalArgumentException(REMOVE_ERROR_MESSAGE);
    }

    /**
     * This method will adjust the resolution of the image that is being processed.
     *
     * @param argument the argument that the user entered (up | down).
     */
    private void adjustResolution(String argument) throws IllegalArgumentException {
        if (argument.equals(UP_KEYWORD)) {
            if (resolution * 2 > maxCharsInRow) {
                throw new IllegalArgumentException(WIDTH_CHANGE_ERROR_MESSAGE);
            }
            resolution *= 2;
            System.out.println(RESOLUTION_CHANGE_MESSAGE + resolution + ".");

        } else if (argument.equals(DOWN_KEYWORD)) {
            if (resolution / 2 < minCharsInRow) {
                throw new IllegalArgumentException(WIDTH_CHANGE_ERROR_MESSAGE);
            }
            resolution /= 2;
            System.out.println(RESOLUTION_CHANGE_MESSAGE + resolution + ".");
        } else {
            throw new IllegalArgumentException(RES_ERROR_MESSAGE);
        }
    }

    /**
     * This method will load a new image to be processed.
     *
     * @param argument the argument that the user entered (the path to the new image).
     */
    private void loadNewImage(String argument) throws IOException {
        Image newImage = new Image(argument);
        fileName = argument;
        initImage(newImage);
    }

    /**
     * This method will change the output type to console or html file.
     *
     * @param argument the argument that the user entered (console | html).
     */
    private void chooseOutputType(String argument) throws IllegalArgumentException {

        if (argument.equals(CONSOLE_COMMAND)) {
            usingConsole = true;
        }

        if (argument.equals(HTML_COMMAND)) {
            usingConsole = false;
        }

        // Its case of "output" and then something wrong
        else {
            throw new IllegalArgumentException(OUTPUT_ERROR_MESSAGE);
        }
        outputAscii();
    }

    /**
     * This method will output the ascii art to the console or to an html file.
     *
     * @param args the arguments that the program gets from the user.
     */
    public static void main(String[] args) {

        new Shell().run();

    }
}
