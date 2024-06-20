package sigmabank.controllers;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public abstract class BaseController<T> {
    protected Stage stage;
    protected T object;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setObject(T object) {
        this.object = object;
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
    public static <C> BaseController<C> loadView(String viewName, String viewTitle, BaseController<C> context, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(context.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        context.stage.setScene(new Scene(loader.load()));
        context.stage.setTitle(viewTitle);
        
        BaseController<C> controller = loader.getController();
        controller.setStage(context.stage);
        controller.setObject(object);
        controller.initData();

        context.stage.show();

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

    public static <C> Parent getView(String viewName, BaseController context, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(context.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        Parent result = loader.load();

        BaseController<C> controller = loader.getController();
        controller.setStage(context.stage);
        controller.setObject(object);
        controller.initData();
        
        return result;
    }

    public abstract void initData() throws IOException;

    public static <C> void openModal(String viewName, String viewTitle, BaseController<C> context, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(context.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        final Stage newStage = new Stage();
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.initStyle(StageStyle.UNDECORATED);
        newStage.setResizable(false);
        newStage.initOwner(context.stage);
    
        newStage.setScene(new Scene(loader.load()));
        newStage.setTitle(viewTitle);
        ((AnchorPane)loader.getRoot()).setStyle("-fx-border-color: black; -fx-border-width: 1;");

        BaseController<C> controller = loader.getController();
        controller.setStage(newStage);
        controller.setObject(object);
        controller.initData();

        newStage.show();
    }
}
