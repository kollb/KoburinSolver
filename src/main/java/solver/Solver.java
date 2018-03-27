package solver;

import gui.Controller;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashSet;

public class Solver implements Runnable {

    private int counter;
    private Controller controller;
    private String operatingMode;
    /* 4x4, 4x4R, 10x10, 10x10R */

    private final Board board;

    HashSet<String> historicBoards = new HashSet<>();

    private boolean solutionFound = false;

    public Solver(Board board, String mode, Controller contr) {
        this.board = board;
        this.controller = contr;
        this.operatingMode = mode;
    }

    public Board getBoard() {
        return this.board;
    }

    public boolean getSolutionState() {
        return solutionFound;
    }

    public void setSolutionState(boolean solutionFound) {
        this.solutionFound = solutionFound;
    }

    public void setCounter(int counter) {
        this.counter = counter;
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

    public void run() {
        System.out.println("Thread startet");
        reset();

        do {
            switch (operatingMode) {
                case "4x4":
                    board.initialiseSmallBoard();
                    board.blacken4x4BoardHard();
                    board.printBoard();
                    break;
                case "4x4R":
                    board.initialiseSmallBoard();
                    board.blackenAdjacentFields();
                    board.printBoard();
                    break;
                case "10x10":
                    board.initialise10x10Board();
                    board.blacken10x10Hard();
                    board.printBoard();
                    break;
                case "10x10R":
                    board.initialise10x10Board();
                    board.blackenAdjacentFields();
                    board.printBoard();
                    break;
                default:
                    board.initialiseSmallBoard();
                    board.blacken4x4BoardHard();
                    board.printBoard();
                    break;
            }
            setCounter(0);
            solve(0, 2);
            board.printBoard();
        } while (!solutionFound);

        System.out.println("Thread zuende");
    }


    public boolean solve(Cell currentCell) {
        if (counter % 20 == 0) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    controller.update(board);
                }
            });
        }
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
        this.historicBoards = new HashSet<>();
        this.counter = 0;
    }


}
