package tech.gesp;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.gesp.autopen_translation.FiveBar;
import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.configuration.PhysicalConfiguration;
import tech.gesp.image_processing.ImageReader;
import tech.gesp.image_processing.PixelImage;
import tech.gesp.image_processing.PixelImageToVectorConverter;
import tech.gesp.maths.Vector2D;
import tech.gesp.mold_creation.MoldCreationFactory;
import tech.gesp.mold_creation.xls.VectorListToXlsGenerator;
import tech.gesp.utils.GridPrinter;

import java.io.File;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class AutoPenTemplateCreator implements CommandLineRunner {

    private final MoldCreationFactory moldCreationFactory;
    private final VectorListToXlsGenerator vectorListToXlsGenerator;
    private final PhysicalConfiguration physicalConfiguration;
    private final PixelImageToVectorConverter pixelImageToVectorConverter;

    public static void main(String[] args) {
        SpringApplication.run(AutoPenTemplateCreator.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        File signatureImageFile = new File("C:\\Users\\user\\Documents\\AutoPen Template Creator\\src\\main\\resources\\lul.png");

        FiveBar fiveBar = new FiveBar(physicalConfiguration.getLongLegLength(), physicalConfiguration.getShortLegLength(), physicalConfiguration.getLeverLength(),
                physicalConfiguration.getHingeDistance(), physicalConfiguration.getAngleBetweenLeverAndShortLeg());
        PixelImage pixelImage = new PixelImage(ImageReader.readImage(signatureImageFile.toPath()));

        List<Vector2D> sortedPixelList = pixelImageToVectorConverter.sortByDrawingOrder(pixelImage);
        GridPrinter.printSortedDebuggingGrid(pixelImage, sortedPixelList);

        List<LeverAngles> moldLeverAngles = pixelImageToVectorConverter.generateMoldLeverAngles(fiveBar, pixelImage);
        moldLeverAngles.forEach(System.out::println);

        List<Vector2D> moldCoordinates = moldCreationFactory.create().translate(moldLeverAngles);
        moldCoordinates.forEach(System.out::println);

        vectorListToXlsGenerator.generate(moldCoordinates, signatureImageFile.getParent());
    }
}
