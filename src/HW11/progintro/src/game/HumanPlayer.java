package game;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    @Override
    public Move move(final Position position, final Cell cell) throws Exception {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column: ");
            try {
                final Move move = new Move(in.nextInt()-1, in.nextInt()-1, cell);
                if (position.isValid(move)) {
                    return move;
                }
                final int row = move.getRow();
                final int column = move.getColumn();
                out.println("Move " + move + " is invalid"); //TODO: create wrong move cheker
            } catch (InputMismatchException e){
                System.out.println("The type of entered data: '" +
                        in.nextLine() +"' does not match the required: 'row(type: int) column(type: int)'");
                throw new Exception("InputMismatchException");
            }
        }
    }
}
