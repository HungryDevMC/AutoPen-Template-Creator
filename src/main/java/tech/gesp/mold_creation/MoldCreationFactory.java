package tech.gesp.mold_creation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;

@Service
public class MoldCreationFactory {

    private StraightMoldTranslationImpl straightMoldTranslation;
    private MoldCreationSettings moldCreationSettings;

    private final EnumMap<MoldType, LeverAngleToMoldTranslation> ANGLE_TO_MOLD_TRANSLATION_ENUM_MAP = new EnumMap<>(MoldType.class);

    @Autowired
    public MoldCreationFactory(StraightMoldTranslationImpl straightMoldTranslation, MoldCreationSettings moldCreationSettings) {
        this.straightMoldTranslation = straightMoldTranslation;
        this.moldCreationSettings = moldCreationSettings;
        ANGLE_TO_MOLD_TRANSLATION_ENUM_MAP.put(straightMoldTranslation.moldType(), straightMoldTranslation);
    }


    public LeverAngleToMoldTranslation create(MoldType moldType) {
        return ANGLE_TO_MOLD_TRANSLATION_ENUM_MAP.get(moldType);
    }

    public LeverAngleToMoldTranslation create() {
        return create(moldCreationSettings.getSelectedMoldType());
    }

}
