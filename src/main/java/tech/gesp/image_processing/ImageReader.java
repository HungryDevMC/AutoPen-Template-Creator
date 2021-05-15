package tech.gesp.image_processing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ImageReader {

    private static List<List<Pixel>> createPixelList(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        List<List<Pixel>> pixels = new ArrayList<>();
        for (int column = 0; column < height; column++) {
            List<Pixel> row = new ArrayList<>();
            for (int rowNumber = 0; rowNumber < width; rowNumber++) {
                int pixel = image.getRGB(rowNumber, column);
                row.add(new Pixel(pixel == -1));
            }
            pixels.add(row);
        }
        return pixels;
    }

    public static List<List<Pixel>> readImage(Path path) {
        try {
            BufferedImage image = ImageIO.read(path.toFile());
            return createPixelList(image);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

}
