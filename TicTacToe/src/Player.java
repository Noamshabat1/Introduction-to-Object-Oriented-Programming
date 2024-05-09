/**
 * An interface defining for a player in a game.
 */
public interface Player {

    /**
     * Plays a turn for the player.
     *
     * This method is intended to be implemented by various types of players to define how they interact
     * with the game board during their turn. The implementation can vary greatly depending on the type
     * of player - for example, a human player might make decisions based on user input, whereas a Random
     * player might use some algorithm to determine its move.
     *
     * @param board The game board on which the player will make a move.
     * @param mark  The mark (X or O) that the player is using.
     */
    void playTurn(Board board, Mark mark);
}
