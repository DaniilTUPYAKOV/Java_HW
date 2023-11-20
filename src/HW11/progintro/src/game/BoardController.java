package game;

public class BoardController implements Position{
    final private Position controlledBoard;

    public BoardController(Position board){
        this.controlledBoard = board;
    }

    @Override
    public boolean isValid(Move move) {
        return controlledBoard.isValid(move);
    }

    @Override
    public Cell getCell(int r, int c) {
        return controlledBoard.getCell(r, c);
    }

    @Override
    public String toString() {
        return controlledBoard.toString();
    }
}
