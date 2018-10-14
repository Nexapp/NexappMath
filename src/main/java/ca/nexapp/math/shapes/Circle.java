package ca.nexapp.math.shapes;

import java.util.Objects;

import ca.nexapp.math.functions.Line;
import ca.nexapp.math.units.Point;

public class Circle {

    private static final double PRECISION = 0.0001;

    private final double radius;
    private final Point center;

    private Circle(double radius, Point center) {
        this.radius = radius;
        this.center = center;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return radius * 2.0;
    }

    public Circle increaseRadiusBy(double units) {
        return new Circle(radius + units, center);
    }

    public Circle decreaseRadiusBy(double units) {
        return new Circle(radius - units, center);
    }

    public boolean isIntersecting(Circle otherCircle) {
        double distance = getDistanceBetweenCenters(otherCircle);
        double maxRadius = Math.max(radius, otherCircle.radius);
        double minDiameter = Math.min(radius, otherCircle.radius) * 2;
        return distance >= 0 && distance >= maxRadius - minDiameter && distance <= radius + otherCircle.radius;
    }

    public Point[] findIntersectionPointsWith(Circle circle) {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/CircleCircleIntersection.htm
        if (equals(circle)) {
            throw new IllegalArgumentException("There are an infinity of intersection points with " + circle);
        }

        if (!isIntersecting(circle)) {
            throw new IllegalArgumentException(this + " does not intersect with " + circle);
        }

        double distanceBetweenCenter = getDistanceBetweenCenters(circle);
        double r0 = radius;
        double r1 = circle.radius;
        double alpha = 1.0 / 4.0 * Math.sqrt((distanceBetweenCenter + r0 + r1) * (distanceBetweenCenter + r0 - r1)
                * (distanceBetweenCenter - r0 + r1) * (-distanceBetweenCenter + r0 + r1));

        double xCalculationFirst = (center.getX() + circle.center.getX()) / 2.0 + (circle.center.getX() - center.getX())
                * (Math.pow(r0, 2) - Math.pow(r1, 2)) / (2.0 * Math.pow(distanceBetweenCenter, 2));
        double xCalculationSecond = 2.0 * ((center.getY() - circle.center.getY()) / Math.pow(distanceBetweenCenter, 2)) * alpha;

        double x1 = xCalculationFirst + xCalculationSecond;
        double x2 = xCalculationFirst - xCalculationSecond;

        double yCalculationFirst = (center.getY() + circle.center.getY()) / 2.0 + (circle.center.getY() - center.getY())
                * (Math.pow(r0, 2) - Math.pow(r1, 2)) / (2.0 * Math.pow(distanceBetweenCenter, 2));
        double yCalculationSecond = 2.0 * ((center.getX() - circle.center.getX()) / Math.pow(distanceBetweenCenter, 2)) * alpha;

        double y1 = yCalculationFirst - yCalculationSecond;
        double y2 = yCalculationFirst + yCalculationSecond;

        Point point1 = Point.fromCartesian(x1, y1);
        Point point2 = Point.fromCartesian(x2, y2);

        if (point1.equals(point2)) {
            return new Point[] { point1 };
        } else {
            return new Point[] { point1, point2 };
        }
    }

    public Line getTangentLineTo(Point pointOnCircle) {
        if (!isOnContour(pointOnCircle)) {
            throw new IllegalArgumentException(pointOnCircle + " is not on contour.");
        }
        Line radiusLine = new Line(pointOnCircle, center);
        return radiusLine.findPerpendicularLinePassingThrough(pointOnCircle);
    }

    public Line[] getTangentalLinesPassingBy(Point externalPoint) {
        Point[] tangentalPoint = findTangentPointsOfLinePassingBy(externalPoint);
        if (tangentalPoint.length == 1 && tangentalPoint[0].equals(externalPoint)) {
            Line line = getTangentLineTo(tangentalPoint[0]);
            return new Line[] { line };
        }

        Line[] lines = new Line[tangentalPoint.length];
        for (int i = 0; i < tangentalPoint.length; i++) {
            lines[i] = new Line(externalPoint, tangentalPoint[i]);
        }
        return lines;
    }

    public Line getLowerTangentalLinePassingBy(Point externalPoint) {
        Point[] tangentalPoints = findTangentPointsOfLinePassingBy(externalPoint);
        if (tangentalPoints.length == 1 && tangentalPoints[0].equals(externalPoint)) {
            return getTangentLineTo(tangentalPoints[0]);
        } else {
            //                    Point lowerTangentalPoint = new ByYAscendingPointSorter().sort(tangentalPoints).get(0);
            //                    return new Line(externalPoint, lowerTangentalPoint);
            return null;
        }
    }

    public Point[] findTangentPointsOfLinePassingBy(Point externalPoint) {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/CirclePoint/CirclePointDistance.htm
        if (isPointInside(externalPoint)) {
            throw new IllegalArgumentException("No tangent point exists for a point in circle - Point: " + externalPoint.toString());
        }

        if (isOnContour(externalPoint)) {
            return new Point[] { externalPoint };
        }

        double circleXCalculation = externalPoint.getX() - center.getX();
        double circleYCalculation = externalPoint.getY() - center.getY();
        double numeratorXFirst = Math.pow(radius, 2) * circleXCalculation;
        double numeratorXSecond = radius * circleYCalculation
                * Math.sqrt(Math.pow(circleXCalculation, 2) + Math.pow(circleYCalculation, 2) - Math.pow(radius, 2));
        double denominator = Math.pow(circleXCalculation, 2) + Math.pow(circleYCalculation, 2);

        double x1 = (numeratorXFirst + numeratorXSecond) / denominator + center.getX();
        double x2 = (numeratorXFirst - numeratorXSecond) / denominator + center.getX();

        double numeratorYFirst = Math.pow(radius, 2) * circleYCalculation;
        double numeratorYSecond = radius * circleXCalculation
                * Math.sqrt(Math.pow(circleXCalculation, 2) + Math.pow(circleYCalculation, 2) - Math.pow(radius, 2));

        double y1 = (numeratorYFirst + numeratorYSecond) / denominator + center.getY();
        double y2 = (numeratorYFirst - numeratorYSecond) / denominator + center.getY();

        Point[] tangentalPoint;
        if (isOnContour(Point.fromCartesian(x1, y1))) {
            tangentalPoint = new Point[] { Point.fromCartesian(x1, y1), Point.fromCartesian(x2, y2) };
        } else {
            tangentalPoint = new Point[] { Point.fromCartesian(x1, y2), Point.fromCartesian(x2, y1) };
        }

        return tangentalPoint;
    }

    public boolean isInside(Circle otherCircle) {
        return getDistanceBetweenCenters(otherCircle) + radius <= otherCircle.radius;
    }

    public boolean isSuperposed(Circle otherCircle) {
        return isInside(otherCircle) || otherCircle.isInside(this);
    }

    public Line getLowerTangentalLineWith(Circle otherCircle) {
        if (isSuperposed(otherCircle)) {
            throw new IllegalArgumentException("There is no lowerTangental: One of the circle is inside another");
        }

        if (this.radius == otherCircle.radius) {
            return getLowerTangentalLineWithEqualSizedCircles(this, otherCircle);
        } else {
            Circle biggerCircle = radius > otherCircle.radius ? this : otherCircle;
            Circle smallerCircle = radius <= otherCircle.radius ? this : otherCircle;
            return getLowerTangentalLineWithDifferentSizedCircles(biggerCircle, smallerCircle);
        }
    }

    private Line getLowerTangentalLineWithDifferentSizedCircles(Circle biggerCircle, Circle smallerCircle) {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/Circles2Tangent.htm
        double xIntersectionOfTangentLinesNumerator = smallerCircle.center.getX() * biggerCircle.radius - biggerCircle.center.getX()
                * smallerCircle.radius;
        double yIntersectionOfTangentLinesNumerator = smallerCircle.center.getY() * biggerCircle.radius - biggerCircle.center.getY()
                * smallerCircle.radius;
        double denominator = biggerCircle.radius - smallerCircle.radius;

        double xIntersectionOfTangentLines = xIntersectionOfTangentLinesNumerator / denominator;
        double yIntersectionOfTangentLines = yIntersectionOfTangentLinesNumerator / denominator;

        Point intersectionOfTangentLinesPoint = Point.fromCartesian(xIntersectionOfTangentLines, yIntersectionOfTangentLines);

        return smallerCircle.getLowerTangentalLinePassingBy(intersectionOfTangentLinesPoint);
    }

    private Line getLowerTangentalLineWithEqualSizedCircles(Circle circle1, Circle circle2) {
        Line linePassingByCenterOfCircle = new Line(circle1.center, circle2.center);
        Line perpendicularLineToCenterCircleLine = linePassingByCenterOfCircle.findPerpendicularLinePassingThrough(circle1.center);
        Point tangentalPointOfCircle1 = perpendicularLineToCenterCircleLine.findOffsettedPointDownward(circle1.radius, circle1.center);
        return linePassingByCenterOfCircle.findParallelLinePassingThrough(tangentalPointOfCircle1);
    }

    private double getDistanceBetweenCenters(Circle otherCircle) {
        return center.getDistanceTo(otherCircle.center);
    }

    //        public Point findLeftIntersectionPointWith(Line line) {
    //            Point[] intersections = findIntersectingPointsWith(line);
    //            return new LeftmostPointPicker().pickFrom(intersections);
    //        }
    //
    //        public Point findRightIntersectionPointWith(Line line) {
    //            Point[] intersections = findIntersectingPointsWith(line);
    //            return new RightmostPointPicker().pickFrom(intersections);
    //        }

    private Point[] findIntersectingPointsWith(Line line) {
        // http://stackoverflow.com/questions/13053061/circle-line-intersection-points
        Point a;
        Point b;
        // TODO Find a better solution to handle vertical line
        if (!line.isVertical()) {
            a = Point.fromCartesian(0, line.findY(0));
            b = Point.fromCartesian(3, line.findY(3));
        } else {
            a = Point.fromCartesian(line.getIntercept(), 0);
            b = Point.fromCartesian(line.getIntercept(), 3);
        }

        double dx = b.getX() - a.getX();
        double dy = b.getY() - a.getY();
        double caX = center.getX() - a.getX();
        double caY = center.getY() - a.getY();

        double aa = Math.pow(dx, 2) + Math.pow(dy, 2);
        double bBy2 = dx * caX + dy * caY;
        double cc = Math.pow(caX, 2) + Math.pow(caY, 2) - Math.pow(radius, 2);

        double pBy2 = bBy2 / aa;
        double q = cc / aa;

        double disc = Math.pow(pBy2, 2) - q;
        if (disc < 0) {
            throw new IllegalArgumentException(this + " does not intersect with " + line);
        }

        double abScalingFactor1 = -pBy2 + Math.sqrt(disc);
        double abScalingFactor2 = -pBy2 - Math.sqrt(disc);

        double x1 = a.getX() - dx * abScalingFactor1;
        double y1 = a.getY() - dy * abScalingFactor1;

        double x2 = a.getX() - dx * abScalingFactor2;
        double y2 = a.getY() - dy * abScalingFactor2;

        Point point1 = Point.fromCartesian(x1, y1);
        Point point2 = Point.fromCartesian(x2, y2);

        if (point1.equals(point2)) {
            return new Point[] { point1 };
        } else {
            return new Point[] { point1, point2 };
        }
    }

    public boolean isTangentTo(Circle circle) {
        return isTangentTo(circle, PRECISION);
    }

    public boolean isTangentTo(Circle circle, double precision) {
        // http://mathworld.wolfram.com/TangentCircles.html
        double x1 = center.getX();
        double y1 = center.getY();
        double r1 = radius;
        double x2 = circle.center.getX();
        double y2 = circle.center.getY();
        double r2 = circle.radius;

        double leftPart = Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2);
        double rightPartPlus = Math.pow(r1 + r2, 2);
        double rightPartMinus = Math.pow(r1 - r2, 2);

        double resultPlus = Math.abs(leftPart - rightPartPlus);
        double resultMinus = Math.abs(leftPart - rightPartMinus);

        return resultPlus < precision || resultMinus < precision;
    }

    public boolean isOnContour(Point point) {
        double squaredDistance = calculateSquaredDistanceFromCircleTo(point);
        return Math.abs(squaredDistance - Math.pow(radius, 2)) <= PRECISION;
    }

    public boolean isPointInside(Point point) {
        if (isOnContour(point)) {
            return false;
        }
        double squaredDistance = calculateSquaredDistanceFromCircleTo(point);
        return squaredDistance < Math.pow(radius, 2);
    }

    public boolean contains(Point point) {
        return isPointInside(point) || isOnContour(point);
    }

    public Circle[] getTangentalCirclesPassingBy(Point point1, Point point2) {
        // http://mathafou.free.fr/pbg_en/jsp136c.html
        // http://www-cabri.imag.fr/abracadabri/GeoPlane/Puissance/CTgt1C2pts.html
        // http://www.cut-the-knot.org/Curriculum/Geometry/GeoGebra/PPC.shtml

        if (isPointInside(point1) && !contains(point2) || !contains(point1) && isPointInside(point2)) {
            throw new IllegalArgumentException(
                    "No tangental circles exist with a point inside and one point outside of the circle. Point: " + point1 + " and "
                            + point2 + " Circle: " + this);
        }

        if (isOnContour(point1) && isOnContour(point2)) {
            return new Circle[] { new Circle(getRadius(), getCenter()) };
        }

        Line perpendicularBisector = getPerpendicularBisectorOf(point1, point2);

        if (isOnContour(point1) || isOnContour(point2)) {
            Point pointOnContour = isOnContour(point1) ? point1 : point2;
            Line linePassingByCenterAndPointOnContour = new Line(center, pointOnContour);
            Point tangentCircleCenter = linePassingByCenterAndPointOnContour.findIntersectionWith(perpendicularBisector);
            return new Circle[] { Circle.fromCenterAndAPoint(pointOnContour, tangentCircleCenter) };
        }

        // TODO Not sure if I need to use the method with a DELTA
        if (perpendicularBisector.isPassingOn(center)) {
            Point[] tangentalPoints = findIntersectingPointsWith(perpendicularBisector);
            return createAllCirclesPassingThroughTwoPointsAndTangentalPoints(point1, point2, tangentalPoints);
        }

        Circle constructionCircle = getTangentalConstructionCircle(point1, point2);

        Point[] intersectingPoints = findIntersectionPointsWith(constructionCircle);
        Line intersectingLine = new Line(intersectingPoints[0], intersectingPoints[1]);
        Line lineFromPoint1ToPoint2 = new Line(point1, point2);
        Point pointIntersection = intersectingLine.findIntersectionWith(lineFromPoint1ToPoint2);

        Point[] tangentalPoints = findTangentPointsOfLinePassingBy(pointIntersection);
        return createAllCirclesPassingThroughTwoPointsAndTangentalPoints(point1, point2, tangentalPoints);
    }

    private Line getPerpendicularBisectorOf(Point point1, Point point2) {
        Point centerPoint = Point.centerPointBetween(point1, point2);
        return new Line(point1, point2).findPerpendicularLinePassingThrough(centerPoint);
        //            Segment segment = new Segment(point1, point2);
        //            return segment.findPerpendicularLinePassingThrough(segment.getCenterPoint());
    }

    private Circle[] createAllCirclesPassingThroughTwoPointsAndTangentalPoints(Point point1, Point point2, Point[] tangentalPoints) {
        Circle[] circles = new Circle[tangentalPoints.length];
        for (int i = 0; i < tangentalPoints.length; i++) {
            circles[i] = Circle.fromThreePoints(point1, point2, tangentalPoints[i]);
        }
        return circles;
    }

    private Circle getTangentalConstructionCircle(Point point1, Point point2) {
        if (isPointInside(point1) && isPointInside(point2)) {
            Point pointOutsideOfCircle = Point.fromCartesian(center.getX() + getDiameter(), center.getY() + getDiameter());
            return Circle.fromThreePoints(point1, point2, pointOutsideOfCircle);
        }

        Line lineBetweenPoints = new Line(point1, point2);
        // TODO Not sure if I need to use the method with a DELTA
        if (lineBetweenPoints.isPassingOn(center)) {
            Line perpendicularLine = lineBetweenPoints.findPerpendicularLinePassingThrough(center);
            Point constructionPoint = perpendicularLine.findOffsettedPointDownward(radius / 2.0, center);
            return Circle.fromThreePoints(point1, point2, constructionPoint);
        } else {
            return Circle.fromThreePoints(point1, point2, center);
        }
    }

    private double calculateSquaredDistanceFromCircleTo(Point point) {
        return Math.pow(point.getX() - center.getX(), 2) + Math.pow(point.getY() - center.getY(), 2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, center);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Circle)) {
            return false;
        }

        Circle other = (Circle) obj;
        return Objects.equals(radius, other.radius) && Objects.equals(center, other.center);
    }

    @Override
    public String toString() {
        return "(x - " + center.getX() + ")² + (y - " + center.getY() + ")² = " + radius + "²";
    }

    public static Circle fromRadius(double radius, Point center) {
        return new Circle(radius, center);
    }

    public static Circle fromCenterAndAPoint(Point point, Point center) {
        double radius = point.getDistanceTo(center);
        return new Circle(radius, center);
    }

    public static Circle fromThreePoints(Point a, Point b, Point c) {
        // http://www.mathopenref.com/print3pointcircle.html
        Point abCenter = Point.centerPointBetween(a, b);
        Point bcCenter = Point.centerPointBetween(b, c);
        Line perpendicularBisectorOfABLine = new Line(a, b).findPerpendicularLinePassingThrough(abCenter);
        Line perpendicularBisectorOfBCLine = new Line(b, c).findPerpendicularLinePassingThrough(bcCenter);
        Point center = perpendicularBisectorOfABLine.findIntersectionWith(perpendicularBisectorOfBCLine);
        double radius = center.getDistanceTo(a);

        return new Circle(radius, center);
    }
}
