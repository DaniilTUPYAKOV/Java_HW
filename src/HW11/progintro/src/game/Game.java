package game;

public class Game {
    private final boolean log;
    private final boolean finalLog;
    private final Player player1, player2;

    public Game(final boolean log, final boolean finalLog, final Player player1, final Player player2) {
        this.log = log;
        this.finalLog = finalLog;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) {
        try{
            while (true) {
                final int result1 = move(board, player1, 1);
                if (result1 != -1) {
                    return result1;
                }
                final int result2 = move(board, player2, 2);
                if (result2 != -1) {
                    return result2;
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private int move(final Board board, final Player player, final int no) throws Exception {
        if (!board.isAvailible()){
            throw board.getExeption();
        }
        Result result;
        try{
            final Move move = player.move(board.getPosition(), board.getCell());
            result = board.makeMove(move);
            log("Player " + no + " move: " + move);
        } catch (Exception e){
            result = Result.LOSE;
            System.out.println("The appearance of '" + e.getMessage() + "' led to player " + no + " defeat.");
        }
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            if (finalLog){
                showFinalLayout(board);
            }
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }

    public void showFinalLayout(Board board){
        System.out.println("Game final Layout:");
        System.out.println(board.toString());
    }
}
