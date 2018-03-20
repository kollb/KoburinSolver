import java.util.ArrayList;

public class Application {
    private Cell[][] board;
    private MersenneTwister random = new MersenneTwister();

    Application() {

    }

    void run() {
        Board board = new Board();
        Solver solver = new Solver();

        board.initialiseSmallBoard();
        board.blackenSmallBoard();
        board.printBoard();

        solver.drawLine(0,0,board.getBoard());

        board.printBoard();
        board.printBoardWithFollowers();
    }


}
