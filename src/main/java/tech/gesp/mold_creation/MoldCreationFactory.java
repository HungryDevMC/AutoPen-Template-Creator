package tech.gesp.mold_creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.gesp.configuration.MoldCreationConfiguration;

import java.util.EnumMap;

@Service
public class MoldCreationFactory {

    private StraightMoldTranslationImpl straightMoldTranslation;
    private MoldCreationConfiguration moldCreationConfiguration;

    private final EnumMap<MoldType, LeverAngleToMoldTranslation> ANGLE_TO_MOLD_TRANSLATION_ENUM_MAP = new EnumMap<>(MoldType.class);

    @Autowired
    public MoldCreationFactory(StraightMoldTranslationImpl straightMoldTranslation, MoldCreationConfiguration moldCreationConfiguration) {
        this.straightMoldTranslation = straightMoldTranslation;
        this.moldCreationConfiguration = moldCreationConfiguration;
        ANGLE_TO_MOLD_TRANSLATION_ENUM_MAP.put(straightMoldTranslation.moldType(), straightMoldTranslation);
    }


    public LeverAngleToMoldTranslation create(MoldType moldType) {
        return ANGLE_TO_MOLD_TRANSLATION_ENUM_MAP.get(moldType);
    }

    public LeverAngleToMoldTranslation create() {
        return create(moldCreationConfiguration.getSelectedMoldType());
    }

}
