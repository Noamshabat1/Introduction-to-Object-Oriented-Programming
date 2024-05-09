/**
 * An interface defining the contract for rendering a game board.
 */
public interface Renderer {

    /**
     * Renders the given board.
     *
     * This method is intended to be implemented by different rendering strategies to provide
     * a visual representation of the board's current state. The specific rendering details
     * depend on the implementation.
     *
     * @param board The game board to be rendered.
     */
    void renderBoard(Board board);
}
