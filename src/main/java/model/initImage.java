package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class initImage {

    public StackPane getImageView30(String imageName, Color backgroundColor) {
        String imagePath = "/images/" + imageName + ".png";
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        Rectangle background = new Rectangle(40, 40); // taille légèrement plus grande
        background.setArcWidth(10); // coins arrondis
        background.setArcHeight(10);
        background.setFill(backgroundColor);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(background, imageView);

        return stackPane;
    }

}
