/**
 * Represents a tic-tac-toe game.
 * This class encapsulates the logic for a tic-tac-toe game, including managing the players,
 * the game board, rendering the board, and determining the game's outcome.
 */
public class Game {
    // constants:
    private final static int DEFAULT_WIN_STREAK = 3;
    private final static int NUMBER_PLAYERS = 2;
    public static final int SEEN_ONE = 1;
    public static final int RESET = 0;


    private final Player[] players = new Player[NUMBER_PLAYERS];
    private final Mark[] marks = new Mark[NUMBER_PLAYERS];
    private final Renderer renderer;
    private final int winStreak;
    private final Board board;
    private final int maxTurns;

    /**
     * Default Constructs for a Game with default board size & win streak, and provided players and
     * renderer.
     *
     * @param playerX The player who will use the 'X' mark.
     * @param playerO The player who will use the 'O' mark.
     * @param renderer The renderer to be used for displaying the board.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.players[0] = playerX;
        this.players[1] = playerO;
        this.marks[0] = Mark.X;
        this.marks[1] = Mark.O;
        this.renderer = renderer;
        this.winStreak = DEFAULT_WIN_STREAK;
        this.board = new Board();
        this.maxTurns = this.board.getSize() * this.board.getSize();
    }

    /**
     * Constructs a Game with specified board size, win streak, players, and renderer.
     *
     * @param playerX The player who will use the 'X' mark.
     * @param playerO The player who will use the 'O' mark.
     * @param size The size of the board (number of squares in one row/column).
     * @param winStreak The number of consecutive marks needed to win.
     * @param renderer The renderer to be used for displaying the board.
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.players[0] = playerX;
        this.players[1] = playerO;
        this.marks[0] = Mark.X;
        this.marks[1] = Mark.O;

        if (winStreak < 2 || winStreak > size) {
            this.winStreak = size;
        } else {
            this.winStreak = winStreak;
        }

        this.renderer = renderer;
        this.board = new Board(size);
        this.maxTurns = size * size;
    }

    /**
     * Retrieves the win streak length.
     *
     * @return The number of consecutive marks needed to win the game.
     */
    public int getWinStreak() {
        return this.winStreak;
    }

    /**
     * Retrieves the board size.
     *
     * @return The size of the board (number of squares in one row/column).
     */
    public int getBoardSize() {
        return this.board.getSize();
    }

    /**
     * Runs the game until a player wins or the board is full.
     *
     * This method controls the game flow, alternating turns between the two players, checking
     * for a winner after each turn, and rendering the board. The game ends when one player wins
     * or when all cells are filled without a winner.
     *
     * @return The mark of the winning player or Mark.BLANK if there's no winner (tie game).
     */
    public Mark run() {
        int counter = 0;
        while (counter < maxTurns) {
            players[counter % 2].playTurn(board, marks[counter % 2]);
            Mark ans = checkWin();
            if (ans != Mark.BLANK) {
                return ans;
            }
            counter++;
            renderer.renderBoard(board);
        }
        return Mark.BLANK;
    }

    /**
     * Checks if there is a winning condition on the board met.
     *
     * This method checks rows, columns, and diagonals for the win streak of the same mark.
     * It renders the board if a winning condition is found.
     *
     * @return The winning player's mark or Mark.BLANK if no winner is found.
     */
    private Mark checkWin() {

        // Check rows and columns
        Mark ans = checkRight();
        if (ans != Mark.BLANK) {
            renderer.renderBoard(board); // prints the last board if won
            return ans;
        }
        ans = checkDown();
        if (ans != Mark.BLANK) {
            renderer.renderBoard(board); // prints the last board if won
            return ans;
        }

        // Check diagonals
        ans = checkMainDiagonals();
        if (ans != Mark.BLANK) {
            renderer.renderBoard(board); // prints the last board if won
            return ans;
        }
        ans = checkAntiMainDiagonals();
        if (ans != Mark.BLANK) {
            renderer.renderBoard(board); // prints the last board if won
            return ans;
        }
        return Mark.BLANK;
    }

    /**
     * Checks for a winning condition in all rows.
     *
     * This method iterates through each row and checks if there are enough consecutive
     * same marks to meet the win streak. If such a condition is found, it returns the winning mark.
     *
     * @return The mark that has won in a row or Mark.BLANK if no row has a winning streak.
     */
    private Mark checkRight() {
        for (int row = 0; row < this.board.getSize(); row++) {
            int inARow = RESET;
            Mark curMark = Mark.BLANK;
            for (int col = 0; col < this.board.getSize(); col++) {
                if (board.getMark(row, col) != Mark.BLANK) {
                    if (board.getMark(row, col) == curMark) {
                        inARow++;
                    } else {
                        inARow = SEEN_ONE;
                        curMark = board.getMark(row, col);
                    }
                    if (inARow == this.winStreak) {
                        return curMark;
                    }
                } else {
                    inARow = RESET;
                    curMark = Mark.BLANK;
                }
            }
        }
        return Mark.BLANK;
    }

    /**
     * Checks for a winning condition in all columns.
     *
     * Similar to checkRight, this method iterates through each column and checks for consecutive
     * same marks that meet the win streak criteria.
     *
     * @return The mark that has won in a column or Mark.BLANK if no column has a winning streak.
     */
    private Mark checkDown() {
        for (int col = 0; col < this.board.getSize(); col++) {
            int inARow = RESET;
            Mark curMark = Mark.BLANK;
            for (int row = 0; row < this.board.getSize(); row++) {
                if (board.getMark(row, col) != Mark.BLANK) {
                    if (board.getMark(row, col) == curMark) {
                        inARow++;
                    } else {
                        inARow = SEEN_ONE;
                        curMark = board.getMark(row, col);
                    }
                    if (inARow == this.winStreak) {
                        return curMark;
                    }
                } else {
                    inARow = RESET;
                    curMark = Mark.BLANK;
                }
            }
        }
        return Mark.BLANK;
    }


    /**
     * Checks for a winning condition in all main diagonals.
     *
     * This method iterates through possible diagonal lines (from top-left to bottom-right) and checks
     * for consecutive same marks.
     *
     * @return The mark that has won on a main diagonal or Mark.BLANK if no diagonal has a winning streak.
     */
    private Mark checkMainDiagonals() {
        int size = this.board.getSize();
        // Check diagonals starting from the leftmost column
        for (int row = 0; row <= size - this.winStreak; row++) {
            Mark winner = checkSingleDiagonal(row, 0, 1, 1);
            if (winner != Mark.BLANK) {
                return winner;
            }
        }
        // Check diagonals starting from the top row
        for (int col = 1; col <= size - this.winStreak; col++) {
            Mark winner = checkSingleDiagonal(0, col, 1, 1);
            if (winner != Mark.BLANK) {
                return winner;
            }
        }
        return Mark.BLANK;
    }


    /**
     * Checks for a winning condition in all anti-main-diagonals.
     *
     * This method iterates through possible anti-diagonal lines (from top-right to bottom-left)
     * and checks for consecutive same marks.
     *
     * @return The mark that has won on an anti-main-diagonal or Mark.BLANK if no anti-diagonal has a
     * winning streak.
     */
    private Mark checkAntiMainDiagonals() {
        int size = this.board.getSize();
        // Check diagonals starting from the rightmost column
        for (int row = 0; row <= size - this.winStreak; row++) {
            Mark winner = checkSingleDiagonal(row, size - 1, 1, -1);
            if (winner != Mark.BLANK) {
                return winner;
            }
        }
        // Check diagonals starting from the top row
        for (int col = size - 2; col >= this.winStreak - 1; col--) {
            Mark winner = checkSingleDiagonal(0, col, 1, -1);
            if (winner != Mark.BLANK) {
                return winner;
            }
        }
        return Mark.BLANK;
    }

    /**
     * Checks for a winning condition in one diagonal direction.
     *
     * This method scans a diagonal line on the board, starting from the specified row and column, and
     * incrementing the row and column indices by the specified amounts. It checks for a sequence of
     * consecutive identical marks (excluding BLANK) of length equal to the win streak.
     *
     * @param startRow The starting row index.
     * @param startCol The starting column index.
     * @param rowIncrement The amount to increment the row index by after each step.
     * @param colIncrement The amount to increment the column index by after each step.
     * @return The winning mark if a win streak is found in this diagonal, otherwise returns Mark.BLANK.
     */
    private Mark checkSingleDiagonal(int startRow, int startCol, int rowIncrement, int colIncrement) {
        int inARow = RESET;
        Mark curMark = Mark.BLANK;

        int row = startRow, col = startCol;
        while (row < this.board.getSize() && col < this.board.getSize() && col >= 0 && row >= 0) {
            Mark nextMark = board.getMark(row, col);
            if (nextMark == Mark.BLANK) {
                // Reset if blank is encountered
                inARow = RESET;
                curMark = Mark.BLANK;
            } else if (nextMark == curMark) {
                // Increment if the same mark is encountered
                inARow++;
                if (inARow == this.winStreak) {
                    return curMark;
                }
            } else {
                // Reset if a different mark is encountered
                inARow = SEEN_ONE;
                curMark = nextMark;
            }
            row += rowIncrement;
            col += colIncrement;
        }
        return Mark.BLANK;
    }

}
