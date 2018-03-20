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




}
