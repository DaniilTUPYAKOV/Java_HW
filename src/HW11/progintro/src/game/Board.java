package game;

public interface Board {
    Position getPosition();
    Cell getCell();
    Result makeMove(Move move) throws Exception;
    boolean isAvailible();
    Exception getExeption();
}
