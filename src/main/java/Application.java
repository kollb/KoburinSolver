public class Application {

    Application() {

    }

    void run() {
        Board board = new Board();
        Solver solver = new Solver(board);

        board.initialiseSmallBoard();
        board.blackenSmallBoard();
        /*board.initialiseBoard();
        board.blackenAdjacentFields();*/
        board.printBoard();

        System.out.println("\n\n\n");

        solver.solve(0,0);

        board.printBoard();
        board.printBoardWithFollowers();
    }


}
