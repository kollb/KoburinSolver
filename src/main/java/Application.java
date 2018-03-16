import java.util.ArrayList;

public class Application {
    private Cell[][] board;
    private MersenneTwister random = new MersenneTwister();

    Application() {

    }

    void run() {
        this.initialiseBoard();
        this.blackenAdjacentFields();
        this.printBoard();
        this.
    }

    private boolean trackComplete(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j].getValue() == '_') return false;
            }
        }

        int x = 0;
        int y = 0;
        //detect Loop in Track
        while(board[x][y].isNumber()){
            x+=1;
        }
        Cell firstCell = board[x][y];
        Cell nextCell = firstCell.next();

        for (int i = 0; i<100; i++){
            if (firstCell == nextCell) return true;
            if (nextCell.next() == null) return false;
            nextCell = nextCell.next();
        }
        return true;
    }


    private void initialiseBoard() {
        this.board = new Cell[10][10];
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (y == 0 && x == 0
                        || y == 6 && x == 0
                        || y == 8 && x == 1
                        || y == 3 && x == 3
                        || y == 3 && x == 6
                        || y == 4 && x == 5
                        || y == 8 && x == 5)
                    board[x][y] = new Cell(x, y, '0');
                else if (y == 3 && x == 0
                        || y == 1 && x == 5
                        || y == 4 && x == 9
                        || y == 8 && x == 7)
                    board[x][y] = new Cell(x, y, '1');
                else if (y == 2 && x == 7
                        || y == 6 && x == 7
                        || y == 8 && x == 3)
                    board[x][y] = new Cell(x, y, '2');
                else
                    board[x][y] = new Cell(x, y, '_');
            }
        }
    }

    private void printBoard() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                System.out.print(board[x][y].getValue() + " | ");
                if (y == 9) System.out.print("\n--------------------------------------- \n");
            }
        }
    }

    private void blackenAdjacentFields() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (board[x][y].getValue() == '1') {
                    //blacken one field in surrounding
                    Cell cell = new Cell(x, y, ' ');
                    ArrayList<Cell> surroundingCells = cell.getValidSurroundingCoords(this.board);
                    cell = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    this.board[cell.x][cell.y].setValue('x');
                } else if (board[x][y].getValue() == '2') {
                    //blacken two fields in surrounding
                    Cell cell = this.board[x][y];
                    ArrayList<Cell> surroundingCells = cell.getValidSurroundingCoords(this.board);
                    cell = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    Cell cell2 = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    while (cell == cell2)
                        cell2 = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    this.board[cell.x][cell.y].setValue('x');
                    this.board[cell2.x][cell2.y].setValue('x');
                }
            }
        }
        this.blackenRandomFields();
    }

    private void blackenRandomFields() {
        int nmbrAdditionalBlackFields = this.random.nextInt(5);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (this.board[x][y].getValue() == '_') {
                    if (this.random.nextInt(100) < nmbrAdditionalBlackFields) {
                        this.board[x][y].setValue('x');
                    }
                }
            }
        }
    }


}
