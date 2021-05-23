package tech.gesp.image_processing;

import lombok.AllArgsConstructor;
import tech.gesp.maths.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class VectorShape {

    private List<Vector2D> vectors;

    public List<Vector2D> getAllIntersections(int lengthForNeighbor) {
        List<Vector2D> pixelIntersections = new ArrayList<>();
        vectors.forEach(shapeVector -> {
            List<Vector2D> neighboringVectors = getNeighboringVectors(shapeVector, lengthForNeighbor);
            if (isIntersection(neighboringVectors, lengthForNeighbor)) {
                pixelIntersections.add(shapeVector);
            }
        });
        return pixelIntersections;
    }

    public List<Vector2D> getNeighboringVectors(Vector2D vectorToCheckNeighbors, int lengthForNeighbor) {
        return vectors.stream()
                .filter(vectorOfShape -> vectorOfShape != vectorToCheckNeighbors)
                .filter(vectorOfShape -> vectorOfShape.largestComponentDifferenceWith(vectorToCheckNeighbors) <= lengthForNeighbor)
                .collect(Collectors.toList());
    }

    public boolean isIntersection(List<Vector2D> neighboringVectors, int lengthForNeighbor) {
        return neighboringVectors.size() >= 3 && neighboringVectors.stream()
                .allMatch(neighborVector -> neighboringVectors.stream()
                        .filter(otherNeighbor -> otherNeighbor != neighborVector)
                        .allMatch(otherNeighbor -> neighborVector.distance(otherNeighbor) > lengthForNeighbor));
    }

}
