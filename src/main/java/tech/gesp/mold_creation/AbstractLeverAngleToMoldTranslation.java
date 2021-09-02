package tech.gesp.mold_creation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.maths.Vector2D;

@RequiredArgsConstructor
@Component
public abstract class AbstractLeverAngleToMoldTranslation implements LeverAngleToMoldTranslation {

    private final MoldCreationSettings moldCreationSettings;

    public Vector2D angleToVector(double leverAngle, int indexOfLeverAngle) {
        double xComponent = moldCreationSettings.getComponentPerDegree() * leverAngle;
        double yComponent = moldCreationSettings.getComponentPerDegree() * indexOfLeverAngle;
        return new Vector2D(xComponent, yComponent);
    }

}
