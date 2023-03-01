package todo;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;



public class NewList{
    static String listFile = "C:/Users/amace/Documents/vs code/app/ToDoList/src/todo/Lists/";

    private NewList() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static void list(){
        Stage primaryStage = new Stage();
        StackPane layout = new StackPane();

        Label label = new Label("Name of the list:");
        label.setTranslateY(-80);
        label.setTranslateX(-55);
        TextField name = new TextField("Name");
        name.setPrefWidth(150);
        name.setMaxWidth(200);
        name.setTranslateY(-55);

        //Create button, create a new list and verify if it already exists
        Button create = new Button("Create");
        create.setTranslateX(-50);
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                String content = name.getText();

                File newFile = new File(listFile + content);
                try{
                    if(newFile.createNewFile())
                    Alert.created();
                    else
                        Alert.error();
                }catch (IOException e) {
                    System.out.println("Error while creating new list");
                    e.printStackTrace();
                }
            }
        });

        //Cancel button, close the stage
        Button cancel = new Button("Cancel");
        cancel.setTranslateX(50);
        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                primaryStage.close();
            }
        });

        layout.getChildren().add(label);
        layout.getChildren().add(name);
        layout.getChildren().add(create);
        layout.getChildren().add(cancel);


        Scene scene = new Scene(layout, 300,150, Color.WHITE);
        primaryStage.setResizable(false);
        primaryStage.setTitle("New List");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
