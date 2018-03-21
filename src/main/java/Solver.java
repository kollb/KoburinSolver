import java.util.ArrayList;

public class Solver {
    private int counter;

    private final Board board;

    public Solver(Board board) {
        this.board = board;
    }


    public boolean finalValidation() {
       // System.out.println("jan");
        for (Cell[] row : board.getBoard()) {
            for (Cell field : row) {

                if (!field.isUsed()){
                    //System.out.println("hier kommt flasch raus");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean solve(int x, int y) {
        return solve(board.getCell(x, y));
    }


    public boolean solve(Cell currentCell) {

        if (currentCell.isStart()) {
            System.out.println(currentCell);
            return finalValidation();
        }

        if (counter == 0) {
            currentCell.setStart(true);
        }

        currentCell.enter();
        ArrayList<Cell> possibleCells = currentCell.possibleValues(board);
        System.out.println(currentCell + " ?> " + possibleCells);
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
