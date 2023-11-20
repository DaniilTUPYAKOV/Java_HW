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

    public MNKBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.empty = m * n;
        this.cells = new Cell[m][n];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
        this.controller = new BoardController(this);
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
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();
        empty--;

        int inDiag1 = 0;
        int inDiag2 = 0;
        for (int u = 0; u < 3; u++) {
            int inRow = 0;
            int inColumn = 0;
            for (int v = 0; v < 3; v++) {
                if (cells[u][v] == turn) {
                    inRow++;
                }
                if (cells[v][u] == turn) {
                    inColumn++;
                }
            }
            if (inRow == 3 || inColumn == 3) {
                return Result.WIN;
            }
            if (cells[u][u] == turn) {
                inDiag1++;
            }
            if (cells[u][2 - u] == turn) {
                inDiag2++;
            }
        }
        if (inDiag1 == 3 || inDiag2 == 3) {
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
        for (int r = 0; r < 3; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < 3; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}
