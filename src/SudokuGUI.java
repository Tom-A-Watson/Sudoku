import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SudokuGUI extends Application {

    @Override
    public void start(Stage stage) {
        mainGame(stage);
    }

    private void mainGame(Stage stage) {
        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();
        bp.setCenter(gp);
        Scene s = new Scene(bp);
        SudokuSolver solver = new SudokuSolver();
        Button b = new Button("Dark Mode");

        b.setPrefWidth(120);
        b.setPrefHeight(40);
        b.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        bp.setRight(b);
        gp.setPrefWidth(500);
        gp.setPrefHeight(500);

        for (int i = 0; i < solver.getBoardSize(); i++) {

            for (int j = 0; j < solver.getBoardSize(); j++) {
                if (solver.getBoard()[i][j] != 0) {
                    addBoardNumbers(gp, solver, i, j);
                    continue;
                }

                TextField inputs = getEmptyTextFields();
                gp.add(inputs, i, j);
            }
        }

        stage.setScene(s);
        stage.show();
    }

    private TextField getEmptyTextFields() {
        TextField inputSquare = new TextField();
        inputSquare.setMaxWidth(60);
        inputSquare.setPrefHeight(60);
        inputSquare.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        inputSquare.setAlignment(Pos.CENTER);
        return inputSquare;
    }

    private void addBoardNumbers(GridPane gp, SudokuSolver solver, int i, int j) {
        TextField boardNumber = new TextField();
        boardNumber.setMaxWidth(60);
        boardNumber.setPrefHeight(60);
        boardNumber.setFont(Font.font("Calibri", FontWeight.BOLD, 30));
        boardNumber.setText(String.valueOf(solver.getBoard()[i][j]));
        boardNumber.setAlignment(Pos.CENTER);
        boardNumber.setDisable(true);
        gp.add(boardNumber, i, j);
    }

}
