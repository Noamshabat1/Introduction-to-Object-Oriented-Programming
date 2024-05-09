/**
 * A renderer implementation that performs no rendering.
 *
 * This class implements the Renderer interface but does not provide any concrete rendering logic.
 */
public class VoidRenderer implements Renderer {

    /**
     * Does nothing when called to render the board.
     *
     * @param board The game board that would typically be rendered. In this case, it is not used.
     */
    @Override
    public void renderBoard(Board board) {
    }
}
