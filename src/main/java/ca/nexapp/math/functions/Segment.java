package ca.nexapp.math.functions;

import java.util.Objects;

import ca.nexapp.math.units.Angle;
import ca.nexapp.math.units.Point;

public class Segment {

    private static final double EPSILON = 1E-8;

    private final Point point1;
    private final Point point2;
    private final Line line;

    public Segment(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
        line = new Line(point1, point2);
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public Point getCenterPoint() {
        double xHalfDistance = (point2.getX() - point1.getX()) / 2.0;
        double yHalfDistance = (point2.getY() - point1.getY()) / 2.0;
        return Point.fromCartesian(point1.getX() + xHalfDistance, point1.getY() + yHalfDistance);
    }

    public double getLength() {
        return point1.getDistanceTo(point2);
    }

    public double getSlope() {
        return line.getSlope();
    }

    public double getIntercept() {
        return line.getIntercept();
    }

    public Segment rotateCounterClockwiseOnPivot(Point pivot, Angle angle) {
        Point newPoint1 = point1.rotateCounterClockwise(pivot, angle);
        Point newPoint2 = point2.rotateCounterClockwise(pivot, angle);
        return new Segment(newPoint1, newPoint2);
    }

    public Segment rotateClockwiseOnPivot(Point pivot, Angle angle) {
        Point newPoint1 = point1.rotateClockwise(pivot, angle);
        Point newPoint2 = point2.rotateClockwise(pivot, angle);
        return new Segment(newPoint1, newPoint2);
    }

    public Segment translate(double translationUnits) {
        Line translated = line.translate(translationUnits);
        Point newPoint1 = translated.findPointGivenX(point1.getX());
        Point newPoint2 = translated.findPointGivenX(point2.getX());
        return new Segment(newPoint1, newPoint2);
    }

    public Segment findParallelLinePassingThrough(Point point) {
        Line parallel = line.findParallelLinePassingThrough(point);
        Point newPoint1 = parallel.findPointGivenX(point1.getX());
        Point newPoint2 = parallel.findPointGivenX(point2.getX());
        return new Segment(newPoint1, newPoint2);
    }

    public Segment findPerpendicularLinePassingThrough(Point point) {
        Line perpendicular = line.findPerpendicularLinePassingThrough(point);
        Point newPoint1 = perpendicular.findPointGivenX(point1.getX());
        Point newPoint2 = perpendicular.findPointGivenX(point2.getX());
        return new Segment(newPoint1, newPoint2);
    }

    public boolean isPassingOn(Point c) {
        return isPassingOn(c, EPSILON);
    }

    public boolean isPassingOn(Point c, double delta) {
        double acDistance = point1.getDistanceTo(c);
        double cbDistance = c.getDistanceTo(point2);
        double abDistance = point1.getDistanceTo(point2);
        return acDistance + cbDistance - abDistance < delta;
    }

    @Override
    public int hashCode() {
        return Objects.hash(point1, point2);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Segment)) {
            return false;
        }

        Segment other = (Segment) obj;

        return Objects.equals(point1, other.point1) && Objects.equals(point2, other.point2);
    }

    @Override
    public String toString() {
        return point1 + " to " + point2;
    }

}
