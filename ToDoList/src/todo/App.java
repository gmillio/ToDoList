package todo;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javafx.application.*;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class App extends Application{

    @Override
    public void start(Stage primaryStage) throws IOException{

        int nbToDoList = count();
        String[][] listName = listOfLists(nbToDoList);

        //button creating a new list
        Button btn = new Button();
        btn.setId("create-button");
        btn.setText("Create a new ToDoList");
        btn.setTranslateX(-180);
        btn.setTranslateY(-160);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event){
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
        grille.setPadding(new Insets(10));
        grille.setHgap(10);
        grille.setVgap(10);


        // The scrolling panel
        ScrollPane scroll = new ScrollPane();
        scroll.setPrefSize(600, 300);
        scroll.setMaxHeight(320);
        scroll.setTranslateY(80);
        scroll.setStyle("-fx-background-color: transparent;");
        scroll.setContent(grille);
        root.getChildren().add(scroll);

        //Créer les boutons
        int compteur=0;
        for(int ligne=0; ligne<nbLignes; ligne++){
            for(int colonne=0; colonne<nbColonnes; colonne++){
                if(compteur>=nbToDoList)
                    break;

                Text text1 = new Text(listName[compteur][0]);
                Text text2 = new Text(listName[compteur][1]); 
                Text text3 = new Text(listName[compteur][2]);
                text1.setFont(Font.font(19));
                text2.setFont(Font.font(11));
                text3.setFont(Font.font(9));
                VBox vbox = new VBox(text1, text2, text3);

    
                Button bouton = new Button();
                bouton.setGraphic(vbox);
                bouton.setId("list-button");
                bouton.setMinHeight(145);
                String name = listName[compteur][0];
                bouton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event){
                        try {
                            ModifyList test = new ModifyList(name, primaryStage);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        primaryStage.close();
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


        
        Scene scene = new Scene(root, 600, 500, Color.WHITE);
        scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
        primaryStage.setTitle("ToDoList");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
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


    public static String[][] listOfLists(int nbToDoList){
        File directory = new File("C:/Users/amace/Documents/vs code/app/ToDoList/src/todo/Lists");
        File[] list = directory.listFiles();
        int cur = 0;
        String[][] res = new String[nbToDoList][3];
        for(File item : list){
            if(item.isFile()){
                //list name
                res[cur][0] = item.getName();
                //preview of content
                res[cur][1] = Preview.preview(res[cur][0], directory.getAbsolutePath());
                //last modification
                long lastModified = item.lastModified();
                Instant instant = Instant.ofEpochMilli(lastModified);
                LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' HH:mm");
                String time = dateTime.format(formatter);
                res[cur][2] = "last mod: "+ time;
                cur++;
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
