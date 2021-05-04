package tech.gesp;

import java.io.File;

public class AutoPenTemplateCreator {

    public static void main(String[] args) {
        File signatureImageFile = new File("C:\\Users\\user\\Pictures\\signature_test.png");
        var imagePixels = ImageReader.readImage(signatureImageFile.toPath());

        imagePixels.forEach(pixelRow -> {
            pixelRow.forEach(pixel -> {
                System.out.print((pixel.isWhite() ? 0 : 1));
            });
            System.out.print("\n");
        });
    }
}
