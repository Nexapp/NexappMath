package ca.nexapp.math.units;

import java.util.Objects;

public class Point {

    public static final Point ORIGIN = new Point(0.0, 0.0);

    private static final double PRECISION = 0.001;

    private final double x;
    private final double y;

    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getPolarDistance() {
        return Math.hypot(x, y);
    }

    public Angle getPolarAngle() {
        if (x == 0 && y == 0) {
            return Angle.ZERO_DEGREES;
        } else if (x > 0 && y >= 0) {
            return Angle.fromRadians(Math.atan(y / x));
        } else if (x > 0 && y < 0) {
            return Angle.fromRadians(Math.atan(y / x) + 2.0 * Math.PI);
        } else if (x < 0) {
            return Angle.fromRadians(Math.atan(y / x) + Math.PI);
        } else if (x == 0 && y > 0) {
            return Angle.fromRadians(Math.PI / 2.0);
        } else {
            return Angle.fromRadians(3.0 * Math.PI / 2.0);
        }
    }

    public double getDistanceTo(Point otherPoint) {
        double distanceX = x - otherPoint.x;
        double distanceY = y - otherPoint.y;
        return Math.hypot(distanceX, distanceY);
    }

    public boolean isAtLeftOf(Point other) {
        return x < other.x;
    }

    public boolean isAtRightOf(Point other) {
        return x > other.x;
    }

    public boolean isAboveOf(Point other) {
        return y > other.y;
    }

    public boolean isBelowOf(Point other) {
        return y < other.y;
    }

    public Point rotateClockwise(Point pivot, Angle angle) {
        double sin = Math.sin(angle.toRadians());
        double cos = Math.cos(angle.toRadians());

        double rotatedX = (x - pivot.x) * cos + (y - pivot.y) * sin + pivot.x;
        double rotatedY = (x - pivot.x) * -sin + (y - pivot.y) * cos + pivot.y;

        return new Point(rotatedX, rotatedY);
    }

    public Point rotateCounterClockwise(Point pivot, Angle angle) {
        double sin = Math.sin(angle.toRadians());
        double cos = Math.cos(angle.toRadians());

        double rotatedX = (x - pivot.x) * cos - (y - pivot.y) * sin + pivot.x;
        double rotatedY = (x - pivot.x) * sin + (y - pivot.y) * cos + pivot.y;

        return new Point(rotatedX, rotatedY);
    }

    public static Point fromCartesian(double x, double y) {
        return new Point(x, y);
    }

    public static Point fromPolar(Angle angle, double distance) {
        double x = distance * Math.cos(angle.toRadians());
        double y = distance * Math.sin(angle.toRadians());
        return new Point(x, y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Point)) {
            return false;
        }

        Point other = (Point) obj;
        return Math.abs(x - other.x) < PRECISION && Math.abs(y - other.y) < PRECISION;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")(" + getPolarDistance() + ", " + getPolarAngle().toDegrees() + ")";
    }

}
