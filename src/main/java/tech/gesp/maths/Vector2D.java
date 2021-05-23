package tech.gesp.maths;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Vector2D {

    private double xComponent;
    private double yComponent;

    public void update(double x, double y) {
        this.xComponent = x;
        this.yComponent = y;
    }

    private int xComponentDifference(Vector2D vector) {
        return (int) Math.abs(xComponent - vector.getXComponent());
    }

    private int yComponentDifference(Vector2D vector) {
        return (int) Math.abs(yComponent - vector.getYComponent());
    }

    public double distance(Vector2D vector) {
        return Math.sqrt(TrigonometryUtil.square(xComponentDifference(vector)) + TrigonometryUtil.square(yComponentDifference(vector)));
    }

    public int largestComponentDifferenceWith(Vector2D vector) {
        return Math.max(xComponentDifference(vector), yComponentDifference(vector));
    }

}
