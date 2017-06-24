package main;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.custombindings.DirectDoubleBinding;

/**
 * Created by shaev_000 on 5/8/2016.
 */
public class ApplicationMaster {
    public static final double INITIAL_WIDTH = 800;
    public static final double INITIAL_HEIGHT = 600;

    private Stage mainStage;
    private Scene mainScene;

    public ApplicationMaster(Stage stage) {
        mainStage = stage;

        DoubleProperty widthProperty = new SimpleDoubleProperty(INITIAL_WIDTH);
        DoubleProperty heightProperty = new SimpleDoubleProperty(INITIAL_HEIGHT);

        mainScene = new Scene(new MainView(new DirectDoubleBinding(widthProperty), new DirectDoubleBinding(heightProperty)), INITIAL_WIDTH, INITIAL_HEIGHT);
        mainStage.setScene(mainScene);

        widthProperty.bind(mainStage.widthProperty());
        heightProperty.bind(mainStage.heightProperty());
    }

    public void go() {
        mainStage.show();
    }

}
