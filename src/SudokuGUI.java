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
        Button b = new Button();

        bp.setBottom(b);
        gp.setPrefWidth(400);
        gp.setPrefHeight(400);

        for(int i = 0; i < solver.getBoardSize(); i++) {

            for(int j = 0; j < solver.getBoardSize(); j++) {
                if (solver.getBoard()[i][j] != 0) {
                    TextField textField = new TextField();

                    textField.setMaxWidth(50);
                    textField.setPrefHeight(50);
                    textField.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
                    textField.setText(String.valueOf(solver.getBoard()[i][j]));
                    textField.setAlignment(Pos.CENTER);
                    textField.setDisable(true);
                    gp.add(textField, i, j);

                    continue;
                }

                TextField empty = new TextField();

                empty.setMaxWidth(50);
                empty.setPrefHeight(50);
                empty.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
                empty.setAlignment(Pos.CENTER);

                gp.add(empty, i, j);
            }
        }



        stage.setScene(s);
        stage.show();
    }

}
