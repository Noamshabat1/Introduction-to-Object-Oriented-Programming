import java.util.Arrays;

/**
 * Represents the game board for a tic-tac-toe game.
 * <p>
 * This class provides the necessary functionality to create a game board of a specified size,
 * place marks on the board, and check the state of individual squares.
 * <p>
 * The board is represented as a two-dimensional array of `Mark` enum values, where each value
 * can be `Mark.BLANK`, `Mark.X`, or `Mark.O`.
 */
public class Board {

    private static final int DEFAULT_BOARD_SIZE = 4;
    private final int boardSize;
    private final Mark[][] board;

    /**
     * default Constructs for Board object with a default size.
     */
    public Board() {
        this(DEFAULT_BOARD_SIZE);
    }

    /**
     * Second Constructs for a Board object with a specified size.
     *
     * @param size The size of the board.
     */
    public Board(int size) {
        this.boardSize = size;
        this.board = new Mark[this.boardSize][this.boardSize];

        for (Mark[] mark : this.board) {
            Arrays.fill(mark, Mark.BLANK);
        }
    }

    /**
     * Gets the size of the board.
     *
     * @return The size of the board.
     */
    public int getSize() {
        return this.boardSize;
    }

    /**
     * Places a mark on the board at the specified row and column.
     *
     * @param mark The mark to be placed on the board.
     * @param row  The row to place the mark.
     * @param col  The column to place the mark.
     * @return true if the mark was successfully placed, false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (isNotLegalBoardBorders(row, col)) {
            System.err.println(Constants.INVALID_COORDINATE);
            return false;

        } else if (this.getMark(row, col) != Mark.BLANK) {
            return false;
        } else {
            this.board[row][col] = mark;
            return true;
        }
    }

    /**
     * Checks whether the specified row and column are within the legal borders of the board.
     *
     * @param row The row to be checked.
     * @param col The column to be checked.
     * @return true if the row and column are not within the board's borders, false otherwise.
     */
    private boolean isNotLegalBoardBorders(int row, int col) {
        int size = this.getSize();
        if (row < 0 || row >= size || col < 0 || col >= size) {
            return true;
        }
        return false;
    }

    /**
     * Retrieves the mark at the specified row and column of the board.
     *
     * @param row The row of the mark.
     * @param col The column of the mark.
     * @return The mark at the specified location or Mark.BLANK if the location is outside the board's
     * boundaries.
     */
    public Mark getMark(int row, int col) {
        if (row < 0 || row >= this.board.length || col < 0 || col >= this.board.length) {
            return Mark.BLANK;
        }
        return this.board[row][col];
    }
}
