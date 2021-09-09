package tech.gesp.autopen_translation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.gesp.configuration.PhysicalConfiguration;
import tech.gesp.maths.Vector2D;

@Service
@RequiredArgsConstructor
public class VirtualToRealPositionTranslator {

    private final PhysicalConfiguration physicalConfiguration;

    public Vector2D translate(Vector2D virtualPosition, double maxRange) {
        double xTolerance = (physicalConfiguration.getXRangeTolerancePercentage() / 100.0);
        double yTolerance = (physicalConfiguration.getYRangeTolerancePercentage() / 100.0);
        return new Vector2D(-maxRange + (xTolerance * maxRange) + virtualPosition.getXComponent(), maxRange - (yTolerance * maxRange) - virtualPosition.getYComponent());
    }

}
