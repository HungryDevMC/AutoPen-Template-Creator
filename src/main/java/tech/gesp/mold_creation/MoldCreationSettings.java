package tech.gesp.mold_creation;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mold.properties")
@Getter
public class MoldCreationSettings {
    @Value("${translation.vector_component_per_degree}")
    private double componentPerDegree;
    @Value("${translation.mold_type}")
    private MoldType selectedMoldType;
}
