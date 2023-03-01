package todo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Observable;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ModifyList {
    private ModifyList() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public ModifyList(String nameList, Stage mainStage){
        Stage primaryStage = new Stage();
        StackPane layout = new StackPane();


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
        

        //Calcul le nombre de lignes et colonnes pour la grille
        int nbColonnes = 2;
        int nbLignes = tasks.length;

        //Créer la grille pour les boutons
        GridPane grille = new GridPane();
        grille.setTranslateY(150);
        grille.setTranslateX(25);
        grille.setPadding(new Insets(10));
        grille.setHgap(10);
        grille.setVgap(10);

        int compteur=0;
        for(int ligne=0; ligne<nbLignes; ligne++){
            if(compteur>=tasks.length)
                break;

            CheckBox check = new CheckBox();
            Label lblTask = new Label(tasks[compteur]);
            lblTask.setFont(new Font("Arial",16));
            check.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue){
                    Text text = new Text(lblTask.getText());
                    text.setFill(Color.GRAY);
                    text.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 16));
                    lblTask.setGraphic(text);
                    lblTask.setText("");
                }else{
                    lblTask.setTextFill(Color.BLACK);
                    lblTask.setText(((Text) lblTask.getGraphic()).getText());
                    lblTask.setGraphic(null);
                }
            });
            compteur++;
            grille.add(check, 1, ligne);
            grille.add(lblTask, 2, ligne);
        }
        layout.getChildren().add(grille);

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
    private String[] listTasks(String nameList){
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
