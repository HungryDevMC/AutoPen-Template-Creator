package tech.gesp.autopen_translation;

import lombok.Getter;
import tech.gesp.image_processing.Pixel;
import tech.gesp.image_processing.PixelImage;
import tech.gesp.maths.TrigonometryUtil;
import tech.gesp.maths.Vector2D;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class FiveBar {

    private Vector2D currentPosition;
    private double smallerToLeverAngle;
    private double topLength;
    private double smallerLength;
    private double leverLength;
    private double hingeDistance;

    public FiveBar(double topLength, double smallerLength, double leverLength, double hingeDistance, double smallerToLeverAngle) {
        this.topLength = topLength;
        this.smallerLength = smallerLength;
        this.leverLength = leverLength;
        this.hingeDistance = hingeDistance;
        this.smallerToLeverAngle = smallerToLeverAngle;
        this.currentPosition = new Vector2D(0, 0);
    }

    public Vector2D getHypothenuseFirst() {
        return new Vector2D(currentPosition.getXComponent() + (hingeDistance / 2), currentPosition.getYComponent());
    }

    public Vector2D getHypothenuseSecond() {
        return new Vector2D(currentPosition.getXComponent() - (hingeDistance / 2), currentPosition.getYComponent());
    }

    public LeverAngles getLeverAngles() {
        Vector2D h1Vector = getHypothenuseFirst();
        Vector2D h2Vector = getHypothenuseSecond();
        double h1Length = Math.sqrt(TrigonometryUtil.square(h1Vector.getXComponent()) + TrigonometryUtil.square(h1Vector.getYComponent()));
        double h2Length = Math.sqrt(TrigonometryUtil.square(h2Vector.getXComponent()) + TrigonometryUtil.square(h2Vector.getYComponent()));

        double angleL1 = getLeverAngle(h1Length, h2Length);
        double angleL2 = getLeverAngle(h2Length, h1Length);
        return new LeverAngles(angleL1, angleL2);
    }

    public double getLeverAngle(double h1Length, double h2Length) {
        double angleH1 = TrigonometryUtil.getAcosAngle(h1Length, smallerLength, topLength);
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

    public List<LeverAngles> generateLeverAnglesFromPixelImage(PixelImage pixelImage) {
        return convertPixelsToLeverAngles(getSortedPixelImagePixelList(pixelImage));
    }

    public List<Vector2D> getSortedPixelImagePixelList(PixelImage pixelImage) {
        List<Vector2D> blackPixelsList = new ArrayList<>();
        for (int row = 0; row < pixelImage.getPixels().size(); row++) {
            List<Pixel> pixelRow = pixelImage.getPixels().get(row);
            for (int column = 0; column < pixelRow.size(); column++) {
                Pixel pixel = pixelRow.get(column);
                if (!pixel.isWhite()) {
                    blackPixelsList.add(pixel.getPosition());
                }
            }
        }

        return sortPixelVectorList(blackPixelsList);
    }

    private LinkedList<Vector2D> sortPixelVectorList(List<Vector2D> pixelVectorList) {
        LinkedList<Vector2D> sortedlist = new LinkedList<>();

        Optional<Vector2D> optionalReferencePixel = findStartingPixelShape(pixelVectorList);
        while (optionalReferencePixel.isPresent()) {
            Vector2D referencePixel = optionalReferencePixel.get();
            sortedlist.add(referencePixel);
            pixelVectorList.remove(referencePixel);
            Optional<Vector2D> optionalClosestDistancePixel = findClosestDistancePixel(referencePixel, pixelVectorList);

            while (optionalClosestDistancePixel.isPresent() && optionalClosestDistancePixel.get().distance(referencePixel) < 2) {
                Vector2D closestDistancePixel = optionalClosestDistancePixel.get();
                sortedlist.add(closestDistancePixel);
                pixelVectorList.remove(closestDistancePixel);

                referencePixel = closestDistancePixel;
                optionalClosestDistancePixel = findClosestDistancePixel(referencePixel, pixelVectorList);
            }

            optionalReferencePixel = findStartingPixelShape(pixelVectorList);
        }
        return sortedlist;
    }

    private Optional<Vector2D> findClosestDistancePixel(Vector2D referencePixel, List<Vector2D> remainingPixelVectorList) {
        if (remainingPixelVectorList.isEmpty()) return Optional.empty();
        Vector2D closestPixelToReference = remainingPixelVectorList.get(0);
        for (Vector2D remainingPixel : remainingPixelVectorList) {
            if (referencePixel.distance(closestPixelToReference) > referencePixel.distance(remainingPixel)) {
                closestPixelToReference = remainingPixel;
            } else if (referencePixel.distance(closestPixelToReference) == referencePixel.distance(remainingPixel) && closestPixelToReference.getYComponent() < remainingPixel.getYComponent()) {
                closestPixelToReference = remainingPixel;
            }
        }

        return Optional.of(closestPixelToReference);
    }

    private Optional<Vector2D> findStartingPixelShape(List<Vector2D> remainingPixelVectorList) {
        if (remainingPixelVectorList.isEmpty()) return Optional.empty();
        Vector2D startingPixel = remainingPixelVectorList.get(0);
        for (Vector2D pixelPosition : remainingPixelVectorList) {
            if (startingPixel.getXComponent() > pixelPosition.getXComponent()) {
                startingPixel = pixelPosition;
            } else if (startingPixel.getXComponent() == pixelPosition.getXComponent() && startingPixel.getYComponent() > pixelPosition.getYComponent()) {
                startingPixel = pixelPosition;
            }
        }
        return Optional.of(startingPixel);
    }

    private List<LeverAngles> convertPixelsToLeverAngles(List<Vector2D> pixelVectorList) {
        return pixelVectorList.stream()
                .map(vector -> {
                    getCurrentPosition().update(vector.getXComponent() * 5, vector.getYComponent() + 500);
                    int positionInList = pixelVectorList.indexOf(vector);
                    LeverAngles leverAngles = getLeverAngles();
                    if (pixelVectorList.size() - 1 > positionInList && pixelVectorList.get(positionInList + 1).distance(vector) >= 2) {
                        leverAngles.setShouldPickUp(true);
                    }
                    return leverAngles;
                })
                .collect(Collectors.toList());
    }

}
