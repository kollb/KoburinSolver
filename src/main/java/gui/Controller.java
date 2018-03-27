package gui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import solver.Board;
import solver.Cell;
import solver.Solver;

public class Controller {


    @FXML
    private Pane pane;

    Board board;
    Solver solver;

    public void initialize() {
        System.out.println("Init vom Controller :)");
        board = new Board();
        solver = new Solver(board, "4x4", this);
    }

    @FXML
    public void clickStart4x4() {
        this.startThread("4x4");
    }

    @FXML
    public void clickStart4x4Random() {
        this.startThread("4x4R");
    }

    @FXML
    public void clickStart10x10() {
        this.startThread("10x10");
    }

    @FXML
    public void clickStart10x10Random() {
        this.startThread("10x10R");
    }

    public void startThread(String mode){
        Thread thread = new Thread(new Solver(this.board, mode, this));
        thread.setDaemon(true);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showBoard();
    }

    public void update(Board board){
        this.board = board;
        this.showBoard();
    }

    public void showBoard() {
        //Clear GUI of old Grids
        pane.getChildren().clear();

        int i = 0;
        for (int x = 0; x < this.board.getBoard().length; x++) {
            for (int y = 0; y < this.board.getBoard()[x].length; y++) {

                Cell cell = this.board.getCell(x, y);

                // Rectangles for white and black cells
                Rectangle rectangle = new Rectangle(x * 50, y * 50, 50, 50);
                rectangle.setFill(cell.isBlackend() ? Paint.valueOf("#111111") : Paint.valueOf("#ffffff00"));
                rectangle.setStroke(Paint.valueOf("#666666"));
                pane.getChildren().add(rectangle);

                // add numbers to the grid
                if (cell.isNumber()) {
                    Text text = new Text(x * 50 + 20, y * 50 + 25, String.valueOf(cell.getNumber()));
                    pane.getChildren().addAll(text);
                }

                // draw line of solution
                Cell next = cell.getNext();
                if (next != null) {
                    Line line = new Line(x * 50 + 25, y * 50 + 25, next.x * 50 + 25, next.y * 50 + 25);
                    pane.getChildren().add(line);
                }

            }
        }
    }


}
