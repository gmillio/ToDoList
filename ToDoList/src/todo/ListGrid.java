package todo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class ListGrid {
    static int ligne=0;

    private ListGrid() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static void createGrid(GridPane grille, int nbLignes, String[] tasks, String listFile, String nameList) throws IOException{
        int compteur=0;
        for( ligne=0; ligne<nbLignes; ligne++){
            if(compteur>=tasks.length)
                break;

            CheckBox check = new CheckBox();
            Label lblTask = new Label();

            //Verifier si il y a un status
            StringReader stringReader = new StringReader(tasks[compteur]);
            BufferedReader bufferedReader = new BufferedReader(stringReader);
            String verify;
            while((verify = bufferedReader.readLine()) != null){
                //Il y a un status
                if(verify.contains(",")){
                    String[] data = tasks[compteur].split(",");
                    String isCheck = data[0];
                    lblTask.setText(data[1]);
                    lblTask.setFont(new Font("Arial",16));
                    //Regarde le status
                    if(isCheck.equals("1")){
                        check.setSelected(true);
                        Text text = new Text(lblTask.getText());
                        text.setFill(Color.GRAY);
                        text.setFont(Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, 16));
                        lblTask.setGraphic(text);
                        lblTask.setText("");
                    }
                //Il n'y a pas de status
                }else{
                    lblTask.setText(tasks[compteur]);
                    lblTask.setFont(new Font("Arial",16));
                    lblTask.setGraphic(null);
                }
            }

            
            check.selectedProperty().addListener((observable, oldValue, newValue) -> {
                //Change la forme du texte selon le status de la checkBox
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
            //----------------------------------------------------------------------------
            //-------------------Bin button--------------------
            Image img = new Image("/todo/Images/bin.jpg");
            ImageView view = new ImageView(img);
            view.setFitHeight(19);
            view.setPreserveRatio(true);
            Button bin = new Button("",view);
            bin.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            bin.setOnMouseEntered(event ->{
                bin.setStyle("-fx-background-color: #cfcfcf;");
            });
            bin.setOnMouseExited(event ->{
                bin.setStyle("-fx-background-color: transparent;");
            });
            bin.setOnAction(new EventHandler<ActionEvent>() {
            
                @Override
                public void handle(ActionEvent event){
                    binButton(grille, listFile, nameList, ligne);
                }
            });
            //-----------------------------------------------------------------------------
            compteur++;
            grille.add(check, 1, ligne);
            grille.add(lblTask, 2, ligne);
            grille.add(bin, 3, ligne);
        }


        //Button add
        Image img2 = new Image("/todo/Images/plus.png");
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(25);
        view2.setPreserveRatio(true);
        Button add = new Button("New task",view2);
        add.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        add.setOnMouseEntered(event ->{
           add.setStyle("-fx-background-color: #cfcfcf;");
        });
        add.setOnMouseExited(event ->{
           add.setStyle("-fx-background-color: transparent;");
        });
        add.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event){
                AddTask.addTask( grille,  listFile,  nameList);
            }
        });
        grille.add(add, 1, ligne+1);
    }



    static void binButton(GridPane grille, String listFile, String nameList, int ligne){
        try {
            File inputFile = new File(listFile + nameList);
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            FileWriter tempo = new FileWriter(listFile + "tempo", true);
            File newFile = new File(listFile + "tempo");

            String line;
            int lineNumber = 1;
            while ((line = bufferedReader.readLine()) != null) {
                if (lineNumber != ligne) {
                    tempo.write(line+"\n");
                }
                lineNumber++;
            }
            bufferedReader.close();
            fileInputStream.close();
            tempo.close();
            
            //Supprime le fichier original et renomme le fichier temporaire
            if (inputFile.delete()) {
                newFile.renameTo(inputFile);
            }
            grille.getChildren().clear();

            String[] tasks = ModifyList.listTasks(nameList);
            int nbLignes = tasks.length;

            createGrid(grille, nbLignes, tasks, listFile, nameList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
