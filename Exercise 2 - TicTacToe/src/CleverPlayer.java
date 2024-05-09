/**
 * Represents a tic-tac-toe player with a straightforward and systematic playing strategy.
 *
 * This player, known as 'CleverPlayer', follows a simple and consistent approach to playing
 * tic-tac-toe. It starts from the top-left corner of the board (position 0,0) and attempts to place
 * its mark sequentially across the rows and then down the columns.
 */
public class CleverPlayer implements Player {

    /**
     * Plays a turn in the tic-tac-toe game using a systematic strategy.
     *
     * The strategy involves starting from the top-left corner of the board and moving right across
     * the columns in each row. Once a row is completed, the player moves to the next row. This
     * continues until the player successfully places a mark or runs out of spaces.
     *
     * @param board The game board on which the player will make a move.
     * @param mark The mark (X or O) that the player is using.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.putMark(mark, row, col)) {
                    return;
                }
            }
        }
    }
}