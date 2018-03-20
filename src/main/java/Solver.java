import java.util.ArrayList;

public class Solver {
    private MersenneTwister random = new MersenneTwister();

    public boolean trackComplete(Cell[][] board) {
        for (Cell[] row : board) {
            for (Cell field : row) {
                if (field.getValue() == '_') return false;
            }
        }

        int x = 0;
        int y = 0;
        //detect Loop in Track
        while (board[x][y].isNumber()) {
            x += 1;
        }
        Cell firstCell = board[x][y];
        Cell nextCell = firstCell.getNext();

        for (int i = 0; i < board.length * board[0].length; i++) {
            ArrayList<Cell> surroundingCells = new ArrayList<Cell>();
            if (nextCell.getNext() == null)
            {   if (nextCell.x < board[0].length - 1) surroundingCells.add(board[x + 1][y]);
                if (nextCell.y < board.length - 1) surroundingCells.add(board[x][y + 1]);
                if (nextCell.x > 0) surroundingCells.add(board[x - 1][y]);
                if (nextCell.y > 0) surroundingCells.add(board[x][y - 1]);
                for (Cell c : surroundingCells){
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


    public boolean drawLine(int x, int y, Cell[][] board) {
        System.out.println("Aktuelle Zelle: x: " + x + " y: " + y);
        if (trackComplete(board)) return true;

        Cell cell = board[x][y];
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


    public void blackenAdjacentFields(Cell[][] board) {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (board[x][y].getValue() == '1') {
                    //blacken one field in surrounding
                    ArrayList<Cell> surroundingCells = board[x][y].getValidSurroundingCoords(board, true);
                    board[x][y].removeUnfitCoordsForBlackening(surroundingCells, board);
                    Cell cell = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    board[cell.x][cell.y].setValue('x');
                } else if (board[x][y].getValue() == '2') {
                    //blacken two fields in surrounding
                    ArrayList<Cell> surroundingCells = board[x][y].getValidSurroundingCoords(board, true);
                    board[x][y].removeUnfitCoordsForBlackening(surroundingCells, board);
                    Cell cell1 = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    Cell cell2 = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    while (cell1 == cell2)
                        cell2 = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    board[cell1.x][cell1.y].setValue('x');
                    board[cell2.x][cell2.y].setValue('x');
                }
            }
        }
        this.blackenRandomFields(board);
    }


    public void blackenRandomFields(Cell[][] board) {
        int nmbrAdditionalBlackFields = this.random.nextInt(5);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (board[x][y].getValue() == '_') {
                    if (this.random.nextInt(100) < nmbrAdditionalBlackFields) {
                        board[x][y].setValue('x');
                    }
                }
            }
        }
    }


}
