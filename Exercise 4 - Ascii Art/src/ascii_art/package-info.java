/**
 * The {@code ascii_art} package provides a complete system for generating ASCII art from images. It
 * includes the main entry point of the program, utility classes for handling user input, a storage
 * mechanism for optimizing runtime by avoiding unnecessary operations, and the core algorithm for
 * transforming images into ASCII art. This package is designed to process images, allowing users to
 * customize the output through various commands and parameters, and output the ASCII art to either
 * the console or HTML files for web viewing.
 * Key components of the package include:
 * 1. { ascii_art.Shell} - Serves as the main entry point of the program, handling user input and
 * orchestrating the ASCII art generation process.
 * 2. {ascii_art.KeyboardInput} - A utility class for reading user input from the console, implemented
 * as a singleton to ensure a consistent input mechanism throughout the application.
 * 3. {ascii_art.Git} - A metaphorically named storage class that mimics the functionality of Git,
 * storing pre-processed ASCII art and character brightness data to improve runtime performance by reusing
 * computations.
 * 4. {ascii_art.AsciiArtAlgorithm} - Contains the core algorithm for converting images into ASCII art,
 * leveraging the {image_char_matching.SubImgCharMatcher} for character brightness matching.
 * This package is suitable for applications requiring ASCII art generation, offering extensive
 * configurability, support for different output formats, and efficient processing through caching
 * mechanisms.
 *
 * @author Noam Shabat and Samuel Hayat
 * @version Java 11
 */
package ascii_art;
