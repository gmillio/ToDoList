package todo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class Alert {
    private Alert() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void created(){
        Stage primaryStage = new Stage();
        StackPane layout = new StackPane();

        Label label = new Label("List created successfully");
        label.setTranslateY(-25);

        Button button = new Button("Ok");
        button.setTranslateY(5);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                primaryStage.close();
            }
        });
        layout.getChildren().add(label);
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 200,70, Color.WHITE);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Alert");
        primaryStage.setScene(scene);
        primaryStage.show();
    }    

    public static void error(){
        Stage primaryStage = new Stage();
        StackPane layout = new StackPane();

        Label label = new Label("List already exists");
        label.setTranslateY(-25);

        Button button = new Button("Ok");
        button.setTranslateY(5);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                primaryStage.close();
            }
        });
        layout.getChildren().add(label);
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 200,70, Color.WHITE);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Alert");
        primaryStage.setScene(scene);
        primaryStage.show();
    }    
}
