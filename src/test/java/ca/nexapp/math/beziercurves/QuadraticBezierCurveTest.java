package ca.nexapp.math.beziercurves;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Before;
import org.junit.Test;

import ca.nexapp.math.functions.Segment;
import ca.nexapp.math.units.Point;

public class QuadraticBezierCurveTest {

    private static final Point A_STARTING_POINT = Point.fromCartesian(5, 5);
    private static final Point AN_ENDING_POINT = Point.fromCartesian(15, 5);
    private static final Point A_CONTROL_POINT = Point.fromCartesian(10, 10);

    private static final Point THE_MIDDLE_POINT = Point.fromCartesian(10, 7.5);

    private static final Segment TWO_INTERSECTING_POINTS_LINE = new Segment(A_STARTING_POINT, AN_ENDING_POINT);
    private static final Segment ONLY_ONE_INTERSECTING_POINT_LINE = new Segment(Point.ORIGIN, A_STARTING_POINT);
    private static final Segment NON_INTERSECTING_LINE = new Segment(Point.fromCartesian(-500, -500), Point.fromCartesian(-1000, -750));

    private QuadraticBezierCurve bezierCurve;

    @Before
    public void setUp() {
        bezierCurve = new QuadraticBezierCurve(A_STARTING_POINT, A_CONTROL_POINT, AN_ENDING_POINT);
    }

    @Test
    public void givenAZeroValuedT_WhenFindingPointAtT_ShouldReturnTheStartingPoint() {
        Point actualPoint = bezierCurve.findPointAt(0.00d);

        assertThat(actualPoint).isEqualTo(A_STARTING_POINT);
    }

    @Test
    public void givenAHalfValuedT_WhenFindingPointAtT_ShouldReturnTheMiddlePoint() {
        Point actualPoint = bezierCurve.findPointAt(0.50d);

        assertThat(actualPoint).isEqualTo(THE_MIDDLE_POINT);
    }

    @Test
    public void givenAStraightCurveAndAHalfValuedT_WhenFindingPointAtT_ShouldReturnTheMiddlePoint() {
        bezierCurve = new QuadraticBezierCurve(Point.ORIGIN, Point.fromCartesian(5, 5), Point.fromCartesian(10, 10));

        Point actualPoint = bezierCurve.findPointAt(0.50d);

        assertThat(actualPoint).isEqualTo(Point.fromCartesian(5, 5));
    }

    @Test
    public void givenAOneValuedT_WhenFindingPointAtT_ShouldReturnTheEndingPoint() {
        Point actualPoint = bezierCurve.findPointAt(1.00d);

        assertThat(actualPoint).isEqualTo(AN_ENDING_POINT);
    }

    @Test
    public void givenALineThatHasTwoIntersectingPoints_WhenFindingTheLeftIntersectingPoint_ShouldReturnTheStartingPoint() {
        Point actualPoint = bezierCurve.getLeftIntersectingPointWith(TWO_INTERSECTING_POINTS_LINE);

        assertThat(actualPoint).isEqualTo(A_STARTING_POINT);
    }

    @Test
    public void givenALineThatHasOnlyOneIntersectingPoint_WhenFindingTheLeftIntersectingPoint_ShouldReturnTheStartingPoint() {
        Point actualPoint = bezierCurve.getLeftIntersectingPointWith(ONLY_ONE_INTERSECTING_POINT_LINE);

        assertThat(actualPoint).isEqualTo(A_STARTING_POINT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenALineThatDoesNotInterceptTheBezierCurve_WhenFindingTheLeftIntersectingPoint_ShouldThrowAnException() {
        bezierCurve.getLeftIntersectingPointWith(NON_INTERSECTING_LINE);
    }

    @Test
    public void givenALineThatHasTwoIntersectingPoints_WhenFindingTheRightIntersectingPoint_ShouldReturnTheEndingPoint() {
        Point actualPoint = bezierCurve.getRightIntersectingPointWith(TWO_INTERSECTING_POINTS_LINE);

        assertThat(actualPoint).isEqualTo(AN_ENDING_POINT);
    }

    @Test
    public void givenALineThatHasOnlyOneIntersectingPoint_WhenFindingTheRightIntersectingPoint_ShouldReturnTheStartingPoint() {
        Point actualPoint = bezierCurve.getRightIntersectingPointWith(ONLY_ONE_INTERSECTING_POINT_LINE);

        assertThat(actualPoint).isEqualTo(A_STARTING_POINT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenALineThatDoesNotInterceptTheBezierCurve_WhenFindingTheRightIntersectingPoint_ShouldThrowAnException() {
        bezierCurve.getRightIntersectingPointWith(NON_INTERSECTING_LINE);
    }
}
