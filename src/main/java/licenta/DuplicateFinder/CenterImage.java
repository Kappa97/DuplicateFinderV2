package licenta.DuplicateFinder;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CenterImage {
    public void centerImageInImageView(Image image, ImageView imageView) {
        if (image != null) {
            double widthOfImage = 0;
            double heightOfImage = 0;

            double ratioX = imageView.getFitWidth() / image.getWidth();
            double ratioY = imageView.getFitHeight() / image.getHeight();
            double ratioCoefficient = 0;
            if (ratioX >= ratioY) {
                ratioCoefficient = ratioY;
            } else {
                ratioCoefficient = ratioX;
            }

            widthOfImage = image.getWidth() * ratioCoefficient;
            heightOfImage = image.getHeight() * ratioCoefficient;

            imageView.setX((imageView.getFitWidth() - widthOfImage) / 2);
            imageView.setY((imageView.getFitHeight() - heightOfImage) / 2);
        }
    }
}
