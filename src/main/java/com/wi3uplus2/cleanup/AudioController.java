package com.wi3uplus2.cleanup;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.sql.SQLException;

public class AudioController {
    public static MediaPlayer musicPlayer = new MediaPlayer(AssetLoader.bgm);
    public static MediaPlayer boomPlayer = new MediaPlayer(AssetLoader.boom);
    public static MediaPlayer clickPlayer = new MediaPlayer(AssetLoader.clickSFX);
    public static MediaPlayer winPlayer = new MediaPlayer(AssetLoader.winSFX);
    public static MediaPlayer losePlayer = new MediaPlayer(AssetLoader.loseSFX);

    public static double bgmVolume;
    public static double sfxVolume;

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
        musicPlayer.setVolume(bgmVolume);
        musicPlayer.setOnEndOfMedia(() -> musicPlayer.seek(Duration.ZERO));
        musicPlayer.play();
    }

    public static void vineBoom() {
        boomPlayer.seek(boomPlayer.getStartTime());
        boomPlayer.setVolume(sfxVolume);
        boomPlayer.play();
    }

    public static void click() {
        clickPlayer.seek(clickPlayer.getStartTime());
        clickPlayer.setVolume(sfxVolume);
        clickPlayer.play();
    }

    public static void win() {
        winPlayer.seek(winPlayer.getStartTime());
        winPlayer.setVolume(sfxVolume);
        winPlayer.play();
    }

    public static void lose() {
        losePlayer.seek(losePlayer.getStartTime());
        losePlayer.setVolume(sfxVolume);
        losePlayer.play();
    }
}
