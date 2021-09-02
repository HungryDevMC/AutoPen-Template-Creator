package tech.gesp.mold_creation;

import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.maths.Vector2D;

import java.util.List;

public interface LeverAngleToMoldTranslation {
    List<Vector2D> translate(List<LeverAngles> leverAnglesList);
    Vector2D angleToVector(double leverAngle, int indexOfLeverAngle);
    MoldType moldType();
}
