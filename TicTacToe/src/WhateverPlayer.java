import java.util.Random;

/**
 * Represents a player in a tic-tac-toe game who makes moves randomly.
 *
 * This player does not follow any strategy and randomly selects positions on the board to place their mark.
 * The randomness is achieved using the java.util.Random class.
 */
public class WhateverPlayer implements Player {

    private final Random random;

    /**
     * Constructor for WhateverPlayer.
     * Initializes a new Random object to be used for generating random moves.
     */
    public WhateverPlayer() {
        this.random = new Random();
    }

    /**
     * Plays a turn for the WhateverPlayer.
     *
     * During his turn, the player randomly selects a row and a column on the board to place their mark.
     * If the randomly chosen cell is already occupied, the player will continue to select random cells
     * until an empty one is found.
     *
     * @param board The game board on which the player will place their mark.
     * @param mark  The mark (X or O) that the player is playing with.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int boardSize = board.getSize();

        int row = this.random.nextInt(boardSize);
        int col = this.random.nextInt(boardSize);

        while (!board.putMark(mark, row, col)) {
            row = this.random.nextInt(boardSize);
            col = this.random.nextInt(boardSize);
        }
    }
}