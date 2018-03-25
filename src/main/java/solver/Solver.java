package solver;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver {
    private int counter;

    private final Board board;

    HashSet<String> historicBoards = new HashSet<>();

    private boolean solutionFound = false;

    public Solver(Board board) {
        this.board = board;
    }

    public boolean getSolutionState() {
        return solutionFound;
    }

    public void setSolutionState(boolean solutionFound) {
        this.solutionFound = solutionFound;
    }

    public void setCounter(int counter) {
        this.counter = 0;
    }

    public void addCheckedBoard(String board) {
        this.historicBoards.add(board);
    }

    public boolean alreadyChecked(String board) {
        return historicBoards.contains(board);
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
        if (this.historicBoards.contains(this.board.toString())) {
            System.out.println("!!!!!!!!!!!!!!! Already checked that.");
            return false;
        }
        historicBoards.add(this.board.toString());
        return solve(board.getCell(x, y));
    }


    public boolean solve(Cell currentCell) {
        if (currentCell.isStart()) {
            return finalValidation();
        }

        if (counter == 0) {
            currentCell.setStart(true);
        }

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

    public void reset() {
        this.historicBoards =  new HashSet<>();
        this.counter = 0;
    }


}
