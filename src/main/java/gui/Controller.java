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

    // TODO BUTTONS in HBOX: Start 4x4 (mit fertigen schwarzen), Start 10x10 (mit richtigen schwarzen), Start 10x10 (refreshren mit zufälligen schwarzen), Start 10x10 while not finished new blacks

    Board board;
    Solver solver;

    @FXML
    public void clickStart4x4() {
        solver.reset();
        board.initialiseSmallBoard();
        board.blacken4x4BoardHard();
        board.printBoard();
        solver.solve(0, 0);
        showBoard();
    }

    @FXML
    public void clickStart4x4Random() {
        boolean solutionFound;
        solver.reset();
        /*solver.Application application = new solver.Application();
        application.run();*/
        do {
            board.initialiseSmallBoard();
            board.blackenAdjacentFields();
            board.printBoard();
            solver.setCounter(0);
            solutionFound = solver.solve(0, 0);
            board.printBoard();
            showBoard();
        } while (!solutionFound);
    }

    @FXML
    public void clickStart10x10() {
        solver.reset();
        board.initialise10x10Board();
        board.blacken10x10Hard();
        board.printBoard();
        solver.solve(0, 1);
        showBoard();
    }

    @FXML
    public void clickStart10x10Random() {
        boolean solutionFound;
        solver.reset();
        do {
            board.initialise10x10Board();
            board.blackenAdjacentFields();
            board.printBoard();
            solver.setCounter(0);
            solutionFound = solver.solve(0, 1);
            board.printBoard();
            showBoard();
        } while (!solutionFound);
    }

    public void initialize() {
        System.out.println("Init vom Controller :)");
        board = new Board();
        solver = new Solver(board);
        board.initialiseSmallBoard();
        board.blackenAdjacentFields();
        board.printBoard();
        showBoard();
    }

    public void showBoard() {

        pane.getChildren().clear();

        int i = 0;
        for (int x = 0; x < this.board.getBoard().length; x++) {
            for (int y = 0; y < this.board.getBoard()[x].length; y++) {

                Cell cell = this.board.getCell(x, y);

                // RECHTECK für RAHMEN und SCHWARZ

                Rectangle rectangle = new Rectangle(x * 50, y * 50, 50, 50);

                rectangle.setFill(cell.isBlackend() ? Paint.valueOf("#111111") : Paint.valueOf("#ffffff00"));

                rectangle.setStroke(Paint.valueOf("#666666"));

                pane.getChildren().add(rectangle);

                // ZAHL

                if (cell.isNumber()) {
                    Text text = new Text(x * 50 + 20, y * 50 + 25, String.valueOf(cell.getNumber()));
                    pane.getChildren().addAll(text);
                }

                Cell next = cell.getNext();

                if (next != null) {
                    Line line = new Line(x * 50 + 25, y * 50 + 25, next.x * 50 + 25, next.y * 50 + 25);
                    pane.getChildren().add(line);
                }

            }
        }


    }

}
