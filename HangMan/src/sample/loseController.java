package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class loseController {
    public Button tryBut;
    public Button quitBut;
    public Label word;

    public void setWord(String word1) {
        word.setText(word1);
    }

    void loadWindow (String location, String title) {
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void quit(ActionEvent actionEvent) {
        Stage stage = (Stage) quitBut.getScene().getWindow();
        stage.close();
    }

    public void tryAgain(ActionEvent actionEvent) {
        loadWindow("./sample.fxml", "Game");
        Stage stage = (Stage) tryBut.getScene().getWindow();
        stage.close();
    }
}
