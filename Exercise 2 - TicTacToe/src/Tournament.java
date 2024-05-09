/**
 * Manages and executes a tic-tac-toe tournament.
 *
 * This class is responsible for setting up and running a series of tic-tac-toe games, constituting a
 * tournament. It keeps track of the number of rounds, manages player turns, and records the results of
 * each game. The tournament can be configured with different board sizes, win streaks, and player types.
 */
public class Tournament {

    private static final String INVALID_ARGUMENTS =
                    "Please use this pattern: Tournament" +
                    " [round count] " +
                    " [size] " +
                    " [win_streak]\n" +
                    " [rendertarget:console/none]\n" +
                    " [first player: human/whatever/clever/genius]\n" +
                    " [second player: human/whatever/clever/genius]";
    private static final String RESULT_MESSAGE =
                    "######### Results #########\n" +
                    "Player 1, %s won: %d rounds\n" +
                    "Player 2, %s won: %d rounds\n" +
                    "Ties: %d\n";

    private final static int NUMBER_PLAYERS = 2;
    public static final int CORRECT_ARGS_COUNT = 6;
    private final int rounds;
    private final Renderer renderer;
    private final Player[] players = new Player[NUMBER_PLAYERS];


    /**
     * Constructor that Construct a Tournament with the specified number of rounds, renderer, and players.
     *
     * @param rounds   The total number of rounds to be played in the tournament.
     * @param renderer The Renderer instance used for rendering the board in each game.
     * @param player1  The first player in the tournament.
     * @param player2  The second player in the tournament.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.players[0] = player1;
        this.players[1] = player2;
    }

    /**
     * Plays the entire tournament, round by round.
     *
     * In each round, a new game is created with the specified board size and win streak. The method
     * alternates the starting player each round and records the results after each game, tallying wins,
     * losses, and ties. After all rounds are played, it prints the tournament results.
     *
     * @param size        The size of the game board for each game.
     * @param winStreak   The number of consecutive marks needed to win a game.
     * @param playerName1 The display name of the first player.
     * @param playerName2 The display name of the second player.
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        // initializing
        int player1Wins = 0;
        int player2Wins = 0;
        int ties = 0;
        int curRound = 0;

        while (curRound < rounds) {
            Game curGame = new Game(players[curRound % 2], players[(curRound + 1) % 2], size, winStreak,
                    renderer);
            Mark curRes = curGame.run();
            if (curRes == Mark.BLANK) {
                ties++;
            } else if (curRes == Mark.X) {
                if (curRound % 2 == 0) {
                    player1Wins++;
                } else {
                    player2Wins++;
                }
            } else {
                if (curRound % 2 == 0) {
                    player2Wins++;
                } else {
                    player1Wins++;
                }
            }
            curRound++;
        }
        System.out.printf((RESULT_MESSAGE) + "%n", playerName1, player1Wins, playerName2, player2Wins, ties);
    }

    /**
     * The main entry point of the Tournament class.
     *
     * Parses command-line arguments to configure and start a new tournament. It validates the arguments
     * and initializes the required players and renderer based on the input. If the arguments are invalid or
     * if the players or renderer cannot be created, it prints an error message.
     *
     * @param args Command-line arguments specifying the settings for the tournament.
     */
    public static void main(String[] args) {
        if (args.length != CORRECT_ARGS_COUNT) {
            System.out.println(INVALID_ARGUMENTS);
            return;
        }

        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);

        PlayerFactory playerFactory = new PlayerFactory();
        Player player1 = playerFactory.buildPlayer(args[4]);
        Player player2 = playerFactory.buildPlayer(args[5]);

        if (player1 == null || player2 == null) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return;
        }

        Renderer renderer = new RendererFactory().buildRenderer(args[3], size);
        if (renderer == null) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return;
        }

        Tournament tournament = new Tournament(rounds, renderer, player1, player2);
        tournament.playTournament(size, winStreak, args[4], args[5]);
    }
}