package ca.nexapp.math.functions;

import java.util.Objects;

import ca.nexapp.math.units.Point;

public class QuadraticFunction {

    private final double a;
    private final double b;
    private final double c;

    public QuadraticFunction(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double findY(double x) {
        return (a * x * x) + (b * x) + c;
    }

    public Point findVertex() {
        double x = -b / (2.0 * a);
        double y = findY(x);
        return Point.fromCartesian(x, y);
    }

    public boolean hasRoots() {
        return getDiscriminant() >= 0;
    }

    public double[] findRealRoots() {
        double discriminant = getDiscriminant();

        if (discriminant < 0) {
            return new double[] {};
        } else if (discriminant == 0) {
            double root = -b / (2.0 * a);
            return new double[] { root };
        } else {
            double positiveRoot = (-b + Math.sqrt(discriminant)) / (2.0 * a);
            double negativeRoot = (-b - Math.sqrt(discriminant)) / (2.0 * a);
            return new double[] { positiveRoot, negativeRoot };
        }
    }

    private double getDiscriminant() {
        return b * b - 4.0 * a * c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b, c);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuadraticFunction)) {
            return false;
        }

        QuadraticFunction other = (QuadraticFunction) obj;
        return Objects.equals(a, other.a)
                && Objects.equals(b, other.b)
                && Objects.equals(c, other.c);
    }

    @Override
    public String toString() {
        return "y = " + a + "x² + " + b + "x + " + c;
    }

    public static QuadraticFunction standardForm(double a, double b, double c) {
        return new QuadraticFunction(a, b, c);
    }

    public static QuadraticFunction fromThreePoints(Point pointA, Point pointB, Point pointC) {
        // http://www.vb-helper.com/howto_find_quadratic_curve.html
        double x1 = pointA.getX();
        double x2 = pointB.getX();
        double x3 = pointC.getX();
        double y1 = pointA.getY();
        double y2 = pointB.getY();
        double y3 = pointC.getY();

        double a = ((y2 - y1) * (x1 - x3) + (y3 - y1) * (x2 - x1)) / ((x1 - x3) * (x2 * x2 - x1 * x1) + (x2 - x1) * (x3 * x3 - x1 * x1));
        double b = ((y2 - y1) - a * (x2 * x2 - x1 * x1)) / (x2 - x1);
        double c = y1 - a * x1 * x1 - b * x1;
        return standardForm(a, b, c);
    }

    public static QuadraticFunction fromVertex(Point vertex, Point point) {
        double x = point.getX();
        double y = point.getY();
        double h = vertex.getX();
        double k = vertex.getY();

        double a = (y - k) / Math.pow(x - h, 2); // isolate a in y = a(x - h)² + k
        double b = h * 2 * a * -1; // vertex's x (or h) is equivalent to "h = (-b / 2a)", isolate b
        double c = y - (a * Math.pow(x, 2)) - (b * x); // plug a and b into y = ax² + bx + c
        return standardForm(a, b, c);
    }
}
