package tech.gesp.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:physical.properties")
@Getter
public class PhysicalConfiguration {
    @Value("${hinge_distance}")
    private double hingeDistance;
    @Value("${long_leg_length}")
    private double longLegLength;
    @Value("${short_leg_length}")
    private double shortLegLength;
    @Value("${lever_length}")
    private double leverLength;
    @Value("${angle_between_lever_and_short_leg}")
    private double angleBetweenLeverAndShortLeg;
    @Value("${y_range_tolerance_percentage}")
    private double yRangeTolerancePercentage;
    @Value("${x_range_tolerance_percentage}")
    private double xRangeTolerancePercentage;
    @Value("${pen_width}")
    private double penWidth;
}
