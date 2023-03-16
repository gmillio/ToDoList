package todo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class Preview {
    private Preview() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


    public static String preview(String nameList, String location){
        String res="";
        String file = location+"/"+nameList;        
        try {
            BufferedReader reader  = new BufferedReader(new FileReader(file));
            //lire uniquement les 4 premières lignes
            int lineNumber = 1;
            String line;
            int count = 0;
            while((line = reader.readLine()) != null && lineNumber <= 4){
                //Verifier si il y a un status
                StringReader stringReader = new StringReader(line);
                BufferedReader bufferedReader = new BufferedReader(stringReader);
                String verify;
                while((verify = bufferedReader.readLine()) != null){
                    //Il y a un status
                    if(verify.contains(",")){
                        String[] data = line.split(",");
                        if(data[1].length()>=15)
                            res += "    - " + data[1].substring(0,14) + " ...\n";
                        else 
                            res += "    - " + data[1] + "\n";
                    //Il n'y a pas de status
                    }else{
                        if(line.length()>=15)
                            res += "    - " + line.substring(0,14) + " ...\n";
                        else
                            res += "    - "+ line + "\n";
                    }
                }
                lineNumber++;
                count++;
            }
            if (count>=4)
                res += "    ...";
            else if(count==0)
                res +="     Nothing added\n\n\n\n";
            else for(int i=count; i<4; i++){
                res += "\n";
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
