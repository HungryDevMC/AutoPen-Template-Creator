package tech.gesp.maths;

public final class TrigonometryUtil {

    public static double getAcosAngle(double hypothenuse, double adjacent, double opposite) {
        double cosAngle = (square(hypothenuse) + square(adjacent) - square(opposite)) / (2 * hypothenuse * adjacent);
        return Math.toDegrees(Math.acos(cosAngle));
    }

    public static double square(double number) {
        return Math.pow(number, 2);
    }

}
