package tech.gesp;

import java.io.File;
import java.util.List;

public class AutoPenTemplateCreator {

    public static void main(String[] args) {
        File signatureImageFile = new File("C:\\Users\\user\\Pictures\\signature_test.png");
        var imagePixels = ImageReader.readImage(signatureImageFile.toPath());

        FiveBar fiveBar = new FiveBar(500, 300, 150, 100, 130);

        imagePixels.forEach(pixelRow -> {
            pixelRow.forEach(pixel -> {
                System.out.print((pixel.isWhite() ? 0 : 1));
            });
            System.out.print("\n");
        });

        for (int row = 0; row < imagePixels.size(); row++) {
            List<Pixel> pixelRow = imagePixels.get(row);
            for (int column = 0; column < pixelRow.size(); column++) {
                Pixel pixel = pixelRow.get(column);
                if (!pixel.isWhite()) {
                    fiveBar.getCurrentPosition().update(column * 5, row + 500);
                    LeverAngles leverAngles = fiveBar.getLeverAngles();
                    System.out.println("Angle 1: " + leverAngles.getFirst() + ", Angle 2: " + leverAngles.getSecond());
                }
            }
        }
    }
}
