package com.wi3uplus2.cleanup;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioController {
    private static MediaPlayer musicPlayer = new MediaPlayer(AssetLoader.bgm);
    private static MediaPlayer boomPlayer = new MediaPlayer(AssetLoader.boom);

    public static void startBGM() {
        musicPlayer.seek(musicPlayer.getStartTime());
        musicPlayer.setVolume(0.5); // Set volume to 50%
        musicPlayer.play();
    }

    public static void vineBoom() {
        boomPlayer.seek(boomPlayer.getStartTime());
        boomPlayer.setVolume(0.5); // Set volume to 50%
        boomPlayer.play();
    }
}
