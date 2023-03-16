package todo;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AddTask {
    private AddTask() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


    static void addTask(GridPane grille, String listFile, String nameList){
        Stage primaryStage = new Stage();
        StackPane layout = new StackPane();

        Label label = new Label("New task:");
        label.setFont(new Font("Arial",16));
        label.setTranslateY(-50);
        label.setTranslateX(-40);
        TextField name = new TextField("Task Name");
        name.setFont(new Font("Arial",16));
        name.setPrefWidth(150);
        name.setMaxWidth(200);
        name.setTranslateY(-15);
        //set character limit to 10
        name.setPrefColumnCount(30);
        name.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length()>30)
                name.setText(oldValue);
        });

        //Create button, create a new list and verify if it already exists
        Button create = new Button("Create");
        create.setTranslateX(-50);
        create.setTranslateY(35);
        create.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                String content = name.getText();

                boolean good = addButton(content,grille, listFile, nameList);
                if(good)
                    Alert.created();
 
            }
        });

        //Cancel button, close the stage
        Button cancel = new Button("Cancel");
        cancel.setTranslateX(50);
        cancel.setTranslateY(35);
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
    


    static boolean addButton(String name, GridPane grille, String listFile, String nameList){
        boolean res = false;
        try {
            FileWriter writer = new FileWriter(listFile + nameList, true);
            writer.write("0,"+name+"\n");
            writer.close();

            grille.getChildren().clear();

            String[] tasks = ModifyList.listTasks(nameList);
            int nbLignes = tasks.length;
            ListGrid.createGrid(grille, nbLignes, tasks, listFile, nameList);
            res = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
