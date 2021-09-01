package tech.gesp.autopen_translation;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
public class LeverAngles {

    private double firstAngle;
    private double secondAngle;
    private boolean pickUp;

    public LeverAngles(double firstAngle, double secondAngle) {
        this.firstAngle = firstAngle;
        this.secondAngle = secondAngle;
    }

    public double getFirst() {
        return firstAngle;
    }

    public double getSecond() {
        return secondAngle;
    }

    public boolean shouldPickUp() {
        return pickUp;
    }

    public void setShouldPickUp(boolean shouldPickUp) {
        this.pickUp = shouldPickUp;
    }

}
