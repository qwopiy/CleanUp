package com.wi3uplus2.cleanup;

import javafx.scene.image.Image;
import javafx.scene.media.Media;

import java.util.Objects;

public class AssetLoader {
    public static Image[] bg;
    public static Image[] character;

    public static Image[] organicTrash;
    public static Image[] inorganicTrash;
    public static Image[] B3Trash;

    public static Image organicTrashBin;
    public static Image inorganicTrashBin;
    public static Image B3TrashBin;

    public static Media bgm = new Media(
            AudioController.class.getResource("/com/wi3uplus2/cleanup/assets/sounds/laguWibu.mp3").toExternalForm()
    );

    public static Media boom = new Media(
            AudioController.class.getResource("/com/wi3uplus2/cleanup/assets/sounds/sfx/vine-boom.mp3").toExternalForm()
    );

    // tambah asset lainnya sesuai kebutuhan
    public static void init() {
        organicTrashBin = new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_trashCan.png")));
        organicTrash = new Image[]{
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_apple.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_banana.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_dryLeaf.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_stick.png")))
        };

        inorganicTrashBin = new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_trashCan.png")));
        inorganicTrash = new Image[]{
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_plasticBag.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_plasticBottle.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_snackPack.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_sodaCan.png")))
        };

        B3TrashBin = new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_trashCan.png")));
        B3Trash = new Image[]{
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_Battery.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_brokenGlass.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_brokenLamp.png"))),
                new Image(Objects.requireNonNull(AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_poison.png")))
        };
    }
}
