package ca.nexapp.math.shapes;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

import ca.nexapp.math.functions.Line;
import ca.nexapp.math.units.Angle;
import ca.nexapp.math.units.Point;

public class TriangleTest {

    private static final Point POINT_A = Point.ORIGIN;
    private static final Point POINT_B = Point.fromCartesian(0, 4);
    private static final Point POINT_C = Point.fromCartesian(3, 0);

    private static final Angle ANGLE_AT_POINT_A = Angle.fromDegrees(90.00);
    private static final Angle BISECTOR_ANGLE_AT_POINT_A = Angle.fromDegrees(90.00 / 2.00);

    private static final Angle ANGLE_AT_POINT_B = Angle.fromDegrees(36.86989764584401);
    private static final Angle BISECTOR_ANGLE_AT_POINT_B = Angle.fromDegrees(36.86989764584401 / 2.00);

    private static final Angle ANGLE_AT_POINT_C = Angle.fromDegrees(53.13010235415598);
    private static final Angle BISECTOR_ANGLE_AT_POINT_C = Angle.fromDegrees(53.13010235415598 / 2.00);

    private static final Point POINT_IN_CENTER_OF_TRIANGLE_A_B_C = Point.fromCartesian(1, 1);

    private static final double DELTA = 0.01;

    private Triangle triangle;

    @Before
    public void setUp() {
        triangle = new Triangle(POINT_A, POINT_B, POINT_C);
    }

    @Test
    public void canRetrieveTheAngleAtPointA() {
        assertThat(triangle.getAngleAtA()).isEqualTo(ANGLE_AT_POINT_A);
    }

    @Test
    public void canRetrieveTheAngleAtPointB() {
        assertThat(triangle.getAngleAtB()).isEqualTo(ANGLE_AT_POINT_B);
    }

    @Test
    public void canRetrieveTheAngleAtPointC() {
        assertThat(triangle.getAngleAtC()).isEqualTo(ANGLE_AT_POINT_C);
    }

    @Test
    public void canRetrieveTheBisectorAngleAtPointA() {
        assertThat(triangle.getBisectorAngleAtA()).isEqualTo(BISECTOR_ANGLE_AT_POINT_A);
    }

    @Test
    public void canRetrieveTheBisectorAngleAtPointB() {
        assertThat(triangle.getBisectorAngleAtB()).isEqualTo(BISECTOR_ANGLE_AT_POINT_B);
    }

    @Test
    public void canRetrieveTheBisectorAngleAtPointC() {
        assertThat(triangle.getBisectorAngleAtC()).isEqualTo(BISECTOR_ANGLE_AT_POINT_C);
    }

    @Test
    public void canFindTheCenterPoint() {
        assertThat(POINT_IN_CENTER_OF_TRIANGLE_A_B_C).isEqualTo(triangle.findCenterPoint());
    }

    @Test
    public void theBisectorLineFromAShouldPassOnPointA() {
        Line bisectorLine = triangle.getBisectorLineFromA();

        assertThat(bisectorLine.isPassingOn(POINT_A)).isTrue();
    }

    @Test
    public void theBisectorLineFromAShouldPassOnTheCenterPoint() {
        Line bisectorLine = triangle.getBisectorLineFromA();

        assertThat(bisectorLine.isPassingOn(triangle.findCenterPoint())).isTrue();
    }

    @Test
    public void theBisectorLineFromBShouldPassOnPointB() {
        Line bisectorLine = triangle.getBisectorLineFromB();

        assertThat(bisectorLine.isPassingOn(POINT_B)).isTrue();
    }

    @Test
    public void theBisectorLineFromBShouldPassOnTheCenterPoint() {
        Line bisectorLine = triangle.getBisectorLineFromB();

        assertThat(bisectorLine.isPassingOn(triangle.findCenterPoint())).isTrue();
    }

    @Test
    public void theBisectorLineFromCShouldPassOnPointC() {
        Line bisectorLine = triangle.getBisectorLineFromC();

        assertThat(bisectorLine.isPassingOn(POINT_C)).isTrue();
    }

    @Test
    public void theBisectorLineFromCShouldPassOnTheCenterPoint() {
        Line bisectorLine = triangle.getBisectorLineFromC();

        assertThat(bisectorLine.isPassingOn(triangle.findCenterPoint())).isTrue();
    }
}
