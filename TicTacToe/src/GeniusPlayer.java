/**
 * Represents a tic-tac-toe player with a specific starting strategy.
 *
 * This player, referred to as 'GeniusPlayer', has a distinct strategy for its first move and
 * subsequent moves. Initially, it avoids the first column to implement a specific game plan,
 * and then switches to a standard filling strategy for the rest of the game.
 */
public class GeniusPlayer implements Player {

    /**
     * Plays a turn in the tic-tac-toe game with a specific strategy.
     *
     * On the first turn, this player skips the first column entirely, starting from the second column.
     * This is done to implement a specific opening strategy. On subsequent turns, it considers all
     * columns, following a standard top-to-bottom, left-to-right filling approach.
     *
     * @param board The game board on which the player will make a move.
     * @param mark  The mark (X or O) that the player is using.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        if (!tryBlockingMove(board, mark)) {
            tryAnyMove(board, mark);
        }
    }

    /**
     * Tries to make a blocking move on the board.
     *
     * This method iterates from the second column to the last, checking each cell in a row-wise manner.
     * The purpose is to execute a blocking strategy.
     *
     * @param board The game board.
     * @param mark  The player's mark.
     * @return True if a move is made, false otherwise.
     */
    private boolean tryBlockingMove(Board board, Mark mark) {
        int size = board.getSize();

        // Start from the second column (index 1) and iterate over all rows to block clever
        for (int col = 1; col < size; col++) {
            for (int row = 0; row < size; row++) {
                if (board.putMark(mark, row, col)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tries to make a move on any available cell on the board.
     *
     * This method iterates over the entire board, starting from the first column, in a row-wise manner.
     * It attempts to place the player's mark in the first available cell.
     *
     * @param board The game board.
     * @param mark  The player's mark.
     */
    private void tryAnyMove(Board board, Mark mark) {
        int size = board.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.putMark(mark, row, col)) {
                    return;
                }
            }
        }
    }
}