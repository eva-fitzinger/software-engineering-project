package at.jku.softengws20.group1.controlsystem.gui;

import at.jku.softengws20.group1.controlsystem.gui.viewmodel.CityTrafficMap;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * JavaFX at.jku.softengws20.group1.controlsystem.gui.App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        CityTrafficMap m = new CityTrafficMap();

        SplitPane s = new SplitPane();
        s.setOrientation(Orientation.HORIZONTAL);
        s.getItems().add(m.getPane());

        m.setOnRoadSegmentSelected(rs -> {
            if (rs != null) {
                //System.out.println(rs.getRoad().getName() + "  " + rs.getId());
            }
        });

        var scene = new Scene(m.getPane(), 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}