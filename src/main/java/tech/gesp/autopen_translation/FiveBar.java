package tech.gesp.autopen_translation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import tech.gesp.configuration.PhysicalConfiguration;
import tech.gesp.image_processing.Pixel;
import tech.gesp.image_processing.PixelImage;
import tech.gesp.maths.TrigonometryUtil;
import tech.gesp.maths.Vector2D;

import java.util.*;

@Getter
public class FiveBar {

    private Vector2D currentPosition;
    private double smallerToLeverAngle;
    private double topLength;
    private double bottomLength;
    private double leverLength;
    private double hingeDistance;
    private double startingOffsetX = 0;
    private double startingOffsetY = 0;

    public FiveBar(double topLength, double bottomLength, double leverLength, double hingeDistance, double smallerToLeverAngle) {
        this.topLength = topLength;
        this.bottomLength = bottomLength;
        this.leverLength = leverLength;
        this.hingeDistance = hingeDistance;
        this.smallerToLeverAngle = smallerToLeverAngle;
        this.currentPosition = new Vector2D(0, 0);
    }

    public void setStartingOffsets(double offsetX, double offsetY) {
        this.startingOffsetX = offsetX;
        this.startingOffsetY = offsetY;
    }

    public double getMaxRange() {
        return this.topLength;
    }

    public Vector2D getHypothenuseFirst(Vector2D realPosition) {
        return new Vector2D(realPosition.getXComponent() + (hingeDistance / 2), realPosition.getYComponent());
    }

    public Vector2D getHypothenuseSecond(Vector2D realPosition) {
        return new Vector2D(realPosition.getXComponent() - (hingeDistance / 2), realPosition.getYComponent());
    }

    public LeverAngles getLeverAngles(Vector2D realPosition) {
        Vector2D h1Vector = getHypothenuseFirst(realPosition);
        Vector2D h2Vector = getHypothenuseSecond(realPosition);
        double h1Length = Math.sqrt(TrigonometryUtil.square(h1Vector.getXComponent()) + TrigonometryUtil.square(h1Vector.getYComponent()));
        double h2Length = Math.sqrt(TrigonometryUtil.square(h2Vector.getXComponent()) + TrigonometryUtil.square(h2Vector.getYComponent()));

        double angleL1 = getLeverAngle(h1Length, h2Length);
        double angleL2 = getLeverAngle(h2Length, h1Length);
        return new LeverAngles(angleL1, angleL2);
    }

    public double getLeverAngle(double h1Length, double h2Length) {
        double angleH1 = TrigonometryUtil.getAcosAngle(h1Length, bottomLength, topLength);
        double angleH1O1 = TrigonometryUtil.getAcosAngle(hingeDistance, h1Length, h2Length);
        double angleO1 = 360 - angleH1 - angleH1O1 - smallerToLeverAngle;
        return angleO1 - 90;
    }

    public List<Vector2D> getAllBlackPixelPosition(PixelImage pixelImage) {
        List<Vector2D> blackPixelPositions = new ArrayList<>();
        for (int row = 0; row < pixelImage.getPixels().size(); row++) {
            List<Pixel> pixelRow = pixelImage.getPixels().get(row);
            for (int column = 0; column < pixelRow.size(); column++) {
                Pixel pixel = pixelRow.get(column);
                if (!pixel.isWhite()) {
                    blackPixelPositions.add(new Vector2D(row, column));
                }
            }
        }
        return blackPixelPositions;
    }
}
