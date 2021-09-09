package tech.gesp.utils;

import tech.gesp.image_processing.Pixel;
import tech.gesp.image_processing.PixelImage;
import tech.gesp.maths.Vector2D;

import java.util.List;
import java.util.Vector;

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

    public static void printSortedDebuggingGrid(PixelImage pixelImage, List<Vector2D> sortedPixelList) {
        int count = 0;
        for (List<Pixel> pixelRow : pixelImage.getPixels()) {
            for (Pixel pixel : pixelRow) {
                String toPrint = "0";
                if (!pixel.isWhite()) {
                    String prefixColor = ConsoleColor.ANSI_BLUE;
                    toPrint = "" + prefixColor + findSortedPositionOfPixel(pixel, sortedPixelList) + ConsoleColor.ANSI_RESET;
                    count++;
                }
                System.out.print(toPrint);
            }
            System.out.print("\n");
        }
    }

    private static int findSortedPositionOfPixel(Pixel pixel, List<Vector2D> sortedPixelList) {
        int position = -1;
        for (int sortedListPosition = 0; sortedListPosition < sortedPixelList.size(); sortedListPosition++) {
            Vector2D sortedPixelPosition = sortedPixelList.get(sortedListPosition);
            if(sortedPixelPosition.equals(pixel.getPosition())) {
                position = sortedListPosition;
            }
        }
        return position;
    }

}
