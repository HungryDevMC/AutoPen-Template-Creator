package tech.gesp.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import tech.gesp.mold_creation.MoldType;

@Configuration
@PropertySource("classpath:mold.properties")
@Getter
public class MoldCreationConfiguration {
    @Value("${translation.vector_component_per_degree_x}")
    private double componentPerDegreeX;
    @Value("${translation.vector_component_per_degree_y}")
    private double componentPerDegreeY;
    @Value("${translation.mold_unit}")
    private String moldUnit;
    @Value("${translation.mold_type}")
    private MoldType selectedMoldType;
    @Value("${translation.drawing_offset}")
    private double drawingOffset;
}
