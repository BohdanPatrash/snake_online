package bin;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class fileText {

    private String[] text;

    fileText(String fileName)throws IOException{
        Scanner in = new Scanner(new FileReader(fileName));
        this.text = new String[5];
        int i = 0;
        while(in.hasNext()) {
            text[i] = in.next();
            i++;
        }
        in.close();
    }

    public String[] getText(){
        return text;
    }

    public String getMeaning(int i){
        String result;
        int a = text[i].indexOf("=");
        result = text[i].substring(a+1);

        return result;
    }

}
