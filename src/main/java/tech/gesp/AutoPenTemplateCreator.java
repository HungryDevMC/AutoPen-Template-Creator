package tech.gesp;

import tech.gesp.autopen_translation.FiveBar;
import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.image_processing.ImageReader;
import tech.gesp.image_processing.PixelImage;
import tech.gesp.image_processing.VectorShape;
import tech.gesp.maths.Vector2D;
import tech.gesp.utils.GridPrinter;

import java.io.File;
import java.util.List;

public class AutoPenTemplateCreator {

    public static void main(String[] args) {
        File signatureImageFile = new File("C:\\Users\\user\\Pictures\\signature_test.png");

        FiveBar fiveBar = new FiveBar(500, 300, 150, 100, 130);
        PixelImage pixelImage = new PixelImage(ImageReader.readImage(signatureImageFile.toPath()));

        List<Vector2D> blackPixelPositions = pixelImage.getAllBlackPixelPositions();
        VectorShape vectorShape = new VectorShape(blackPixelPositions);
        List<Vector2D> intersectionVectors = vectorShape.getAllIntersections(1);

        GridPrinter.printDebuggingGrid(pixelImage, intersectionVectors);

        List<LeverAngles> leverAnglesForImage = fiveBar.generateLeverAnglesFromPixelImage(pixelImage);
        leverAnglesForImage.forEach(System.out::println);
    }
}
