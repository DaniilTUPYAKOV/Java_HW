package game;

import java.util.Arrays;
import java.util.Map;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '_'
    );

    private final Cell[][] cells;
    private Cell turn;
    private final int m, n, k;
    private int empty;
    private final Position controller;
    private Exception error = null;
    private final boolean availible;

    public MNKBoard(int m, int n, int k){
        this.availible = checkCorrectness(m, n, k);
        this.m = m;
        this.n = n;
        this.k = k;
        this.empty = availible ? m * n : 0;
        this.cells = availible ? new Cell[m][n] : null;
        if (availible){
            for (Cell[] row : cells) {
                Arrays.fill(row, Cell.E);
            }
        }
        turn = Cell.X;
        this.controller = availible ? new BoardController(this) : null;
        this.error = availible ? null : new IllegalArgumentException("Impossible to play with rules: m = "
                + m + ", n = " + n + ", k = " + k +
                ". Numbers m, n, k must be positive integer, and k <= max(n,m)");
    }

    private boolean checkCorrectness(int m, int n, int k){
        if (k < 1 || m < 1 || n < 1) {
            return false;
        }
        if (m < n){
            return k <= n;
        }
        return k <= m;
    }
    private boolean isInBoard(int row, int col){
        return (row < m && col < n) && (row > -1 && col > -1);
    }
    private boolean checkWin(int curRow, int curCol, int deltaRow, int deltaCol){
        int counter = this.k - 1;
        int i = curRow + deltaRow;
        int j = curCol + deltaCol;
        while (isInBoard(i, j) && cells[i][j] == turn && counter > 0){
            counter--;
            i+=deltaRow;
            j+=deltaCol;
        }
        i = curRow - deltaRow;
        j = curCol - deltaCol;
        while (isInBoard(i, j) && cells[i][j] == turn && counter > 0){
            counter--;
            i-=deltaRow;
            j-=deltaCol;
        }
        return counter == 0;
    }

    @Override
    public Position getPosition() {
        return controller;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) throws Exception {
        if (!availible){
            throw error;
        }
        if (!isValid(move)) {
            return Result.LOSE;
        }
        final int moveRow = move.getRow();
        final int moveColumn = move.getColumn();
        cells[moveRow][moveColumn] = move.getValue();
        empty--;

        if (checkWin(moveRow, moveColumn, 1, 0) ||
                checkWin(moveRow, moveColumn, 1, 1) ||
                checkWin(moveRow, moveColumn, 0, 1) ||
                checkWin(moveRow, moveColumn, -1, 1)){
            return Result.WIN;
        }
        if (empty == 0) {
            return Result.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < 3
                && 0 <= move.getColumn() && move.getColumn() < 3
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        for (int r = 0; r < n; r++) {sb.append(r+1);}
        for (int r = 0; r < m; r++) {
            sb.append("\n");
            sb.append(r+1);
            for (int c = 0; c < n; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
    @Override
    public boolean isAvailible() {
        return availible;
    }
    @Override
    public Exception getExeption() {
        return error;
    }
}
