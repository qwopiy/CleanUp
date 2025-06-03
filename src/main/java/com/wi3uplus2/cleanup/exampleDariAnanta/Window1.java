package com.wi3uplus2.cleanup.exampleDariAnanta;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Window1 {

    public void tampilkan(Stage stage, MainApp app) {
        // Kontainer utama horizontal
        HBox root = new HBox(20);
        root.setPadding(new Insets(20));

        // === Bagian Kiri: Scrollable List ===
        VBox daftarKonten = new VBox(10);
        daftarKonten.setPadding(new Insets(10));

        // Tambahkan beberapa contoh item (gambar + teks)
        for (int i = 1; i <= 10; i++) {
            HBox item = new HBox(10);
            Label lbl1 = new Label("Game ke-" + i);
            Label lbl2 = new Label("jumlah Mati:");
//            ImageView img = new ImageView(new Image("https://via.placeholder.com/50"));
            item.getChildren().addAll(lbl1, lbl2);
            daftarKonten.getChildren().add(item);
        }

        ScrollPane scrollPane = new ScrollPane(daftarKonten);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(250);

        // === Bagian Kanan: Gambar utama ===
        ImageView gambarUtama = new ImageView(new Image("https://via.placeholder.com/400x300"));
        gambarUtama.setPreserveRatio(true);
        gambarUtama.setFitWidth(400);

        VBox kanan = new VBox(gambarUtama);
        kanan.setPadding(new Insets(10));

        // Gabungkan ke dalam root
        root.getChildren().addAll(scrollPane, kanan);

        // Tombol kembali ke menu
        Label kembali = new Label("← Kembali ke menu");
        kembali.setStyle("-fx-text-fill: blue; -fx-underline: true; -fx-cursor: hand;");
        kembali.setOnMouseClicked(e -> app.showMainMenu());

        VBox layout = new VBox(10, kembali, root);

        Scene scene = new Scene(layout, 800, 500);
        stage.setTitle("Window 1");
        stage.setScene(scene);
    }
}


