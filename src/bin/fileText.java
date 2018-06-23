package bin;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class fileText {

    private ArrayList<String> text;

    fileText(String fileName)throws IOException{
        Scanner in = new Scanner(new FileReader(fileName));
        text = new ArrayList<>();
        while(in.hasNext()) {
            text.add(in.nextLine());
        }
        in.close();
    }

    public ArrayList<String> getText(){
        return text;
    }

    public String getMeaning(int i){
        String result;
        int a = text.get(i).indexOf("=");
        result = text.get(i).substring(a+1);

        return result;
    }

}
