package tech.gesp.utils;

import tech.gesp.image_processing.PixelImage;
import tech.gesp.maths.Vector2D;

import java.util.List;

public class GridPrinter {

    public static void printDebuggingGrid(PixelImage pixelImage, List<Vector2D> intersections) {
        pixelImage.getPixels().forEach(pixelRow -> {
            pixelRow.forEach(pixel -> {
                String toPrint = "0";
                if (!pixel.isWhite()) {
                    String prefixColor = intersections.contains(pixel.getPosition()) ? ConsoleColor.ANSI_BRIGHT_RED : ConsoleColor.ANSI_BLUE;
                    toPrint = prefixColor + "1" + ConsoleColor.ANSI_RESET;
                }
                System.out.print(toPrint);
            });
            System.out.print("\n");
        });
    }

}
