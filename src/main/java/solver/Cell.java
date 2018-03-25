package solver;

import java.util.*;
import java.util.stream.Collectors;

public class Cell {

    public int x;
    public int y;
    private char value;
    private Cell next;
    private boolean start;


    private HashSet<Cell> surroundedBlackCells = new HashSet<Cell>();

    Cell(int x, int y, char value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public boolean isBlackend() {
        return this.value == 'x';
    }

    public boolean isUsed() {
        return isBlackend() || isNumber() || next != null;
    }

    public int getNumber() {
        return this.value - 48;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isEmpty() {
        return this.value == '_';
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public void setBlack() {
        //System.out.println("ayy yoo, ya blackening me?? I'm the one and only " + this); // TODO
        this.value = 'x';
    }

    public boolean isVisitable(Cell comingFrom) {
        if (this.next == comingFrom) {
            return false;
        }
        return !isBlackend() && !isNumber() && (this.next == null || this.start);
    }


    public char getValue() {
        return value;
    }

    public Cell getNext() {
        return next;
    }

    public void setNext(Cell next) {
        this.next = next;
    }

    public ArrayList<Cell> getSurroundingCells(Cell cell, Board board) {
        ArrayList<Cell> surroundingCells = new ArrayList<Cell>();

        Cell above = board.getCell(cell.x, cell.y - 1);
        Cell below = board.getCell(cell.x, cell.y + 1);
        Cell left = board.getCell(cell.x - 1, cell.y);
        Cell right = board.getCell(cell.x + 1, cell.y);

        if (above != null) surroundingCells.add(above);
        if (below != null) surroundingCells.add(below);
        if (left != null) surroundingCells.add(left);
        if (right != null) surroundingCells.add(right);

        return surroundingCells;
    }

    public ArrayList<Cell> possibleValues(Board board) {
        ArrayList<Cell> possibleCells = new ArrayList<Cell>();

        ArrayList<Cell> surroundingCells = this.getSurroundingCells(this, board);

        for (Cell c : surroundingCells) {
            if (c != null && c.isVisitable(this)) {
                possibleCells.add(c);
            }
        }

        return possibleCells;
    }

    public void enter() {
    }

    public void leave() {
        this.next = null;
    }

    public void addToBlackSurroundings(Cell cell) {
        if (surroundedBlackCells.size() < getNumber()) {
            //System.out.println("sup dawg, adding " + cell + " to blackend cells for mah nigga " + this); // less profanity
            cell.setBlack();
            surroundedBlackCells.add(cell);
        } else {
            System.err.println("@solver.Cell.java 105 - Already statisfied.");
        }
    }

    public boolean doBlackening(Board board) {

        Cell above = board.getCell(this.x, this.y - 1);
        Cell below = board.getCell(this.x, this.y + 1);
        Cell left = board.getCell(this.x - 1, this.y);
        Cell right = board.getCell(this.x + 1, this.y);

        List<Cell> remaining = new ArrayList<>();
        List<Cell> surrounding = Arrays.asList(above, below, left, right);


        for (Cell c : surrounding) {
            if (c == null) {
                continue;
            }

            if (c.isBlackend()) { // if already blackend, add to set just so we know
                addToBlackSurroundings(c);
            } else {
                remaining.add(c); // remaining cells we may have to check
            }
        }

        List<Cell> unsatisfieds = remaining //Arrays.asList(above, below, left, right)
                .stream()
                .filter(c -> c.isBlackable(board))
                .collect(Collectors.toList());

        Collections.shuffle(unsatisfieds); // zufällig wählen

        unsatisfieds.subList(0, inNeedOf()).forEach(this::addToBlackSurroundings);

        return surroundedBlackCells.size() == this.getNumber();
    }


    public boolean isBlackable(Board board) {
        if (isBlackend()) return true;
        if (isNumber()) return false;

        // cell is an empty one
        // gotta check if surroundings are satisfied
        Cell above = board.getCell(this.x, this.y - 1);
        Cell below = board.getCell(this.x, this.y + 1);
        Cell left = board.getCell(this.x - 1, this.y);
        Cell right = board.getCell(this.x + 1, this.y);

        // if one of the surroundings are already satisfied
        // the cell in question cant be blackend
        if (above != null && above.isStatisfied()) return false;
        if (below != null && below.isStatisfied()) return false;
        if (left != null && left.isStatisfied()) return false;
        if (right != null && right.isStatisfied()) return false;

        // i hope so
        return true;
    }

    public int inNeedOf() {
        return getNumber() - surroundedBlackCells.size();
    }

    public boolean isStatisfied() { // check if cell is satisfied
        return inNeedOf() == 0;
    }

    public boolean isNumber() {
        return this.value == '0'
                || this.value == '1'
                || this.value == '2';
    }

    @Override
    public String toString() {
        //return "solver.Cell (" + this.x + "|" + this.y + ")";
        return "solver.Cell[x=" + x + ",y=" + y + ",v=" + value + "]";
    }
}
