package todo;

import java.io.File;
import java.io.IOException;

import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException{
       /** Button btn = new Button();
        btn.setText("Create a new to do list");
        btn.setTranslateX(-100);
        btn.setTranslateY(-100);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event){
                System.out.println("Hello Wolrd!");
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        int nbToDoList=0;

        File directory = new File("C:/Users/amace/Documents/vs code/app/ToDoList/src/todo/Lists");
        File[] list = directory.listFiles();
        int cur = 0;
        for(File item : list){
            if(item.isFile()){
                System.out.format("File: %s%n", item.getName());
                nbToDoList++;
            }
        }
        String[] listName = new String[nbToDoList];
        for(File item : list){
            if(item.isFile()){
                listName[cur] = item.getName();
                cur++;
            }
        }

        Button[] btnExist = new Button[nbToDoList];
        for(int i=0; i<nbToDoList; i++){
            btnExist[i] = new Button();
            btnExist[i].setText(listName[i]);
            root.getChildren().add(btnExist[i]);
        }
        


        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        **/



        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("To Do List");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
