
/**
 * Represents a player in a tic-tac-toe game who is a real player.
 *
 * This player follow it own strategy to selects positions on the board to be place.
 * The player represent a real player.
 */
public class HumanPlayer implements Player {

    /**
     * Default Constructor for HumanPlayer.
     */
    public HumanPlayer() {}

    /**
     * Handles a human player's turn in the game.
     *
     * This method uses the human player input to place his mark on the board.
     * If the chosen cell is already occupied, the player is prompted to enter a different cell.
     *
     * @param board The game board on which the player will place their mark.
     * @param mark  The mark (X or O) that the player is playing with.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        System.out.println(Constants.playerRequestInputString(String.valueOf(mark)));
        int num = KeyboardInput.readInt();
        int row = num / 10;
        int col = num % 10;

        while (!board.putMark(mark, row, col)) {
            System.out.println(Constants.OCCUPIED_COORDINATE);
            num = KeyboardInput.readInt();
            row = num / 10;
            col = num % 10;
        }
    }
}







