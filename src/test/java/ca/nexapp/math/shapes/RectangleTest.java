package ca.nexapp.math.shapes;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import ca.nexapp.math.units.Point;

public class RectangleTest {

    private static final double A_WIDTH = 50;
    private static final double A_HEIGHT = 60;

    private static final double X1 = -25;
    private static final double X2 = 25;
    private static final double Y1 = 30;
    private static final double Y2 = -30;

    private static final Point CENTER_POINT = Point.ORIGIN;
    private static final Point TOP_LEFT_POINT = Point.fromCartesian(X1, Y1);

    private static final double TOLERANCE = 0.0000001;

    @Test
    public void whenCreatingARectangleFromCorners_ShouldHaveTheCorrespondingDimension() {
        Rectangle rectangle = Rectangle.fromCorners(X1, Y1, X2, Y2);

        assertThat(rectangle.getWidth()).isWithin(TOLERANCE).of(A_WIDTH);
        assertThat(rectangle.getHeight()).isWithin(TOLERANCE).of(A_HEIGHT);
    }

    @Test
    public void whenCreatingARectangleFromCorners_ShouldHaveTheCorrespondingCenterPoint() {
        Rectangle rectangle = Rectangle.fromCorners(X1, Y1, X2, Y2);

        assertThat(rectangle.getCenter()).isEqualTo(CENTER_POINT);
    }

    @Test
    public void whenCreatingARectangleFromCorners_ShouldHaveTheCorrespondingCorners() {
        Rectangle rectangle = Rectangle.fromCorners(X1, Y1, X2, Y2);

        assertThat(rectangle.getX1()).isWithin(TOLERANCE).of(X1);
        assertThat(rectangle.getX2()).isWithin(TOLERANCE).of(X2);
        assertThat(rectangle.getY1()).isWithin(TOLERANCE).of(Y1);
        assertThat(rectangle.getY2()).isWithin(TOLERANCE).of(Y2);
    }

    @Test
    public void whenCreatingARectangleWithTheCenterPoint_ShouldHaveTheCorrespondingDimension() {
        Rectangle rectangle = Rectangle.fromCenter(CENTER_POINT, A_WIDTH, A_HEIGHT);

        assertThat(rectangle.getWidth()).isWithin(TOLERANCE).of(A_WIDTH);
        assertThat(rectangle.getHeight()).isWithin(TOLERANCE).of(A_HEIGHT);
    }

    @Test
    public void whenCreatingARectangleWithTheCenterPoint_ShouldHaveTheCorrespondingCenterPoint() {
        Rectangle rectangle = Rectangle.fromCenter(CENTER_POINT, A_WIDTH, A_HEIGHT);

        assertThat(rectangle.getCenter()).isEqualTo(CENTER_POINT);
    }

    @Test
    public void whenCreatingARectangleWithTheCenterPoint_ShouldReturnTheCorrespondingCorners() {
        Rectangle rectangle = Rectangle.fromCenter(CENTER_POINT, A_WIDTH, A_HEIGHT);

        assertThat(rectangle.getX1()).isWithin(TOLERANCE).of(X1);
        assertThat(rectangle.getX2()).isWithin(TOLERANCE).of(X2);
        assertThat(rectangle.getY1()).isWithin(TOLERANCE).of(Y1);
        assertThat(rectangle.getY2()).isWithin(TOLERANCE).of(Y2);
    }

    @Test
    public void whenCreatingARectangleWithTheTopLeftCorner_ShouldHaveTheCorrespondingDimension() {
        Rectangle rectangle = Rectangle.fromTopLeftCorner(TOP_LEFT_POINT, A_WIDTH, A_HEIGHT);

        assertThat(rectangle.getWidth()).isWithin(TOLERANCE).of(A_WIDTH);
        assertThat(rectangle.getHeight()).isWithin(TOLERANCE).of(A_HEIGHT);
    }

    @Test
    public void whenCreatingARectangleWithTheTopLeftCorner_ShouldHaveTheCorrespondingCenterPoint() {
        Rectangle rectangle = Rectangle.fromTopLeftCorner(TOP_LEFT_POINT, A_WIDTH, A_HEIGHT);

        assertThat(rectangle.getCenter()).isEqualTo(CENTER_POINT);
    }

    @Test
    public void whenCreatingARectangleWithTheTopLeftCorner_ShouldReturnTheCorrespondingCorners() {
        Rectangle rectangle = Rectangle.fromTopLeftCorner(TOP_LEFT_POINT, A_WIDTH, A_HEIGHT);

        assertThat(rectangle.getX1()).isWithin(TOLERANCE).of(X1);
        assertThat(rectangle.getX2()).isWithin(TOLERANCE).of(X2);
        assertThat(rectangle.getY1()).isWithin(TOLERANCE).of(Y1);
        assertThat(rectangle.getY2()).isWithin(TOLERANCE).of(Y2);
    }

    @Test
    public void theSameRectangleShouldBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCenter(CENTER_POINT, A_WIDTH, A_HEIGHT);

        assertThat(rectangle.isIntersecting(rectangle)).isTrue();
    }

    @Test
    public void givenSideBySideRectangleOnTop_ShouldBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCorners(-10, 10, 10, -10);
        Rectangle besideRectangleOnTop = Rectangle.fromCorners(-10, 20, 10, 10);

        assertThat(rectangle.isIntersecting(besideRectangleOnTop)).isTrue();
    }

    @Test
    public void givenSideBySideRectangleOnBottom_ShouldBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCorners(-10, 10, 10, -10);
        Rectangle besideRectangleOnBottom = Rectangle.fromCorners(-10, -10, 10, -20);

        assertThat(rectangle.isIntersecting(besideRectangleOnBottom)).isTrue();
    }

    @Test
    public void givenSideBySideRectangleOnLeft_ShouldBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCorners(-10, 10, 10, -10);
        Rectangle besideRectangleOnLeft = Rectangle.fromCorners(-20, 10, -10, -10);

        assertThat(rectangle.isIntersecting(besideRectangleOnLeft)).isTrue();
    }

    @Test
    public void givenSideBySideRectangleOnRight_ShouldBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCorners(-10, 10, 10, -10);
        Rectangle besideRectangleOnRight = Rectangle.fromCorners(10, 10, 30, -10);

        assertThat(rectangle.isIntersecting(besideRectangleOnRight)).isTrue();
    }

    @Test
    public void givenAnOutsideRectangleOnRight_ShouldNotBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCorners(-10, 10, 10, -10);
        Rectangle outsideRectangleOnRight = Rectangle.fromCorners(15, 20, 30, -20);

        assertThat(rectangle.isIntersecting(outsideRectangleOnRight)).isFalse();
    }

    @Test
    public void givenAnOutsideRectangleOnLeft_ShouldNotBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCorners(-10, 10, 10, -10);
        Rectangle outsideRectangleOnLeft = Rectangle.fromCorners(-30, 20, -15, -20);

        assertThat(rectangle.isIntersecting(outsideRectangleOnLeft)).isFalse();
    }

    @Test
    public void givenAnOutsideRectangleOnTop_ShouldNotBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCorners(-10, 10, 10, -10);
        Rectangle outsideRectangleOnTop = Rectangle.fromCorners(-30, 15, -15, 30);

        assertThat(rectangle.isIntersecting(outsideRectangleOnTop)).isFalse();
    }

    @Test
    public void givenAnOutsideRectangleOnBottom_ShouldNotBeIntersecting() {
        Rectangle rectangle = Rectangle.fromCorners(-10, 10, 10, -10);
        Rectangle outsideRectangleOnBottom = Rectangle.fromCorners(-30, -15, -15, -30);

        assertThat(rectangle.isIntersecting(outsideRectangleOnBottom)).isFalse();
    }
}
