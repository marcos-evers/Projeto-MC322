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

    public void setObject(T object) {
        this.object = object;
    }

    public void setAdditionalData(Object data) {
        this.additionalData = data;
    }

    /**
     * Used when there is initial data to be setted on the called view.
     * 
     * @param <C>
     * @param viewName
     * @param viewTitle
     * @param context
     * @param object
     * @return
     * @throws IOException
     */
    public <C> BaseController<C> loadView(String viewName, String viewTitle, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        this.stage.setScene(new Scene(loader.load()));
        this.stage.setTitle(viewTitle);
        
        BaseController<C> controller = loader.getController();
        controller.setStage(this.stage);
        controller.setObject(object);
        controller.initData();

        this.stage.show();

        return controller;
    }

    /**
     * Used when there is no initial data to be setted on the called view;
     * 
     * @param <C>
     * @param view
     * @param viewTitle
     * @param stage
     * @return
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

    public <C> Parent getView(String viewName, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        Parent result = loader.load();

        BaseController<C> controller = loader.getController();
        controller.setStage(this.stage);
        controller.setObject(object);
        controller.initData();
        
        return result;
    }

    public <C> Parent getView(String viewName, C object, Object data) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        Parent result = loader.load();

        BaseController<C> controller = loader.getController();
        controller.setStage(this.stage);
        controller.setObject(object);
        controller.setAdditionalData(data);
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
        controller.setObject(object);
        controller.setAdditionalData(data);
        controller.initData();

        stage.show();

        return controller;
    }

    public <C> void openModal(String viewName, String viewTitle, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));
        Stage newStage = initModalStage();
    
        setModalOnStage(newStage, loader, viewTitle, object, null);
    }

    public <C> void openModal(String viewName, String viewTitle, C object, Object data) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));
        Stage newStage = initModalStage();
    
        setModalOnStage(newStage, loader, viewTitle, object, data);
    }

    public abstract void initData() throws IOException;

    public static void errorDialog(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Algo deu errado:");
        alert.setContentText(message);
        alert.show();
    }
}
