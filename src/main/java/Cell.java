import java.util.ArrayList;

public class Cell {

    public int x;
    public int y;
    private char value;
    private Cell next;

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
        this.next = next;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public ArrayList<Cell> getValidSurroundingCoords(Cell[][] board) {
        ArrayList<Cell> surroundingFields =
                new ArrayList<Cell>();
        if (x < 9) surroundingFields.add(board[x + 1][y]);
        if (y < 9) surroundingFields.add(board[x][y + 1]);
        if (x > 0) surroundingFields.add(board[x - 1][y]);
        if (y > 0) surroundingFields.add(board[x][y - 1]);

        this.removeInvalidCoords(surroundingFields, board);
        return surroundingFields;
    }

    private void removeInvalidCoords(ArrayList<Cell> cells, Cell[][] board) {
        ArrayList<Cell> collectWrongCells = new ArrayList<Cell>();
        for (Cell c : cells) {
            //Remove Coords out of Range
            if (c.x < 0 || c.x > 9 ||
                    c.y < 0 || c.y > 9) {
                collectWrongCells.add(c);
            }
            //Remove Coords that would overwrite Numbers or black Fields
            else if (board[c.x][c.y].getValue() == '1' ||
                    board[c.x][c.y].getValue() == '2' ||
                    board[c.x][c.y].getValue() == '0' ||
                    board[c.x][c.y].getValue() == 'x') {
                collectWrongCells.add(c);
            }
            //Remove Coords, that lie orthogonally to other black fields and therefore forbid this Cell
            //to be blackened
            else if (((c.x + 1) < 10 && board[c.x + 1][c.y].getValue() == 'x') ||
                    ((c.y + 1) < 10 && board[c.x][c.y + 1].getValue() == 'x') ||
                    ((c.x - 1) >= 0 && board[c.x - 1][c.y].getValue() == 'x') ||
                    ((c.y - 1) >= 0 && board[c.x][c.y - 1].getValue() == 'x')) {
                collectWrongCells.add(c);
            }
        }
        cells.removeAll(collectWrongCells);

        /*//print ValidCoords:
        System.out.println("-----------");
        for (Cell c : cells)
            System.out.println("x: " + c.x + "y:" + c.y);*/
    }


    public boolean isNumber() {
        return this.value == '0'
                || this.value == '1'
                || this.value == '2';
    }

    public Cell next() {
        return next;
    }

}
