package sigmabank.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseController<T> {
    protected Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static <C> BaseController<C> loadView(String viewName, String viewTitle, BaseController<C> context, C object) throws IOException {
        FXMLLoader loader = new FXMLLoader(context.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));

        context.stage.setScene(new Scene(loader.load()));
        context.stage.setTitle(viewTitle);
        BaseController<C> controller = loader.getController();
        controller.setStage(context.stage);
        controller.initData(object);

        context.stage.show();

        return controller;
    }

    public static <C> BaseController<C> loadView(URL view, String viewTitle, Stage currentStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(view);

        currentStage.setScene(new Scene(loader.load()));
        currentStage.setTitle(viewTitle);

        BaseController<C> controller = loader.getController();
        controller.setStage(currentStage);

        currentStage.show();

        return controller;
    }

    public static <C> Parent getView(String viewName, BaseController<C> context, Map<String, Object> properties) throws IOException {
        FXMLLoader loader = new FXMLLoader(context.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));
        properties.forEach((k, v) -> {
            loader.getNamespace().put(k, v);
        });

        return loader.load();
    }

    public abstract void initData(T object) throws IOException;
}
