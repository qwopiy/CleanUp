package com.wi3uplus2.cleanup;

import javafx.scene.media.MediaPlayer;

public class AudioController {
    public static MediaPlayer musicPlayer = new MediaPlayer(AssetLoader.bgm);
    public static MediaPlayer boomPlayer = new MediaPlayer(AssetLoader.boom);

    public static double bgmVolume = 0.2; // Default background music volume
    public static double sfxVolume = 0.2; // Default boom sound effect volume

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
