public class Application {

    Application() {

    }

    void run() {
        Board board = new Board();
        Solver solver = new Solver(board);
        boolean solutionFound = false;

        solver.setSolutionState(false);
        solver.setCounter(0);

       /* while (!solutionFound) {
            solutionFound = solver.solve(0, 0);
            if (solver.getSolutionState()) {    //When one viable Solution is found, stop
                break;
            }
            System.out.println("Keine Lösung gefunden");
            solver.setCounter(0);
            board.initialiseSmallBoard();
            board.blackenSmallBoard();
            board.printBoard();
        }

        board.printBoardWithFollowers();*/


        //Großes Board
        board.initialiseBoard();
        board.blackenAdjacentFields();
        board.printBoard();

        solver.setSolutionState(false);
        solver.setCounter(0);

        while (!solutionFound) {
            solutionFound = solver.solve(0, 1);
            if (solver.getSolutionState()) {    //When one viable Solution is found, stop
                break;
            }
            System.out.println("Keine Lösung gefunden");
            solver.setCounter(0);
            board.initialiseBoard();
            board.blackenAdjacentFields();
            board.printBoard();
        }

        board.printBoardWithFollowers();




    }
}
