import java.util.ArrayList;

public class Solver {
    private MersenneTwister random = new MersenneTwister();
    private int counter;

    private final Board board;

    public Solver(Board board) {
        this.board = board;
    }

    public boolean trackComplete(Board board) {
        Cell[][] cells = board.getBoard();
        for (Cell[] row : cells) {
            for (Cell field : row) {
                if (field.getValue() == '_') return false;
            }
        }

        int x = 0;
        int y = 0;
        //detect Loop in Track
        while (cells[x][y].isNumber()) {
            x += 1;
        }
        Cell firstCell = cells[x][y];
        Cell nextCell = firstCell.getNext();

        for (int i = 0; i < cells.length * cells[0].length; i++) {
            ArrayList<Cell> surroundingCells = new ArrayList<Cell>();
            if (nextCell.getNext() == null) {
                if (nextCell.x < cells[0].length - 1) surroundingCells.add(cells[x + 1][y]);
                if (nextCell.y < cells.length - 1) surroundingCells.add(cells[x][y + 1]);
                if (nextCell.x > 0) surroundingCells.add(cells[x - 1][y]);
                if (nextCell.y > 0) surroundingCells.add(cells[x][y - 1]);
                for (Cell c : surroundingCells) {
                    if (c.getNext() != null)
                        System.out.println("Fertig");
                    return true;
                }

            }
            if (nextCell.getNext() == null) {
                return false;
            }
            nextCell = nextCell.getNext();
        }
        return true;
    }

    public boolean finalValidation(Board board) {
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

    public boolean solve(int x, int y, Board board) {
        return solve(board.getCell(x, y), board);
    }


    public boolean solve(Cell currentCell, Board board) {
        //Cell currentCell = board.getCell(x, y);

        if (currentCell.isStart()) {
            System.out.println(currentCell);
            return finalValidation(board);
            //return trackComplete(board);
        }

        if (counter == 0) {
            currentCell.setStart(true);
        }

        currentCell.enter();
        ArrayList<Cell> possibleCells = currentCell.possibleValues(board);
        System.out.println(currentCell + " ?> " + possibleCells);
        for (Cell c : possibleCells) {
            currentCell.link(c);
            counter++;
            if (solve(c, board)) {
                return true;
            }
            c.unlink();
        }

        currentCell.leave();

        return false;
    }


    public boolean drawLine(int x, int y, Cell[][] board) {
        Cell cell = board[x][y];
        if (cell.isStart()) {
            //return finalValidation();
            //return trackComplete(board);
        }

        //System.out.println("Aktuelle Zelle: x: " + x + " y: " + y);
        //if (trackComplete(board)) return true;

        ArrayList<Cell> possibleCells = cell.getValidSurroundingCoords(board, false);

        System.out.println("-----Valid Coords------");
        for (Cell c : possibleCells)
            System.out.println("x: " + c.x + "y:" + c.y);

        for (Cell c : possibleCells) {
            cell.setNext(c);
            board[c.x][c.y].setValue('e');
            if (c.hasValidSurroundingCoords(board)) {
                if (drawLine(c.x, c.y, board)) return true;
            }
            System.out.println("break out");
            c.setNext(null);
        }
        return false;
    }


}
