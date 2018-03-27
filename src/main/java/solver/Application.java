package solver;

public class Application implements Runnable {

    public Application() {

    }

    public void run() {
      /*  Board board = new Board();
        // Solver solver = new Solver(board);
        boolean solutionFound = false;


        //Läuft!
        *//*do {
            board.initialiseSmallBoard();
            board.blackenAdjacentFields();
            board.printBoard();
            solver.setCounter(0);
            solutionFound = solver.solve(0,0);
            board.printBoard();
        } while (!solutionFound);*//*

        do {
            board.initialiseSmallBoard();
            board.blackenAdjacentFields();
            board.printBoard();
            System.out.println(board.hashCode());
            solver.setCounter(0);
            solutionFound = solver.solve(0,0);
        } while (!solutionFound);

        //board.printBoard();


        board.printBoardWithFollowers();*/




    }
}
