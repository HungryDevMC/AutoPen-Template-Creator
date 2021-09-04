package tech.gesp.image_processing;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.gesp.autopen_translation.FiveBar;
import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.autopen_translation.VirtualToRealPositionTranslator;
import tech.gesp.maths.Vector2D;
import tech.gesp.configuration.MoldCreationConfiguration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PixelImageToVectorConverter {

    private final MoldCreationConfiguration moldCreationConfiguration;
    private final VirtualToRealPositionTranslator virtualToRealPositionTranslator;

    public List<LeverAngles> generateMoldLeverAngles(FiveBar fiveBar, PixelImage pixelImage) {
        return convertPixelsToLeverAngles(fiveBar, sortByDrawingOrder(pixelImage));
    }

    public List<Vector2D> sortByDrawingOrder(PixelImage pixelImage) {
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

    private Vector2D findSmallestComponentValues(List<Vector2D> pixelVectorList) {
        Vector2D smallestComponentVector = pixelVectorList.get(0);
        for (Vector2D pixelVector : pixelVectorList) {
            if(pixelVector.getXComponent() < smallestComponentVector.getXComponent()) {
                smallestComponentVector.update(pixelVector.getXComponent(), smallestComponentVector.getYComponent());
            }

            if(pixelVector.getYComponent() < smallestComponentVector.getYComponent()) {
                smallestComponentVector.update(smallestComponentVector.getXComponent(), pixelVector.getYComponent());
            }
        }
        return smallestComponentVector;
    }

    private List<LeverAngles> convertPixelsToLeverAngles(FiveBar fiveBar, List<Vector2D> pixelVectorList) {
        Vector2D smallestComponentValuesVector = findSmallestComponentValues(pixelVectorList);
        fiveBar.setStartingOffsets(smallestComponentValuesVector.getXComponent(), smallestComponentValuesVector.getYComponent());
        return pixelVectorList.stream()
                .map(vector -> {
                    fiveBar.getCurrentPosition().update(vector.getXComponent() - fiveBar.getStartingOffsetX(), vector.getYComponent() + moldCreationConfiguration.getDrawingOffset() - fiveBar.getStartingOffsetY());
                    int positionInList = pixelVectorList.indexOf(vector);
                    LeverAngles leverAngles = fiveBar.getLeverAngles(virtualToRealPositionTranslator.translate(fiveBar.getCurrentPosition(), fiveBar.getMaxRange()));
                    if (pixelVectorList.size() - 1 > positionInList && pixelVectorList.get(positionInList + 1).distance(vector) >= 2) {
                        leverAngles.setShouldPickUp(true);
                    }

                    System.out.println("DEBUG: " + fiveBar.getCurrentPosition().getXComponent() + ", " + fiveBar.getCurrentPosition().getYComponent() + " -> " + leverAngles.getFirst() + ", " + leverAngles.getSecond() + (leverAngles.shouldPickUp() ? " (PickUp)" : ""));
                    return leverAngles;
                })
                .collect(Collectors.toList());
    }

}
