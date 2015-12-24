package ca.nexapp.math.functions;

import java.util.Objects;

import ca.nexapp.math.Angle;
import ca.nexapp.math.Point;

public class Line {

    public static final Line X_AXIS = new Line(0.0, Point.ORIGIN);
    public static final Line Y_AXIS = new Line(Double.POSITIVE_INFINITY, 0.00);

    private static final double PRECISION = 0.000000000000001;

    private final double slope;
    private final double intercept;

    public Line(double slope, double intercept) {
        this.slope = slope;
        this.intercept = intercept;
    }

    public Line(Point a, Point b) {
        if (a.equals(b)) {
            throw new IllegalArgumentException("You must provide two different points");
        }

        slope = findSlope(a, b);
        intercept = findIntercept(slope, a);
    }

    public Line(double slope, Point a) {
        this.slope = slope;
        intercept = findIntercept(slope, a);
    }

    public Line(Angle angleWithXAxis, Point a) {
        slope = Math.tan(angleWithXAxis.toRadians());
        intercept = findIntercept(slope, a);
    }

    private double findSlope(Point a, Point b) {
        return (b.getY() - a.getY()) / (b.getX() - a.getX());
    }

    private double findIntercept(double slope, Point a) {
        double intercept = a.getY() - slope * a.getX();
        return Double.isNaN(intercept) ? 0.0 : intercept;
    }

    public double getSlope() {
        return slope;
    }

    public double getIntercept() {
        return intercept;
    }

    public boolean hasAPositiveSlope() {
        return slope >= 0.0;
    }

    public boolean hasANegativeSlope() {
        return !hasAPositiveSlope();
    }

    public double getNegativeReciprocal() {
        return -1.0 / slope;
    }

    public boolean isVertical() {
        return Double.isInfinite(slope);
    }

    public boolean isHorizontal() {
        return slope == 0.0;
    }

    public boolean isParallel(Line other) {
        return slope == other.slope;
    }

    public boolean isPerpendicular(Line other) {
        return slope == other.getNegativeReciprocal();
    }

    public Angle getAngleWithXAxis() {
        double thetaInRadian = Math.atan(getSlope());
        return Angle.fromRadians(thetaInRadian);
    }

    public double getDistanceTo(Point point) {
        if (isVertical()) {
            return Math.abs(point.getX() - findX(0));
        }
        double numerator = Math.abs(slope * point.getX() - point.getY() + intercept);
        double denominator = Math.sqrt(Math.pow(slope, 2) + 1);
        return numerator / denominator;
    }

    public boolean isPassingOn(Point point) {
        return !isPointAbove(point) && !isPointBelow(point);
    }

    public boolean isPointAbove(Point point) {
        if (isVertical()) {
            return false;
        }
        double lineY = findY(point.getX());
        return point.getY() > lineY;
    }

    public boolean isPointBelow(Point point) {
        if (isVertical()) {
            return false;
        }
        double lineY = findY(point.getX());
        return point.getY() < lineY;
    }

    public Point findPointGivenX(double x) {
        return Point.fromCartesian(x, findY(x));
    }

    public Point findPointGivenY(double y) {
        return Point.fromCartesian(findX(y), y);
    }

    public double findX(double y) {
        if (isHorizontal()) {
            throw new IllegalStateException("Cannot find x of a horizontal line");
        }
        return (y - intercept) / slope;
    }

    public double findY(double x) {
        if (isVertical()) {
            throw new IllegalStateException("Cannot find y of a vertical line");
        }

        return slope * x + intercept;
    }

    public Line rotateClockwiseOn(Point pivot, Angle angle) {
        Point aPoint = isVertical() ? Point.ORIGIN : findPointGivenX(0);
        Point anotherPoint = isVertical() ? Point.fromCartesian(0, 1) : findPointGivenX(1);

        Point aRotatedPoint = aPoint.rotateClockwise(pivot, angle);
        Point anotherRotatedPoint = anotherPoint.rotateClockwise(pivot, angle);

        return new Line(aRotatedPoint, anotherRotatedPoint);
    }

    public Line rotateCounterClockwiseOn(Point pivot, Angle angle) {
        Angle clockwiseAngle = angle.invert();
        return rotateClockwiseOn(pivot, clockwiseAngle);
    }

    public Line translate(double translationUnits) {
        return new Line(slope, intercept + translationUnits);
    }

    public Point findOffsettedPointUpward(double offsetFromPoint, Point point) {
        double actualOffset = hasANegativeSlope() ? -offsetFromPoint : offsetFromPoint;
        return findOffsettedPointRightward(actualOffset, point);
    }

    public Point findOffsettedPointDownward(double offsetFromPoint, Point point) {
        double actualOffset = hasAPositiveSlope() ? -offsetFromPoint : offsetFromPoint;
        return findOffsettedPointRightward(actualOffset, point);
    }

    public Point findOffsettedPointLeftward(double offsetFromPoint, Point point) {
        double actualOffset = -offsetFromPoint;
        return findOffsettedPointRightward(actualOffset, point);
    }

    public Point findOffsettedPointRightward(double offsetFromPoint, Point point) {
        double x2 = point.getX();
        double y2 = point.getY();

        if (!isVertical()) {
            double x = offsetFromPoint / Math.sqrt(Math.pow(slope, 2.0) + 1.0) + x2;
            double y = (x - x2) * slope + y2;
            return Point.fromCartesian(x, y);
        } else {
            double x = x2;
            double y = y2 + offsetFromPoint;
            return Point.fromCartesian(x, y);
        }
    }

    public Line findPerpendicularLinePassingThrough(Point point) {
        return new Line(getNegativeReciprocal(), point);
    }

    public Line findParallelLinePassingThrough(Point point) {
        return new Line(slope, point);
    }

    public Line findTheAboveParallelLineOf(double offset) {
        Point aPoint = findPointGivenX(0);
        Line perpendicularLine = findPerpendicularLinePassingThrough(aPoint);
        Point offsettedPoint = perpendicularLine.findOffsettedPointUpward(offset, aPoint);
        return findParallelLinePassingThrough(offsettedPoint);
    }

    public Line findTheBelowParallelLineOf(double offset) {
        return findTheAboveParallelLineOf(-offset);
    }

    private boolean hasSameSlope(Line other) {
        double slopeDiff = Math.abs(slope - other.slope);
        return Double.isInfinite(slopeDiff) || slopeDiff < PRECISION;
    }

    @Override
    public int hashCode() {
        return Objects.hash(slope, intercept);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Line)) {
            return false;
        }

        Line other = (Line) obj;
        return hasSameSlope(other) && Objects.equals(intercept, other.intercept);
    }

    @Override
    public String toString() {
        return slope + "x + " + intercept;
    }

}
