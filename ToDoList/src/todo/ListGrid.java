package todo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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

    static void createGrid(GridPane grille, int nbLignes, String[] tasks, String listFile, String nameList){
        int compteur=0;
        for( ligne=0; ligne<nbLignes; ligne++){
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
                    binButton(grille, nbLignes, tasks, listFile, nameList, ligne);
                }
            });
            //-----------------------------------------------------------------------------
            compteur++;
            grille.add(check, 1, ligne);
            grille.add(lblTask, 2, ligne);
            grille.add(bin, 3, ligne);
        }
    }






    static void binButton(GridPane grille, int nbLignes, String[] tasks, String listFile, String nameList, int ligne){
        try {
            File inputFile = new File(listFile + nameList);
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            File tempFile = new File(listFile + "tempo");
            FileOutputStream fileOutputStream;
            fileOutputStream = new FileOutputStream(tempFile);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);

            String line;
            int lineNumber = 1;
            int i = 0;
            String[] newTasks = new String[nbLignes-1];// a modifier
            while ((line = bufferedReader.readLine()) != null) {

                if (lineNumber != ligne) {
                    printWriter.println(line);
                    newTasks[i] = line;
                }
                lineNumber++;
                i++;
            }
            bufferedReader.close();
            printWriter.close();
            fileInputStream.close();
            fileOutputStream.close();
            
            // Supprimez le fichier original et renommez le fichier temporaire
            if (inputFile.delete()) {
                tempFile.renameTo(inputFile);
            }

            grille.getChildren().clear();

            //newTasks = ModifyList.listTasks(nameList);
            tasks = ModifyList.listTasks(nameList);
            nbLignes = tasks.length;

            createGrid(grille, nbLignes, tasks, listFile, nameList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
