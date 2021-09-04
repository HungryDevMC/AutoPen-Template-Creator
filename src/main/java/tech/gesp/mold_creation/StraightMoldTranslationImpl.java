package tech.gesp.mold_creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.gesp.autopen_translation.LeverAngles;
import tech.gesp.configuration.MoldCreationConfiguration;
import tech.gesp.configuration.PhysicalConfiguration;
import tech.gesp.maths.Vector2D;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class StraightMoldTranslationImpl extends AbstractLeverAngleToMoldTranslation {

    private PhysicalConfiguration physicalConfiguration;

    @Autowired
    public StraightMoldTranslationImpl(MoldCreationConfiguration moldCreationConfiguration, PhysicalConfiguration physicalConfiguration) {
        super(moldCreationConfiguration);
        this.physicalConfiguration = physicalConfiguration;
    }

    @Override
    public List<Vector2D> translate(List<LeverAngles> leverAnglesList) {
        List<Vector2D> secondSideList = new LinkedList<>();
        List<Vector2D> finalPointsList = new LinkedList<>();

        for (int angleIndex = 0; angleIndex < leverAnglesList.size(); angleIndex++) {
            LeverAngles currentAngles = leverAnglesList.get(angleIndex);
            Vector2D firstSidePosition = angleToVector(currentAngles.getFirst(), angleIndex);
            Vector2D secondSidePosition = angleToVector(currentAngles.getSecond(), angleIndex);
            secondSidePosition.update(secondSidePosition.getXComponent() + physicalConfiguration.getHingeDistance(), secondSidePosition.getYComponent());
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
