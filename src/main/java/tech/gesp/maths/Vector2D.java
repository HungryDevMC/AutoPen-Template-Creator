package tech.gesp.maths;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Vector2D {

    private double xComponent;
    private double yComponent;

    public void update(double x, double y) {
        this.xComponent = x;
        this.yComponent = y;
    }

}
