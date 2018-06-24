package bin;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Music {

    static InputStream music;
    static AudioStream audio;

    public static void PlayMusic(String name){
        try{
            music = new FileInputStream(new File(name));
            audio = new AudioStream(music);
            AudioPlayer.player.start(audio);

        }catch (Exception e){}
    }

    public static void StopMusic(){
        AudioPlayer.player.stop(audio);
    }

}
