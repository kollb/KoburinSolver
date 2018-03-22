import java.util.ArrayList;

public class Cell {

    public int x;
    public int y;
    private char value;
    private Cell next;
    private boolean start;

    //todo neue possibleValues schreiben
    private int adjacentBlackCells;
    private int maxAdjacentBlackCells;

    Cell(int x, int y, char value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.maxAdjacentBlackCells = this.getNumber();
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

    public void setValue(char value) {
        this.value = value;
    }

    public ArrayList<Cell> possibleValues(Board board) {
        ArrayList<Cell> possibleCells = new ArrayList<Cell>();

        Cell above = board.getCell(this.x, this.y - 1);
        Cell below = board.getCell(this.x, this.y + 1);
        Cell left = board.getCell(this.x - 1, this.y);
        Cell right = board.getCell(this.x + 1, this.y);

        if (above != null && above.isVisitable(this)) possibleCells.add(above);
        if (below != null && below.isVisitable(this)) possibleCells.add(below);
        if (left != null && left.isVisitable(this)) possibleCells.add(left);
        if (right != null && right.isVisitable(this)) possibleCells.add(right);

        return possibleCells;
    }

    public void enter() {

    }

    public void leave() {
        this.next = null;
    }


    public boolean isBlackenable(Cell cell, Board board) {

        Cell above = board.getCell(this.x, this.y - 1);
        Cell below = board.getCell(this.x, this.y + 1);
        Cell left = board.getCell(this.x - 1, this.y);
        Cell right = board.getCell(this.x + 1, this.y);

        return ((above != null && !above.isBlackend()) || above == null) &&
                ((below != null && !below.isBlackend()) || below == null) &&
                ((right != null && !right.isBlackend()) || right == null) &&
                ((left != null && !left.isBlackend()) || left == null);
    }


    //todo ins board verlagern
    @Deprecated
    public ArrayList<Cell> possibleBlackeningCoords(Board board) {
        ArrayList<Cell> surroundingFields = new ArrayList<Cell>();

        Cell above = board.getCell(this.x, this.y - 1);
        Cell below = board.getCell(this.x, this.y + 1);
        Cell left = board.getCell(this.x - 1, this.y);
        Cell right = board.getCell(this.x + 1, this.y);

        if (above != null && above.isBlackenable(this, board)) surroundingFields.add(above);
        if (below != null && below.isBlackenable(this, board)) surroundingFields.add(below);
        if (left != null && left.isBlackenable(this, board)) surroundingFields.add(left);
        if (right != null && right.isBlackenable(this, board)) surroundingFields.add(right);

        //for (Cell c : surroundingFields) System.out.println(c);
        return surroundingFields;
    }


    //todo überarbeiten?
    public boolean isNumber() {
        return this.value == '0'
                || this.value == '1'
                || this.value == '2';
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "Cell (" + this.x + "|" + this.y + ")";
    }
}
