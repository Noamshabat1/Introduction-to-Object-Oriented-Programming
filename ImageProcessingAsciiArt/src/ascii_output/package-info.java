/**
 * The {@code ascii_output} package is designed to offer flexible output options for ASCII art,
 * allowing for representation in various forms. It includes an interface and implementations
 * that facilitate the output of 2D char arrays as ASCII art, supporting console and HTML formats.
 * This package is ideal for applications that generate ASCII art and require versatile output
 * mechanisms, from simple console displays to more complex web presentations.
 * Key components include:
 * { ascii_output.AsciiOutput} - An interface defining the contract for ASCII art output,
 * ensuring that any class implementing this interface can output a 2D array of chars in some fashion.
 * { ascii_output.ConsoleAsciiOutput} - An implementation of {@code AsciiOutput} that outputs
 * ASCII art to the console, suitable for command-line applications or debugging purposes.
 * { ascii_output.HtmlAsciiOutput} - An implementation of {@code AsciiOutput} that generates an
 * HTML file rendering ASCII art, enabling the art to be viewed in web browsers. This approach offers
 * enhanced visualization options, including font customization and layout control.
 * By providing a standardized interface and multiple output options, the {@code ascii_output} package
 * supports the creation of ASCII art applications with flexible and diverse output requirements,
 * catering to both developers' and end-users' needs.
 *
 * @author Dan Nirel, Noam Shabat and Samuel Hayat
 * @version Java 11
 */
package ascii_output;
