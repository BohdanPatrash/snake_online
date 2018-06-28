package bin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;


public class Main extends Application {

public static Stage window;
public static Scene menu;
public static String language;
public static String name;
public static String controller;
public static double volume;
public static double musicVolume;
public static Properties configProperties;
public static Properties languageProperties;


    @Override
    public void start(Stage primaryStage) throws Exception{
        menu = new Scene(FXMLLoader.load(getClass().getResource("fx/menu.fxml")), 440, 440);
        window = primaryStage;
        window.setScene(menu);
        window.getIcons().add(new Image("images/icon.png"));
        window.setX(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-window.getScene().getWidth()/2);
        window.setY(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2-window.getScene().getHeight()/2);
        window.setTitle(languageProperties.getProperty("windowName"));
        window.setResizable(false);
        window.show();
    }

    private static void getConfiguration(){
        FileInputStream fis;
        configProperties = new Properties();
        try {
            fis = new FileInputStream("src/bin/properties/config.properties");
            configProperties.load(fis);
            fis.close();

            language = configProperties.getProperty("language");
            name = configProperties.getProperty("name");
            controller = configProperties.getProperty("controller");
            volume = Double.parseDouble(configProperties.getProperty("volume"));
            musicVolume = Double.parseDouble(configProperties.getProperty("musicVolume"));

        } catch (IOException e) {
            System.err.println("Error, file does not exist");
        }
    }

    private static void getLanguage(){
        languageProperties = new Properties();
        try{
            languageProperties.load(new InputStreamReader(new FileInputStream("src/bin/properties/" + language + ".properties"),"UTF-8"));
        }catch (IOException e) {
            System.err.println("Error, file does not exist");
        }
    }

    public static void main(String[] args){
        getConfiguration();
        getLanguage();
        launch(args);
    }
}
