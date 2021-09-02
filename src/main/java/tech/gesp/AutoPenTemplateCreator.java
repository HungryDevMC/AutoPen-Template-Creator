package tech.gesp;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import tech.gesp.autopen_translation.FiveBar;
import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.image_processing.ImageReader;
import tech.gesp.image_processing.PixelImage;
import tech.gesp.image_processing.VectorShape;
import tech.gesp.maths.Vector2D;
import tech.gesp.mold_creation.MoldCreationFactory;
import tech.gesp.mold_creation.StraightMoldTranslationImpl;
import tech.gesp.utils.GridPrinter;

import java.io.File;
import java.util.List;

@SpringBootApplication
@EnableAutoConfiguration
public class AutoPenTemplateCreator implements CommandLineRunner {

    @Autowired
    private MoldCreationFactory moldCreationFactory;

    public static void main(String[] args) {
        SpringApplication.run(AutoPenTemplateCreator.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        File signatureImageFile = new File("C:\\Users\\Balie2\\Documents\\AutoPen-Template-Creator\\src\\main\\resources\\lul.png");

        FiveBar fiveBar = new FiveBar(500, 300, 150, 100, 130);
        PixelImage pixelImage = new PixelImage(ImageReader.readImage(signatureImageFile.toPath()));

        List<Vector2D> blackPixelPositions = pixelImage.getAllBlackPixelPositions();
        VectorShape vectorShape = new VectorShape(blackPixelPositions);
        List<Vector2D> intersectionVectors = vectorShape.getAllIntersections(1);

        List<Vector2D> sortedPixelList = fiveBar.getSortedPixelImagePixelList(pixelImage);

        GridPrinter.printSortedDebuggingGrid(pixelImage, sortedPixelList);

        List<LeverAngles> leverAnglesForImage = fiveBar.generateLeverAnglesFromPixelImage(pixelImage);
        leverAnglesForImage.forEach(System.out::println);

        List<Vector2D> moldCoordinates = moldCreationFactory.create().translate(leverAnglesForImage);
        moldCoordinates.forEach(System.out::println);
    }
}
