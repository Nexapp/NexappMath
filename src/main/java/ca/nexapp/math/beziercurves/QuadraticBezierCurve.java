package ca.nexapp.math.beziercurves;

import ca.nexapp.math.functions.QuadraticFunction;
import ca.nexapp.math.functions.Segment;
import ca.nexapp.math.units.Point;

public class QuadraticBezierCurve {

    private final Point startPoint;
    private final Point controlPoint;
    private final Point endPoint;

    public QuadraticBezierCurve(Point startPoint, Point controlPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.controlPoint = controlPoint;
        this.endPoint = endPoint;
    }

    public Point getControlPoint() {
        return controlPoint;
    }

    public double findXAt(double t) {
        double firstPart = startPoint.getX() * Math.pow(1 - t, 2);
        double secondPart = 2 * controlPoint.getX() * (1 - t) * t;
        double thirdPart = endPoint.getX() * Math.pow(t, 2);
        return firstPart + secondPart + thirdPart;
    }

    public double findYAt(double t) {
        double firstPart = Math.pow(1 - t, 2) * startPoint.getY();
        double secondPart = 2 * (1 - t) * t * controlPoint.getY();
        double thirdPart = Math.pow(t, 2) * endPoint.getY();
        return firstPart + secondPart + thirdPart;
    }

    public Point findPointAt(double t) {
        return Point.fromCartesian(findXAt(t), findYAt(t));
    }

    public final Point getLeftIntersectingPointWith(Segment line) {
        QuadraticFunction quadratic = resolveBezierCurveWithLineIntoLinearSystemEquation(line);
        if (!quadratic.hasRoots()) {
            throw new IllegalArgumentException(line + " does not intersect with " + this);
        }

        double t = quadratic.findLeftRoot();

        if (!isTValid(t)) {
            throw new IllegalArgumentException(line + " does not intersect with " + this);
        }

        return findPointAt(t);
    }

    public final Point getRightIntersectingPointWith(Segment line) {
        QuadraticFunction quadratic = resolveBezierCurveWithLineIntoLinearSystemEquation(line);
        if (!quadratic.hasRoots()) {
            throw new IllegalArgumentException(line + " does not intersect with " + this);
        }

        double t = quadratic.findRightRoot();

        if (!isTValid(t)) {
            throw new IllegalArgumentException(line + " does not intersect with " + this);
        }

        return findPointAt(t);
    }

    private QuadraticFunction resolveBezierCurveWithLineIntoLinearSystemEquation(Segment line) {
        double k = (line.getPoint1().getY() - line.getPoint2().getY()) / (line.getPoint2().getX() - line.getPoint1().getX());
        double a = k * (startPoint.getX() - 2.0 * controlPoint.getX() + endPoint.getX()) + startPoint.getY() - 2.0 * controlPoint.getY()
                + endPoint.getY();
        double b = -2.0 * (k * (startPoint.getX() - controlPoint.getX()) + startPoint.getY() - controlPoint.getY());
        double c = k * (startPoint.getX() - line.getPoint1().getX()) + startPoint.getY() - line.getPoint1().getY();
        return new QuadraticFunction(a, b, c);
    }

    private boolean isTValid(double t) {
        return 0.00 <= t && t <= 1.00;
    }

    @Override
    public String toString() {
        return "Bezier[" + startPoint + " to " + endPoint + " with control: " + controlPoint + "]";
    }

}
