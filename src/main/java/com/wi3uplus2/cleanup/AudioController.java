package com.wi3uplus2.cleanup;

import javafx.scene.media.MediaPlayer;

import java.sql.SQLException;

public class AudioController {
    public static MediaPlayer musicPlayer = new MediaPlayer(AssetLoader.bgm);
    public static MediaPlayer boomPlayer = new MediaPlayer(AssetLoader.boom);

    public static double bgmVolume; // Default background music volume
    public static double sfxVolume; // Default boom sound effect volume

    static {
        try {
            bgmVolume = DatabaseHandler.getVolume("bgm");
            sfxVolume = DatabaseHandler.getVolume("sfx");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void startBGM() {
        musicPlayer.seek(musicPlayer.getStartTime());
        musicPlayer.setVolume(bgmVolume); // Set volume to 50%
        musicPlayer.play();
    }

    public static void vineBoom() {
        boomPlayer.seek(boomPlayer.getStartTime());
        boomPlayer.setVolume(sfxVolume); // Set volume to 50%
        boomPlayer.play();
    }
}
