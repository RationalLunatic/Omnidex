package main;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by shaev_000 on 7/26/2016.
 */
public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ApplicationMaster appMaster = new ApplicationMaster(primaryStage);
        appMaster.go();
    }

}
