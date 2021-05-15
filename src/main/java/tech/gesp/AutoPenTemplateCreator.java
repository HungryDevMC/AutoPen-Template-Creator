package tech.gesp;

import tech.gesp.autopen_translation.FiveBar;
import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.image_processing.ImageReader;
import tech.gesp.image_processing.PixelImage;
import tech.gesp.maths.Vector2D;

import java.io.File;
import java.util.List;

public class AutoPenTemplateCreator {

    public static void main(String[] args) {
        File signatureImageFile = new File("C:\\Users\\user\\Pictures\\signature_test.png");

        FiveBar fiveBar = new FiveBar(500, 300, 150, 100, 130);
        PixelImage pixelImage = new PixelImage(ImageReader.readImage(signatureImageFile.toPath()));
        pixelImage.printGridValues();

        List<Vector2D> blackPixelPosition = pixelImage.getAllBlackPixelPositions();

        List<LeverAngles> leverAnglesForImage = fiveBar.generateLeverAnglesFromPixelImage(pixelImage);
        leverAnglesForImage.forEach(System.out::println);

    }
}
