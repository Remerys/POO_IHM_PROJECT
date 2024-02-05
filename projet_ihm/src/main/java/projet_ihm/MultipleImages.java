package projet_ihm;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MultipleImages extends ImageView {
    private int nbImages;
    private int nbImagesX;
    private int nbImagesY;
    private double width;
    private double height;
    private final int IMAGE_SIZE = 80;

    public MultipleImages(Image image) {
        super(image);
        this.calcul(image);
        this.setFitWidth(this.nbImagesX * this.IMAGE_SIZE);
        this.setFitHeight(this.nbImagesY * this.IMAGE_SIZE);
    }

    private void calcul(Image image) {
        width = image.getWidth();
        height = image.getHeight();
        this.nbImagesX = (int) (width / this.IMAGE_SIZE);
        this.nbImagesY = (int) (height / this.IMAGE_SIZE);
        this.nbImages = this.nbImagesX * this.nbImagesY;
    }

    public void setImageIndex(int i) {
        if (0 > i)
            i *= -1;
        i %= this.nbImages;
        int indexX = i % this.nbImagesX;
        int indexY = (i / this.nbImagesX) % this.nbImagesY;
        this.setViewport(
                new Rectangle2D(indexX * this.IMAGE_SIZE, indexY * this.IMAGE_SIZE, this.IMAGE_SIZE, this.IMAGE_SIZE));
    }

    public void changeImage(Image image) {
        super.setImage(image);
        this.calcul(image);
    }

}
