package todo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ModifyList {
    int ligne=0;
    public ModifyList(String nameList, Stage mainStage){
        Stage primaryStage = new Stage();
        StackPane layout = new StackPane();
        String listFile = "C:/Users/amace/Documents/vs code/app/ToDoList/src/todo/Lists/";



        //Button back to menu
        Button button = new Button("Back to the menu");
        button.setId("create-button");
        button.setTranslateY(-250);
        button.setTranslateX(-200);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                primaryStage.close();
                mainStage.show();
            }
        });
        layout.getChildren().add(button);


        //Button save
        Button save = new Button("Save changes");
        save.setId("save-button");
        save.setTranslateY(-250);
        save.setTranslateX(-10);
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                primaryStage.close();
                mainStage.show();
            }
        });
        layout.getChildren().add(save);


        //button Quit
        Button quit = new Button();
        quit.setId("quit-button");
        quit.setText("Quit without saving");
        quit.setTranslateX(190);
        quit.setTranslateY(-250);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event){
                Platform.exit();
            }
        });
        layout.getChildren().add(quit);


        Label label = new Label();
        label.setText(nameList);
        label.setFont(new Font("Arial",16));
        label.setTranslateY(-180);

        Separator separator = new Separator();
        separator.setTranslateY(-165);
        layout.getChildren().add(label);
        layout.getChildren().add(separator);


        //Call listTasks() 
        String[] tasks = listTasks(nameList);
        

        //Calcul le nombre de lignes pour la grille
        int nbLignes = tasks.length;

        //Créer la grille pour les boutons
        GridPane grille = new GridPane();
        grille.setTranslateX(25);
        grille.setPadding(new Insets(10));
        grille.setHgap(10);
        grille.setVgap(10);

        ListGrid.createGrid(grille, nbLignes, tasks, listFile, nameList);
        
        
        // The scrolling panel
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(600, 300);
        scroll.setMaxHeight(460);
        scroll.setTranslateY(70);
        scroll.setStyle("-fx-background-color: transparent;");
        scroll.setContent(grille);
        layout.getChildren().add(scroll);

        Scene scene = new Scene(layout, 600,600, Color.WHITE);
        scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.setTitle(nameList);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    /**
     * 
     * @param nameList
     * @return a list of the content of each line
     */
    public static String[] listTasks(String nameList){
        //reads content of the file
        String line;
        int i=0;
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/amace/Documents/vs code/app/ToDoList/src/todo/Lists/"+nameList))) {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //writes the content of each line in a list
        String[] task= new String[i];
        try (BufferedReader br = new BufferedReader(new FileReader("C:/Users/amace/Documents/vs code/app/ToDoList/src/todo/Lists/"+nameList))) {
            int count=0;
            while ((line = br.readLine()) != null) {
                task[count]=line;
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return task;
    }

} 
