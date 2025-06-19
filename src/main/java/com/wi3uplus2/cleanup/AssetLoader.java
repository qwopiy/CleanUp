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

    public static Media bgm;
    public static Media boom;
    public static Media clickSFX;
    public static Media winSFX;
    public static Media loseSFX;

    public static void init() {
        try {
            bgm = new Media(Objects.requireNonNull(
                    AssetLoader.class.getResource("/com/wi3uplus2/cleanup/assets/sounds/bgm1.mp3"),
                    "Missing resource: bgm1.mp3"
            ).toExternalForm());

            boom = new Media(Objects.requireNonNull(
                    AssetLoader.class.getResource("/com/wi3uplus2/cleanup/assets/sounds/sfx/vine-boom.mp3"),
                    "Missing resource: vine-boom.mp3"
            ).toExternalForm());

            clickSFX = new Media(Objects.requireNonNull(
                    AssetLoader.class.getResource("/com/wi3uplus2/cleanup/assets/sounds/sfx/click.mp3"),
                    "Missing resource: click.mp3"
            ).toExternalForm());

            winSFX = new Media(Objects.requireNonNull(
                    AssetLoader.class.getResource("/com/wi3uplus2/cleanup/assets/sounds/sfx/winSFX.mp3"),
                    "Missing resource: winSFX.mp3"
            ).toExternalForm());

            loseSFX = new Media(Objects.requireNonNull(
                    AssetLoader.class.getResource("/com/wi3uplus2/cleanup/assets/sounds/sfx/loseSFX.mp3"),
                    "Missing resource: loseSFX.mp3"
            ).toExternalForm());

            organicTrashBin = new Image(Objects.requireNonNull(
                    AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_trashCan.png"),
                    "Missing resource: organik_trashCan.png"
            ));

            organicTrash = new Image[]{
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_apple.png"),
                            "Missing resource: organik_apple.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_banana.png"),
                            "Missing resource: organik_banana.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_dryLeaf.png"),
                            "Missing resource: organik_dryLeaf.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/organik_stick.png"),
                            "Missing resource: organik_stick.png"
                    ))
            };

            inorganicTrashBin = new Image(Objects.requireNonNull(
                    AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_trashCan.png"),
                    "Missing resource: anorganik_trashCan.png"
            ));

            inorganicTrash = new Image[]{
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_plasticBag.png"),
                            "Missing resource: anorganik_plasticBag.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_plasticBottle.png"),
                            "Missing resource: anorganik_plasticBottle.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_snackPack.png"),
                            "Missing resource: anorganik_snackPack.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/anorganik_sodaCan.png"),
                            "Missing resource: anorganik_sodaCan.png"
                    ))
            };

            B3TrashBin = new Image(Objects.requireNonNull(
                    AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_trashCan.png"),
                    "Missing resource: B3_trashCan.png"
            ));

            B3Trash = new Image[]{
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_Battery.png"),
                            "Missing resource: B3_Battery.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_brokenGlass.png"),
                            "Missing resource: B3_brokenGlass.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_brokenLamp.png"),
                            "Missing resource: B3_brokenLamp.png"
                    )),
                    new Image(Objects.requireNonNull(
                            AssetLoader.class.getResourceAsStream("/com/wi3uplus2/cleanup/assets/images/object/B3_poison.png"),
                            "Missing resource: B3_poison.png"
                    ))
            };
        } catch (Exception e) {
            System.err.println("Error initializing assets: " + e.getMessage());
            e.printStackTrace();
        }
    }
}