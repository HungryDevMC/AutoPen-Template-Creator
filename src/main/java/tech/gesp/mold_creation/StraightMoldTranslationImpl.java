package tech.gesp.mold_creation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.maths.Vector2D;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class StraightMoldTranslationImpl extends AbstractLeverAngleToMoldTranslation {

    public StraightMoldTranslationImpl(MoldCreationSettings moldCreationSettings) {
        super(moldCreationSettings);
    }

    @Override
    public List<Vector2D> translate(List<LeverAngles> leverAnglesList) {
        List<Vector2D> secondSideList = new LinkedList<>();
        List<Vector2D> finalPointsList = new LinkedList<>();

        for (int angleIndex = 0; angleIndex < leverAnglesList.size(); angleIndex++) {
            LeverAngles currentAngles = leverAnglesList.get(angleIndex);
            Vector2D firstSidePosition = angleToVector(currentAngles.getFirst(), angleIndex);
            Vector2D secondSidePosition = angleToVector(currentAngles.getSecond(), angleIndex);
            finalPointsList.add(firstSidePosition);
            secondSideList.add(secondSidePosition);
        }

        Collections.reverse(secondSideList);
        finalPointsList.addAll(secondSideList);
        return finalPointsList;
    }

    @Override
    public MoldType moldType() {
        return MoldType.STRAIGHT;
    }
}
