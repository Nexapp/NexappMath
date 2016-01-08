package ca.nexapp.math.shapes;

import ca.nexapp.math.units.Point;

public class Rectangle {

    private final Point topLeftPoint;
    private final Point topRightPoint;
    private final Point bottomLeftPoint;

    private Rectangle(Point topLeftPoint, Point topRightPoint, Point bottomLeftPoint) {
        this.topLeftPoint = topLeftPoint;
        this.topRightPoint = topRightPoint;
        this.bottomLeftPoint = bottomLeftPoint;
    }

    public double getWidth() {
        return topLeftPoint.getDistanceTo(topRightPoint);
    }

    public double getHeight() {
        return topLeftPoint.getDistanceTo(bottomLeftPoint);
    }

    public double getX1() {
        return topLeftPoint.getX();
    }

    public double getX2() {
        return topRightPoint.getX();
    }

    public double getY1() {
        return topLeftPoint.getY();
    }

    public double getY2() {
        return bottomLeftPoint.getY();
    }

    public Point getCenter() {
        double centerX = (getX1() + getX2()) / 2;
        double centerY = (getY1() + getY2()) / 2;
        return Point.fromCartesian(centerX, centerY);
    }

    public boolean isIntersecting(Rectangle other) {
        boolean left = isOverlapingToLeft(other);
        boolean right = isOverlapingToRight(other);
        boolean top = isOverlapingToTop(other);
        boolean bottom = isOverlapingToBottom(other);
        return !(left || right || top || bottom);
    }

    private boolean isOverlapingToLeft(Rectangle other) {
        return other.getX2() < getX1();
    }

    private boolean isOverlapingToRight(Rectangle other) {
        return other.getX1() > getX2();
    }

    private boolean isOverlapingToTop(Rectangle other) {
        return other.getY2() > getY1();
    }

    private boolean isOverlapingToBottom(Rectangle other) {
        return other.getY1() < getY2();
    }

    public static Rectangle fromCorners(double x1, double y1, double x2, double y2) {
        Point topLeftPoint = Point.fromCartesian(x1, y1);
        Point topRightPoint = Point.fromCartesian(x2, y1);
        Point bottomLeftPoint = Point.fromCartesian(x1, y2);
        return new Rectangle(topLeftPoint, topRightPoint, bottomLeftPoint);
    }

    public static Rectangle fromCenter(Point center, double width, double height) {
        double x1 = center.getX() - width / 2.0;
        double x2 = center.getX() + width / 2.0;
        double y1 = center.getY() + height / 2.0;
        double y2 = center.getY() - height / 2.0;
        return fromCorners(x1, y1, x2, y2);
    }

    public static Rectangle fromTopLeftCorner(Point topLeftPoint, double width, double height) {
        double x2 = topLeftPoint.getX() + width;
        double y2 = topLeftPoint.getY() - height;
        return fromCorners(topLeftPoint.getX(), topLeftPoint.getY(), x2, y2);
    }
}
