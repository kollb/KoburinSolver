import java.util.ArrayList;

public class Cell {

    public int x;
    public int y;
    private char value;
    private Cell next;
    private boolean start;
    private Cell previous;

    public void link(Cell cell){
        this.next = cell;
        cell.previous = this;
    }

    public void unlink(){
        this.previous = null;
        this.next = null;
    }

    public boolean isBlackend() {
        return this.value == 'x';
    }

    public boolean isUsed() {
        //return this.value == '_';
        return isBlackend() || isNumber() || next != null;
    }


    public int count() {
        return this.value - 48;
    }

    public boolean isVisitable(Cell comingFrom) {
        if(this.next == comingFrom) { return false; }
        return !isBlackend() && !isNumber() && (this.next == null || this.start);
    }

    Cell(int x, int y, char value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public Cell getNext() {
        return next;
    }

    public void setNext(Cell next) {
        System.out.println("NEXT from " + this + " = " + next);
        this.next = next;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public ArrayList<Cell> possibleValues(Board board) {
        ArrayList<Cell> possibru = new ArrayList<Cell>();

        Cell above = board.getCell(this.x, this.y-1);
        Cell below = board.getCell(this.x, this.y+1);
        Cell left = board.getCell(this.x-1, this.y);
        Cell right = board.getCell(this.x+1, this.y);

        if (above != null && above.isVisitable(this)) possibru.add(above);
        if (below != null && below.isVisitable(this)) possibru.add(below);
        if (left  != null && left .isVisitable(this)) possibru.add(left );
        if (right != null && right.isVisitable(this)) possibru.add(right);

        return possibru;
    }

    public void enter(){

    }

    public void leave(){
        this.next = null;
    }

    public ArrayList<Cell> getValidSurroundingCoords(Cell[][] board, boolean blackeningmode) {
        ArrayList<Cell> surroundingFields = new ArrayList<Cell>();
        if (x < board[0].length - 1) surroundingFields.add(board[x + 1][y]);
        if (y < board.length - 1) surroundingFields.add(board[x][y + 1]);
        if (x > 0) surroundingFields.add(board[x - 1][y]);
        if (y > 0) surroundingFields.add(board[x][y - 1]);
        this.removeInvalidCoords(surroundingFields, board, blackeningmode);
        return surroundingFields;
    }

    public boolean hasValidSurroundingCoords(Cell[][] board) {
        return this.getValidSurroundingCoords(board, false).size() > 0;
    }


    private void removeInvalidCoords(ArrayList<Cell> cells, Cell[][] board, boolean blackeningmode) {
        ArrayList<Cell> collectWrongCells = new ArrayList<Cell>();
        for (Cell c : cells) {
            //Remove Coords out of Range
            if (c.x < 0 || c.x > board[0].length ||
                    c.y < 0 || c.y > board.length) {
                collectWrongCells.add(c);
            }
            //Remove Coords that would overwrite Numbers or black Fields
            else if (board[c.x][c.y].getValue() == '1' ||
                    board[c.x][c.y].getValue() == '2' ||
                    board[c.x][c.y].getValue() == '0' ||
                    board[c.x][c.y].getValue() == 'x') {
                collectWrongCells.add(c);
            }
            //Remove Cells, that have already been used
            else if (board[c.x][c.y].next != null) {
                collectWrongCells.add(c);
            }
        }

        cells.removeAll(collectWrongCells);

        if (blackeningmode) {
            cells = this.removeUnfitCoordsForBlackening(cells, board);
        }

        /*//print ValidCoords:
        System.out.println("-----Valid Coords------");
        for (Cell c : cells)
            System.out.println("x: " + c.x + "y:" + c.y);*/
    }

    public ArrayList<Cell> removeUnfitCoordsForBlackening(ArrayList<Cell> cells, Cell[][] board) {
        //Remove Coords, that lie orthogonally to other black fields and therefore forbid this Cell
        //to be blackened
        ArrayList<Cell> collectWrongCells = new ArrayList<Cell>();
        for (Cell c : cells) {
            if (((c.x + 1) < board[0].length && board[c.x + 1][c.y].getValue() == 'x') ||
                    ((c.y + 1) < board.length && board[c.x][c.y + 1].getValue() == 'x') ||
                    ((c.x - 1) >= board[0].length && board[c.x - 1][c.y].getValue() == 'x') ||
                    ((c.y - 1) >= board.length && board[c.x][c.y - 1].getValue() == 'x')) {
                collectWrongCells.add(c);
            }
            cells.removeAll(collectWrongCells);
        }
        return cells;
    }


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
    public String toString(){
        return "Cell ("+ this.x + "|" + this.y + ")";
    }
}
