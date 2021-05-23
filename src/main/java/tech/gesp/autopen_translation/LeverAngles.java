package tech.gesp.autopen_translation;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class LeverAngles {

    private double firstAngle;
    private double secondAngle;

    public double getFirst() {
        return firstAngle;
    }

    public double getSecond() {
        return secondAngle;
    }
}
