package tech.gesp;

import lombok.Getter;

@Getter
public class FiveBar {

    private Vector2D currentPosition;
    private double smallerToLeverAngle;
    private double topLength;
    private double smallerLength;
    private double leverLength;
    private double hingeDistance;

    public FiveBar(double topLength, double smallerLength, double leverLength, double hingeDistance, double smallerToLeverAngle) {
        this.topLength = topLength;
        this.smallerLength = smallerLength;
        this.leverLength = leverLength;
        this.hingeDistance = hingeDistance;
        this.smallerToLeverAngle = smallerToLeverAngle;
        this.currentPosition = new Vector2D(0, 0);
    }

    public Vector2D getHypothenuseFirst() {
        return new Vector2D(currentPosition.getXComponent() + (hingeDistance / 2), currentPosition.getYComponent());
    }

    public Vector2D getHypothenuseSecond() {
        return new Vector2D(currentPosition.getXComponent() - (hingeDistance / 2), currentPosition.getYComponent());
    }

    public LeverAngles getLeverAngles() {
        Vector2D h1Vector = getHypothenuseFirst();
        Vector2D h2Vector = getHypothenuseSecond();
        double h1Length = Math.sqrt(TrigonometryUtil.square(h1Vector.getXComponent()) + TrigonometryUtil.square(h1Vector.getYComponent()));
        double h2Length = Math.sqrt(TrigonometryUtil.square(h2Vector.getXComponent()) + TrigonometryUtil.square(h2Vector.getYComponent()));

        double angleL1 = getLeverAngle(h1Length, h2Length);
        double angleL2 = getLeverAngle(h2Length, h1Length);
        return new LeverAngles(angleL1, angleL2);
    }

    public double getLeverAngle(double h1Length, double h2Length) {
        double angleH1 = TrigonometryUtil.getAcosAngle(h1Length, smallerLength, topLength);
        double angleH1O1 = TrigonometryUtil.getAcosAngle(hingeDistance, h1Length, h2Length);
        double angleO1 = 360 - angleH1 - angleH1O1 - smallerToLeverAngle;
        return angleO1 - 90;
    }

}
