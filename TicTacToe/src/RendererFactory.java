import java.util.Locale;

/**
 * A factory class for creating instances of various renderers.
 *
 * This class is responsible for generating different types of renderer objects based on the input parameters.
 */
public class RendererFactory {

    Renderer renderer;

    /**
     * Default constructor for RendererFactory.
     */
    public RendererFactory() {
    }

    /**
     * Creates and returns a renderer based on the specified type and size.
     *
     * @param renderer The type of renderer to create. Supported values are "console" and "none".
     * @param size     The size parameter for the renderer, applicable to certain types of renderers.
     * @return A renderer object of the specified type. Returns null if the type is not recognized.
     */
    public Renderer buildRenderer(String renderer, int size) {
        switch (renderer.toLowerCase(Locale.ENGLISH)) {
            case "console":
                this.renderer = new ConsoleRenderer(size);
                break;

            case "none":
                this.renderer = new VoidRenderer();
                break;

            default:
                this.renderer = null;
        }
        return this.renderer;
    }
}