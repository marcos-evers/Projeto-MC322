package sigmabank.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class BaseController<T> {
    protected Stage stage;
    protected T object;
    protected Object additionalData;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    /**
     * This method is used when there is initial data to be setted on the
     * called view (stored on the `object` property of the returned
     * controller).
     * 
     * @param <C> the type of the object that is bounded with the view
     * @param viewName the name of the view to be showed
     * @param viewTitle the title to be assigned to the scene
     * @param object the object of type C to be bounded with the scene
     * @return the controller associated with the view being showed after the
     * method ends
     * @throws IOException
     */
    public <C> BaseController<C> loadView(String viewName, String viewTitle, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        this.stage.setScene(new Scene(loader.load()));
        this.stage.setTitle(viewTitle);
        
        BaseController<C> controller = loader.getController();
        controller.setStage(this.stage);
        controller.object = object;
        controller.initData();

        this.stage.show();

        return controller;
    }

    /**
     * This method is used when the current context isn't from a BaseController
     * class child
     * 
     * @param <C> the type of the object that is bounded with the view
     * @param view the URL object of the view file to be shown
     * @param viewTitle the title to be assigned to the scene
     * @param stage the current stage being shown
     * @return the controller associated with the view being showed after the
     * method ends
     * @throws IOException
     */
    public static <C> BaseController<C> loadView(URL view, String viewTitle, Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(view);

        stage.setScene(new Scene(loader.load()));
        stage.setTitle(viewTitle);

        BaseController<C> controller = loader.getController();
        controller.setStage(stage);

        stage.show();

        return controller;
    }

    /**
     * Returns the root element of the specified view
     * 
     * @param <C> the type of the object that is bounded with the view
     * @param viewName the name of the view to be returned
     * @param object the object of type C to be bounded to the view
     * @return the root element of the specified view, with data already
     * initialised
     * @throws IOException
     */
    public <C> Parent getView(String viewName, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        Parent result = loader.load();

        BaseController<C> controller = loader.getController();
        controller.setStage(this.stage);
        controller.object = object;
        controller.initData();
        
        return result;
    }

    /**
     * Returns the root element of the specified view, passing an additional
     * data object
     * 
     * @param <C> the type of the object that is bounded with the view
     * @param viewName the name of the view to be returned
     * @param object the object of type C to be bounded to the view
     * @param data the additional data to be bounded to the view
     * @return the root element of the specified view, with data already
     * initialised
     * @throws IOException
     */
    public <C> Parent getView(String viewName, C object, Object data) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        Parent result = loader.load();

        BaseController<C> controller = loader.getController();
        controller.setStage(this.stage);
        controller.object = object;
        controller.additionalData = data;
        controller.initData();
        
        return result;
    }
    
    private Stage initModalStage() {
        final Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setResizable(false);
        newStage.initOwner(this.stage);

        return newStage;
    }

    private static <C> BaseController<C> setModalOnStage(Stage stage, FXMLLoader loader, String viewTitle, C object, Object data) throws IOException {
        stage.setScene(new Scene(loader.load()));
        stage.setTitle(viewTitle);
        ((AnchorPane)loader.getRoot()).setStyle("-fx-border-color: black; -fx-border-width: 1;");

        BaseController<C> controller = loader.getController();
        controller.setStage(stage);
        controller.object = object;
        controller.additionalData = data;
        controller.initData();

        stage.show();

        return controller;
    }

    /**
     * Opens the specified view as a modal, not able to access the previous
     * stage.
     * 
     * @param <C> the type of the object that is bounded with the view
     * @param viewName the name of the view to be opened
     * @param viewTitle the title to be assigned to the scene
     * @param object the object of type C to be bounded to the view
     * @throws IOException
     */
    public <C> void openModal(String viewName, String viewTitle, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));
        Stage newStage = initModalStage();
    
        setModalOnStage(newStage, loader, viewTitle, object, null);
    }

    /**
     * Opens the specified view as a modal, not able to access the previous
     * stage. Passes `data` as additional data to the view.
     * 
     * @param <C> the type of the object that is bounded with the view
     * @param viewName the name of the view to be opened
     * @param viewTitle the title to be assigned to the scene
     * @param object the object of type C to be bounded to the view
     * @param data the additional data to be bounded to the view
     * @throws IOException
     */
    public <C> void openModal(String viewName, String viewTitle, C object, Object data) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));
        Stage newStage = initModalStage();
    
        setModalOnStage(newStage, loader, viewTitle, object, data);
    }

    /**
     * An abstract method for initialising the view's data, such as texts or
     * data queries.
     * 
     * @throws IOException
     */
    public abstract void initData() throws IOException;

    /**
     * Exhibits a error dialog with the specified message, with an "OK" button
     * to dismiss
     * 
     * @param message the message to be exhibited
     */
    public static void errorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Algo deu errado:");
        alert.setContentText(message);
        alert.show();
    }
}
