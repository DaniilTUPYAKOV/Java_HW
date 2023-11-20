package game;

public class Main {
    public static void main(String[] args) {
        final Game game = new Game(false, true, new HumanPlayer(), new RandomPlayer());
        int result;
        do {
            result = game.play(new MNKBoard(4,4,5));
            System.out.println("Game result: " + result);
        } while (result != 0);
    }
}
