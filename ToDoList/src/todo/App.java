package todo;

import java.io.File;
import java.io.IOException;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException{

        int nbToDoList = count();
        String[] listName = listOfLists(nbToDoList);

        //button creating a new list
        Button btn = new Button();
        btn.setId("create-button");
        btn.setText("Create a new ToDoList");
        btn.setTranslateX(-180);
        btn.setTranslateY(-160);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event){
                System.out.println("Hello Wolrd!");
                NewList.list();
            }
        });
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        //button Quit
        Button quit = new Button();
        quit.setId("quit-button");
        quit.setText("Quit the app");
        quit.setTranslateX(210);
        quit.setTranslateY(-160);
        quit.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event){
                Platform.exit();
            }
        });
        root.getChildren().add(quit);
        

        Label label = new Label();
        label.setText("Your Lists");
        label.setFont(new Font("Arial",16));
        label.setTranslateX(-150);
        label.setTranslateY(-100);

        Separator separator = new Separator();
        separator.setTranslateY(-85);
        root.getChildren().add(label);
        root.getChildren().add(separator);

        //Calcul le nombre de lignes et colonnes pour la grille
        int nbColonnes = (int) Math.ceil(Math.sqrt(nbToDoList));
        int nbLignes = (int) Math.ceil((double)nbToDoList / nbColonnes);

        //Créer la grille pour les boutons
        GridPane grille = new GridPane();
        grille.setTranslateY(125);
        grille.setPadding(new Insets(10));
        grille.setHgap(10);
        grille.setVgap(10);

        //Créer les boutons
        int compteur=0;
        for(int ligne=0; ligne<nbLignes; ligne++){
            for(int colonne=0; colonne<nbColonnes; colonne++){
                if(compteur>=nbToDoList)
                    break;

                Button bouton = new Button(listName[compteur]);
                bouton.setId("list-button");
                bouton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event){
                        //Todo
                    }
                });
                grille.add(bouton, colonne, ligne);
                compteur++;
            }
        
        }
        root.getChildren().add(grille);



        //button updating the lists
        /*Button update = new Button();
        update.setText("Update lists");
        update.setTranslateX(200);
        update.setTranslateY(260);
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                
            }
        });
        root.getChildren().add(update);*/


        
        Scene scene = new Scene(root, 600, 400, Color.WHITE);
        scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
        primaryStage.setTitle("ToDoList");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        

       /* Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("To Do List");
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }

    public static int count(){
        int nbToDoList=0;
        File directory = new File("C:/Users/amace/Documents/vs code/app/ToDoList/src/todo/Lists");
        File[] list = directory.listFiles();
        for(File item : list){
            if(item.isFile()){
                System.out.format("File: %s%n", item.getName());
                nbToDoList++;
            }
        }
        return nbToDoList;
    }


    public static String[] listOfLists(int nbToDoList){
        File directory = new File("C:/Users/amace/Documents/vs code/app/ToDoList/src/todo/Lists");
        File[] list = directory.listFiles();
        int cur = 0;
        String[] listName = new String[nbToDoList];
        for(File item : list){
            if(item.isFile()){
                listName[cur] = item.getName();
                cur++;
            }
        }
        return listName;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
