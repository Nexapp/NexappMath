package ca.nexapp.math.shapes;

import ca.nexapp.math.functions.Line;
import ca.nexapp.math.units.Angle;
import ca.nexapp.math.units.Point;

public class Triangle {

    private final Point a;
    private final Point b;
    private final Point c;

    private final double lengthAB;
    private final double lengthAC;
    private final double lengthBC;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
        lengthAB = a.getDistanceTo(b);
        lengthAC = a.getDistanceTo(c);
        lengthBC = b.getDistanceTo(c);
    }

    public Angle getAngleAtA() {
        return calculateAngle(lengthBC, lengthAB, lengthAC);
    }

    public Angle getBisectorAngleAtA() {
        double bisectorAngle = getAngleAtA().toDegrees() / 2.0;
        return Angle.fromDegrees(bisectorAngle);
    }

    public Angle getAngleAtB() {
        return calculateAngle(lengthAC, lengthAB, lengthBC);
    }

    public Angle getBisectorAngleAtB() {
        double bisectorAngle = getAngleAtB().toDegrees() / 2.0;
        return Angle.fromDegrees(bisectorAngle);
    }

    public Angle getAngleAtC() {
        return calculateAngle(lengthAB, lengthAC, lengthBC);
    }

    public Angle getBisectorAngleAtC() {
        double bisectorAngle = getAngleAtC().toDegrees() / 2.0;
        return Angle.fromDegrees(bisectorAngle);
    }

    public Line getBisectorLineFromA() {
        return new Line(a, findCenterPoint());
    }

    public Line getBisectorLineFromB() {
        return new Line(b, findCenterPoint());
    }

    public Line getBisectorLineFromC() {
        return new Line(c, findCenterPoint());
    }

    public Point findCenterPoint() {
        // Solution found at: http://en.wikipedia.org/wiki/Incenter#Relation_to_triangle_sides_and_vertices
        double p = lengthAB + lengthAC + lengthBC;
        double xNumerator = lengthBC * a.getX() + lengthAC * b.getX() + lengthAB * c.getX();
        double yNumerator = lengthBC * a.getY() + lengthAC * b.getY() + lengthAB * c.getY();
        double x = xNumerator / p;
        double y = yNumerator / p;
        return Point.fromCartesian(x, y);
    }

    private Angle calculateAngle(double lengthOppositeToAngle, double lengthAdjacentToAngle, double lengthAdjacentToAngle2) {
        // Solution found at: http://calculis.net/triangle
        double numerator = Math.pow(lengthAdjacentToAngle, 2) + Math.pow(lengthAdjacentToAngle2, 2) - Math.pow(lengthOppositeToAngle, 2);
        double denominator = 2 * lengthAdjacentToAngle * lengthAdjacentToAngle2;
        double angleInRadians = Math.acos(numerator / denominator);
        return Angle.fromRadians(angleInRadians);
    }


}
