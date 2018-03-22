import java.util.ArrayList;

public class Board {

    public Cell[][] board;
    private MersenneTwister random = new MersenneTwister();

    public void initialiseBoard() {
        this.board = new Cell[10][10];
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (x == 0 && y == 0
                        || x == 6 && y == 0
                        || x == 8 && y == 1
                        || x == 3 && y == 3
                        || x == 3 && y == 6
                        || x == 4 && y == 5
                        || x == 8 && y == 5)
                    board[x][y] = new Cell(x, y, '0');
                else if (  x == 3 && y == 0
                        || x == 1 && y == 5
                        || x == 4 && y == 9
                        || x == 8 && y == 7)
                    board[x][y] = new Cell(x, y, '1');
                else if (  x == 2 && y == 7
                        || x == 6 && y == 7
                        || x == 8 && y == 3)
                    board[x][y] = new Cell(x, y, '2');
                else
                    board[x][y] = new Cell(x, y, '_');
            }
        }
    }


    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= board[0].length || y >= board.length) {
            return null;
        }
        return board[x][y];
    }


    public void initialiseSmallBoard() {
        this.board = new Cell[4][4];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (y == 3 && x == 3)
                    board[x][y] = new Cell(x, y, '0');
                else if (y == 1 && x == 1)
                    board[x][y] = new Cell(x, y, '2');
                else
                    board[x][y] = new Cell(x, y, '_');
            }
        }
    }


    public void blackenSmallBoard() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (y == 1 && x == 2 ||
                        y == 2 && x == 1)
                    board[x][y] = new Cell(x, y, 'x');
            }
        }
        this.blackenRandomFields();
    }

    public void blackenAdjacentFields() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                //todo vllt liste holen,
                if (board[x][y].getValue() == '1') {
                    //blacken one field in surrounding
                    ArrayList<Cell> surroundingCells = board[x][y].possibleBlackeningCoords(this);
                    //System.out.println(surroundingCells);
                    Cell cell = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    board[cell.x][cell.y].setValue('x');
                } else if (board[x][y].getValue() == '2') {
                    //blacken two fields in surrounding
                    ArrayList<Cell> surroundingCells = board[x][y].possibleBlackeningCoords(this);
                    Cell cell1 = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    Cell cell2 = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    while (cell1 == cell2)
                        cell2 = surroundingCells.get(this.random.nextInt(surroundingCells.size()));
                    board[cell1.x][cell1.y].setValue('x');
                    board[cell2.x][cell2.y].setValue('x');
                }
            }
        }
        this.blackenRandomFields();
    }

    public void blackenHardCoded() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (y == 5 && x == 0
                        || y == 9 && x == 0
                        || y == 2 && x == 1
                        || y == 7 && x == 1
                        || y == 8 && x == 2
                        || y == 1 && x == 3
                        || y == 8 && x == 4
                        || y == 2 && x == 5
                        || y == 4 && x == 6
                        || y == 6 && x == 6
                        || y == 8 && x == 6
                        || y == 3 && x == 7
                        || y == 3 && x == 9
                        || y == 7 && x == 9
                        )
                    board[x][y] = new Cell(x, y, 'x');
            }
        }
    }


    public void blackenRandomFields() {
        int nmbrAdditionalBlackFields = this.random.nextInt(5);
        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board.length; y++) {
                if (board[x][y].getValue() == '_') {
                    if (this.random.nextInt(100) < nmbrAdditionalBlackFields) {
                        board[x][y] = new Cell(x,y, 'x');
                    }
                }
            }
        }
    }


    public Cell[][] getBoard() {
        return board;
    }


    public void printBoard() {
        System.out.println("x-länge: " + (this.board[0].length));
        System.out.println("y-länge: " + (this.board.length));
        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board[y].length; x++) {
                System.out.print(board[x][y].getValue() + " | ");
                if (x == this.board.length - 1) System.out.print("\n--------------------------------------- \n"); //
            }
        }
    }

    public void printBoardWithFollowers() {
        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board[y].length; x++) {
                if (board[x][y].getNext() != null) {
                    System.out.print(board[x][y].getNext().x + "-" + board[x][y].getNext().y + " | ");
                } else {
                    System.out.print("    | ");
                }
                if (x == this.board[y].length - 1) {
                    System.out.print("\n----------------------------------------------------------- \n");
                }
            }
        }
    }


}
