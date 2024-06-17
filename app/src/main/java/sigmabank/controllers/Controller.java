package sigmabank.controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

public abstract class Controller implements Initializable {
    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
