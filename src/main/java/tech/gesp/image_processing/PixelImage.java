package tech.gesp.image_processing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tech.gesp.maths.Vector2D;
import tech.gesp.utils.ConsoleColor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class PixelImage {

    private List<List<Pixel>> pixels;

    public List<Vector2D> getAllBlackPixelPositions() {
        List<Vector2D> blackPixelPositions = new ArrayList<>();
        for (int row = 0; row < getPixels().size(); row++) {
            List<Pixel> pixelRow = getPixels().get(row);
            for (int column = 0; column < pixelRow.size(); column++) {
                Pixel pixel = pixelRow.get(column);
                if (!pixel.isWhite()) {
                    blackPixelPositions.add(pixel.getPosition());
                }
            }
        }
        return blackPixelPositions;
    }
}
