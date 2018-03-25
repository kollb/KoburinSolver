package solver;

public class Board {

    public Cell[][] board;
    private MersenneTwister random = new MersenneTwister();

    public Cell getCell(int x, int y) {
        if (x < 0 || y < 0 || x >= board[0].length || y >= board.length) {
            return null;
        }
        return board[x][y];
    }


    public void initialise10x10Board() {
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
                else if (x == 3 && y == 0
                        || x == 1 && y == 5
                        || x == 4 && y == 9
                        || x == 8 && y == 7)
                    board[x][y] = new Cell(x, y, '1');
                else if (x == 2 && y == 7
                        || x == 6 && y == 7
                        || x == 8 && y == 3)
                    board[x][y] = new Cell(x, y, '2');
                else
                    board[x][y] = new Cell(x, y, '_');
            }
        }
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


    public void blacken4x4BoardHard() {
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
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[x][y].isNumber()) {
                    System.out.println("-- Blackening: " + board[x][y]);
                    board[x][y].doBlackening(this);
                }
            }
        }
        this.blackenRandomFields();

    }

    public void blacken10x10Hard() {
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
        double probability = 4 / 100;
        for (int x = 0; x < this.board.length; x++) {
            for (int y = 0; y < this.board.length; y++) {
                if (board[x][y].getValue() == '_') {
                    if (this.random.nextDouble(true, true) <= probability) {
                        System.out.println("----- Blackening random: " + board[x][y]);
                        board[x][y] = new Cell(x, y, 'x');
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board[y].length; x++) {
                sb.append(this.board[x][y].toString()).append("-");
            }
        }

        return sb.toString();
    }
}
