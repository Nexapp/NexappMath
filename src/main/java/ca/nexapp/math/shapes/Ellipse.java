package ca.nexapp.math.shapes;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ca.nexapp.math.functions.QuadraticFunction;
import ca.nexapp.math.functions.QuarticFunction;
import ca.nexapp.math.units.Angle;
import ca.nexapp.math.units.Point;

public class Ellipse {

    private static final double EPSILON = 0.000001;

    private final double a;
    private final double b;
    private final Point centerPoint;
    private final Angle rotationAngle;

    private Ellipse(double a, double b, Point centerPoint, Angle rotationAngle) {
        this.a = a;
        this.b = b;
        this.centerPoint = centerPoint;
        this.rotationAngle = rotationAngle;
    }

    public double getRadiusX() {
        return a;
    }

    public double getRadiusY() {
        return b;
    }

    public Point getCenter() {
        return centerPoint;
    }

    public Angle getRotationAngle() {
        return rotationAngle;
    }

    public double getWidth() {
        return a * 2.0;
    }

    public double getHeight() {
        return b * 2.0;
    }

    public Ellipse increaseRadiusesBy(double units) {
        return new Ellipse(a + units, b + units, centerPoint, rotationAngle);
    }

    public Ellipse decreaseRadiusesBy(double units) {
        return new Ellipse(a - units, b - units, centerPoint, rotationAngle);
    }

    public boolean isIntersecting(Circle circle) {
        return isIntersecting(Ellipse.fromCircle(circle));
    }

    public boolean isIntersecting(Ellipse otherEllipse) {
        if (isConsideredACircle() && otherEllipse.isConsideredACircle()) {
            Circle thisCircle = Circle.fromRadius(a, centerPoint);
            Circle otherCircle = Circle.fromRadius(otherEllipse.a, otherEllipse.centerPoint);
            return thisCircle.isIntersecting(otherCircle);
        }
        return new QuarticFunction(this, otherEllipse).hasRoots();
    }

    public List<Point> findAllIntersectionPointsWith(Circle otherCircle) {
        return findAllIntersectionPointsWith(Ellipse.fromCircle(otherCircle));
    }

    public List<Point> findAllIntersectionPointsWith(Ellipse otherEllipse) {
        // https://elliotnoma.wordpress.com/2013/04/10/a-closed-form-solution-for-the-intersections-of-two-ellipses/
        if (!isIntersecting(otherEllipse)) {
            throw new IllegalArgumentException(this + " does not intersect with " + otherEllipse);
        }

        if (isConsideredACircle() && otherEllipse.isConsideredACircle()) {
            return findIntersectionPointsConsideringBothEllipsesAsCircles(otherEllipse);
        }

        double[] terms1 = toStandardForm();
        double[] terms2 = otherEllipse.toStandardForm();

        double a = terms1[0];
        double b = terms1[1];
        double c = terms1[2];
        double d = terms1[3];
        double e = terms1[4];
        double f = terms1[5];
        double a1 = terms2[0];
        double b1 = terms2[1];
        double c1 = terms2[2];
        double d1 = terms2[3];
        double e1 = terms2[4];
        double f1 = terms2[5];

        List<Point> intersections = new ArrayList<>();
        double[] roots = new QuarticFunction(this, otherEllipse).findRealRoots();
        for (double y : roots) {
            double denominator = a * b1 * y + a * d1 - a1 * b * y - a1 * d;
            if (denominator == 0.00) {
                double bb = b * y + d;
                double cc = c * y * y + e * y + f;

                double[] quadraticRoots = new QuadraticFunction(a, bb, cc).findRealRoots();
                for (double x : quadraticRoots) {
                    intersections.add(Point.fromCartesian(x, y));
                }
            } else {
                double numerator = -(a * f1 + a * c1 * y * y - a1 * c * y * y + a * e1 * y - a1 * e * y - a1 * f);
                double x = numerator / denominator;
                intersections.add(Point.fromCartesian(x, y));
            }
        }
        return intersections;
    }

    private List<Point> findIntersectionPointsConsideringBothEllipsesAsCircles(Ellipse otherEllipse) {
        Circle thisCircle = Circle.fromRadius(a, centerPoint);
        Circle otherCircle = Circle.fromRadius(otherEllipse.a, otherEllipse.centerPoint);
        Point[] intersections = thisCircle.findIntersectionPointsWith(otherCircle);
        return Arrays.asList(intersections);
    }

    public double[] findY(double x) {
        double a2 = Math.pow(a, 2.0);
        double b2 = Math.pow(b, 2.0);
        double xc = centerPoint.getX();
        double yc = centerPoint.getY();

        double inRoot = a2 * b2 * (a2 - Math.pow(xc, 2.0) + 2.0 * x * xc - Math.pow(x, 2.0));
        if (inRoot < 0) {
            return new double[] {};
        } else if (inRoot == 0) {
            return new double[] { yc };
        } else {
            double topY = (a2 * yc + Math.sqrt(inRoot)) / a2;
            double bottomY = (a2 * yc - Math.sqrt(inRoot)) / a2;
            return new double[] { topY, bottomY };
        }
    }

    public double[] findX(double y) {
        double a2 = Math.pow(a, 2.0);
        double b2 = Math.pow(b, 2.0);
        double xc = centerPoint.getX();
        double yc = centerPoint.getY();

        double inRoot = a2 * b2 * (b2 - Math.pow(yc, 2.0) + 2.0 * y * yc - Math.pow(y, 2.0));
        if (inRoot < 0) {
            return new double[] {};
        } else if (inRoot == 0.0) {
            return new double[] { xc };
        } else {
            double leftX = (b2 * xc - Math.sqrt(inRoot)) / b2;
            double rightX = (b2 * xc + Math.sqrt(inRoot)) / b2;
            return new double[] { leftX, rightX };
        }
    }

    public double[] toStandardForm() {
        // http://en.wikipedia.org/wiki/Ellipse#In_analytic_geometry
        double cos = Math.cos(rotationAngle.toRadians());
        double sin = Math.sin(rotationAngle.toRadians());
        double xc = centerPoint.getX();
        double yc = centerPoint.getY();

        double aTerm = a * a * (sin * sin) + b * b * (cos * cos);
        double bTerm = 2.0 * (b * b - a * a) * sin * cos;
        double cTerm = a * a * (cos * cos) + b * b * (sin * sin);
        double dTerm = -2.0 * aTerm * xc - bTerm * yc;
        double eTerm = -bTerm * xc - 2.0 * cTerm * yc;
        double fTerm = aTerm * xc * xc + bTerm * xc * yc + cTerm * yc * yc - a * a * b * b;
        return new double[] { aTerm, bTerm, cTerm, dTerm, eTerm, fTerm };
    }

    public boolean isRotated() {
        return Math.abs(rotationAngle.toDegrees()) > EPSILON;
    }

    public boolean isConsideredACircle() {
        return areRadiusesEquals();
    }

    private boolean areRadiusesEquals() {
        return Math.abs(getRadiusX() - getRadiusY()) < EPSILON;
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("0.####", new DecimalFormatSymbols(Locale.US));

        String angle = formatter.format(rotationAngle.toRadians());
        String x = "(x - " + formatter.format(centerPoint.getX()) + ")";
        String y = "(y - " + formatter.format(centerPoint.getY()) + ")";
        String cos = "cos(" + angle + ")";
        String sin = "sin(" + angle + ")";

        String leftPart = "(" + x + "*" + cos + " + " + y + "*" + sin + ")² / " + formatter.format(a) + "²";
        String rightPart = "(" + x + "*" + sin + " - " + y + "*" + cos + ")² / " + formatter.format(b) + "²";
        return leftPart + " + " + rightPart + " = 1";
    }

    public static Ellipse fromCircle(Circle circle) {
        return new Ellipse(circle.getRadius(), circle.getRadius(), circle.getCenter(), Angle.ZERO_DEGREES);
    }

    public static Ellipse fromCenter(double a, double b, Point center) {
        return new Ellipse(a, b, center, Angle.ZERO_DEGREES);
    }

    public static Ellipse withRadiuses(double radiusX, double radiusY, Point center) {
        return withRadiuses(radiusX, radiusY, center, Angle.ZERO_DEGREES);
    }

    public static Ellipse withRadiuses(double radiusX, double radiusY, Point center, Angle rotationAngle) {
        double a = Math.max(radiusX, radiusY);
        double b = Math.min(radiusX, radiusY);
        return new Ellipse(a, b, center, rotationAngle);
    }
}
