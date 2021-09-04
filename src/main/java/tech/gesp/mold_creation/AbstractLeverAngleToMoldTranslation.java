package tech.gesp.mold_creation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.gesp.configuration.MoldCreationConfiguration;
import tech.gesp.maths.Vector2D;

@RequiredArgsConstructor
@Component
public abstract class AbstractLeverAngleToMoldTranslation implements LeverAngleToMoldTranslation {

    @Getter
    private final MoldCreationConfiguration moldCreationConfiguration;

    public Vector2D angleToVector(double leverAngle, int indexOfLeverAngle) {
        double xComponent = moldCreationConfiguration.getComponentPerDegreeX() * leverAngle;
        double yComponent = moldCreationConfiguration.getComponentPerDegreeY() * indexOfLeverAngle;
        return new Vector2D(xComponent, yComponent);
    }

}
