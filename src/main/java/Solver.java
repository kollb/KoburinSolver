import java.util.ArrayList;

public class Solver {
    private int counter;

    private final Board board;

    private boolean solutionFound = false;

    public Solver(Board board) {
        this.board = board;
    }

    public boolean getSolutionState(){
        return solutionFound;
    }
    public void setSolutionState(boolean solutionFound){
        this.solutionFound = solutionFound;
    }

    public void setCounter(int counter){
        this.counter = 0;
    }


    public boolean finalValidation() {
        for (Cell[] row : board.getBoard()) {
            for (Cell field : row) {
                if (!field.isUsed()) {
                    return false;
                }
            }
        }
        System.out.println("Lösung gefunden!");
        this.solutionFound = true;
        this.counter = 0;
        return true;
    }

    public boolean solve(int x, int y) {
           return solve(board.getCell(x, y));
    }


    public boolean solve(Cell currentCell) {
        if (currentCell.isStart()) {
            // System.out.println(currentCell);
            return finalValidation();
        }

        if (counter == 0) {
            currentCell.setStart(true);
        }
       // System.out.println("currentcell:" + currentCell);

        currentCell.enter();
        ArrayList<Cell> possibleCells = currentCell.possibleValues(board);
        //System.out.println(currentCell + " ?> " + possibleCells);
        for (Cell c : possibleCells) {
            currentCell.setNext(c);
            counter++;
            if (solve(c)) {
                return true;
            }
        }
        currentCell.leave();
        return false;
    }


}
