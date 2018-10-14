package ca.nexapp.math.shapes;

import static com.google.common.truth.Truth.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import ca.nexapp.math.units.Angle;
import ca.nexapp.math.units.Point;

public class EllipseTest {

    private static final double SEMI_MAJOR_AXIS = 4;
    private static final double SEMI_MINOR_AXIS = 2;

    private static final double A_RADIUS = Math.sqrt(6.5);
    private static final Point ANOTHER_CENTER_POINT = Point.fromCartesian(1.00, 0.00);
    private static final Point FAR_AWAY_CENTER = Point.fromCartesian(9999, 9999);

    private static final Circle A_CIRCLE = Circle.fromRadius(A_RADIUS, ANOTHER_CENTER_POINT);

    private static final Angle ANGLE_45 = Angle.fromDegrees(45.00);
    private static final Angle ANGLE_90 = Angle.fromDegrees(90.00);
    //    private static final Angle ANGLE_180 = Angle.fromDegrees(180.00);
    //    private static final Angle ANGLE_270 = Angle.fromDegrees(270.00);
    //    private static final Angle ANGLE_360 = Angle.fromDegrees(360.00);

    private static final double UNITS = 50;

    private static final double DELTA = 0.01;

    @Test
    public void canCreateAnEllipseFromTheCircle() {
        Ellipse ellipse = Ellipse.fromCircle(A_CIRCLE);

        assertThat(ellipse.getRadiusX()).isWithin(DELTA).of(A_CIRCLE.getRadius());
        assertThat(ellipse.getRadiusY()).isWithin(DELTA).of(A_CIRCLE.getRadius());
        assertThat(ellipse.getCenter()).isEqualTo(A_CIRCLE.getCenter());
        assertThat(ellipse.getRotationAngle()).isEqualTo(Angle.ZERO_DEGREES);
    }

    @Test
    public void whenIncreasingRadiuses_ThenEachRadiusShouldBeIncreasedByTheAmountOfUnits() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);

        Ellipse increasedEllipse = ellipse.increaseRadiusesBy(UNITS);

        assertThat(increasedEllipse.getRadiusX()).isWithin(DELTA).of(ellipse.getRadiusX() + 5);
        assertThat(increasedEllipse.getRadiusY()).isWithin(DELTA).of(ellipse.getRadiusY() + 5);
    }

    @Test
    public void whenDecreasingRadiuses_ThenEachRadiusShouldBeDecreasedByTheAmountOfUnits() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);

        Ellipse decreasedEllipse = ellipse.decreaseRadiusesBy(UNITS);

        assertThat(decreasedEllipse.getRadiusX()).isWithin(DELTA).of(ellipse.getRadiusX() - UNITS);
        assertThat(decreasedEllipse.getRadiusY()).isWithin(DELTA).of(ellipse.getRadiusY() - UNITS);
    }

    @Test
    public void givenFarAwayCircle_ShouldNotBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle farAwayCircle = Circle.fromRadius(A_RADIUS, FAR_AWAY_CENTER);

        assertThat(ellipse.isIntersecting(farAwayCircle)).isFalse();
    }

    @Test
    public void givenTangentalCircleOnLeft_ShouldBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Point circleCenter = Point.fromCartesian(ellipse.getCenter().getX() + ellipse.getWidth(), ellipse.getCenter().getY());
        Circle tangentalCircle = Circle.fromRadius(ellipse.getRadiusX(), circleCenter);

        assertThat(ellipse.isIntersecting(tangentalCircle)).isTrue();
    }

    @Test
    public void givenTangentalCircleOnRight_ShouldBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Point circleCenter = Point.fromCartesian(ellipse.getCenter().getX() - ellipse.getWidth(), ellipse.getCenter().getY());
        Circle tangentalCircle = Circle.fromRadius(ellipse.getRadiusX(), circleCenter);

        assertThat(ellipse.isIntersecting(tangentalCircle)).isTrue();
    }

    @Test
    public void givenTangentalCircleOnTop_ShouldBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Point circleCenter = Point.fromCartesian(ellipse.getCenter().getX(), ellipse.getCenter().getY() + ellipse.getHeight());
        Circle tangentalCircle = Circle.fromRadius(ellipse.getRadiusY(), circleCenter);

        assertThat(ellipse.isIntersecting(tangentalCircle)).isTrue();
    }

    @Test
    public void givenTangentalCircleOnBottom_ShouldBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Point circleCenter = Point.fromCartesian(ellipse.getCenter().getX(), ellipse.getCenter().getY() - ellipse.getHeight());
        Circle tangentalCircle = Circle.fromRadius(ellipse.getRadiusY(), circleCenter);

        assertThat(ellipse.isIntersecting(tangentalCircle)).isTrue();
    }

    @Test
    @Ignore
    public void givenAnInsideCircle_ShouldNotBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle insideCircle = Circle.fromRadius(1, Point.ORIGIN);

        assertThat(ellipse.isIntersecting(insideCircle)).isFalse();
    }

    @Test
    public void givenACrossingCircleInTopRight_ShouldBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle crossingCircleOnTopRight = Circle.fromRadius(1, Point.fromCartesian(3, 1.5));

        assertThat(ellipse.isIntersecting(crossingCircleOnTopRight)).isTrue();
    }

    @Test
    public void givenACrossingCircleInTopLeft_ShouldBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle crossingCircleOnTopLeft = Circle.fromRadius(1, Point.fromCartesian(-3, 1.5));

        assertThat(ellipse.isIntersecting(crossingCircleOnTopLeft)).isTrue();
    }

    @Test
    public void givenACrossingCircleInBottomRight_ShouldBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle crossingCircleOnBottomRight = Circle.fromRadius(1, Point.fromCartesian(3, -1.5));

        assertThat(ellipse.isIntersecting(crossingCircleOnBottomRight)).isTrue();
    }

    @Test
    public void givenACrossingCircleInBottomLeft_ShouldBeIntersecting() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle crossingCircleOnBottomLeft = Circle.fromRadius(1, Point.fromCartesian(-3, -1.5));

        assertThat(ellipse.isIntersecting(crossingCircleOnBottomLeft)).isTrue();
    }

    @Test
    public void givenTwoIntersectingEllipsesConsideredAsCircle_ShouldBeIntersecting() {
        Ellipse anEllipse = Ellipse.fromCircle(Circle.fromRadius(50, Point.ORIGIN));
        Ellipse anOtherEllipse = Ellipse.fromCircle(Circle.fromRadius(10, Point.fromCartesian(40, 40)));

        assertThat(anEllipse.isIntersecting(anOtherEllipse)).isTrue();
    }

    @Test
    public void givenTwoIntersectingEllipses_ShouldBeIntersecting() {
        Ellipse anEllipse = Ellipse.withRadiuses(50, 45, Point.ORIGIN);
        Ellipse anOtherEllipse = Ellipse.fromCircle(Circle.fromRadius(10, Point.fromCartesian(40, 40)));

        assertThat(anEllipse.isIntersecting(anOtherEllipse)).isTrue();
    }

    @Test
    public void givenTwoNonIntersectingEllipsesConsideredAsCircle_ShouldNotBeIntersecting() {
        Ellipse anEllipse = Ellipse.fromCircle(Circle.fromRadius(10, Point.ORIGIN));
        Ellipse anOtherEllipse = Ellipse.fromCircle(Circle.fromRadius(10, Point.fromCartesian(40, 40)));

        assertThat(anEllipse.isIntersecting(anOtherEllipse)).isFalse();
    }

    @Test
    public void givenACircleWithSameCenter_WhenFindingIntersectionPoints_ShouldHaveFourSolutions() {
        // http://www.wolframalpha.com/input/?i=solve+x%5E2%2F4%5E2+%2B+y%5E2%2F2%5E2+%3D+1+and+%28x-0%29%5E2+%2B+%28y-0%29%5E2+%3D+6.5
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle circle = Circle.fromRadius(A_RADIUS, ellipse.getCenter());

        List<Point> intersections = ellipse.findAllIntersectionPointsWith(circle);

        Point solution1 = Point.fromCartesian(1.82574, 1.77951);
        Point solution2 = Point.fromCartesian(-1.82574, 1.77951);
        Point solution3 = Point.fromCartesian(-1.82574, -1.77951);
        Point solution4 = Point.fromCartesian(1.82574, -1.77951);
        assertThat(intersections).containsExactly(solution1, solution2, solution3, solution4);
    }

    @Test
    public void givenACircleWithAnotherCenter_WhenFindingIntersectionPoints_ShouldHaveFourSolutions() {
        // http://www.wolframalpha.com/input/?i=solve+x%5E2%2F4%5E2+%2B+y%5E2%2F2%5E2+%3D+1+and+%28x-1%29%5E2+%2B+%28y-0%29%5E2+%3D+6.5
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle circle = Circle.fromRadius(A_RADIUS, ANOTHER_CENTER_POINT);

        List<Point> intersections = ellipse.findAllIntersectionPointsWith(circle);

        Point solution1 = Point.fromCartesian(3.27698, 1.14688);
        Point solution2 = Point.fromCartesian(-0.610317, 1.97658);
        Point solution3 = Point.fromCartesian(3.27698, -1.14688);
        Point solution4 = Point.fromCartesian(-0.610317, -1.97658);
        assertThat(intersections).containsExactly(solution1, solution2, solution3, solution4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenACircleFarAway_WhenFindingIntersectionPoints_ShouldThrowAnException() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Circle farAwayCircle = Circle.fromRadius(A_RADIUS, FAR_AWAY_CENTER);

        ellipse.findAllIntersectionPointsWith(farAwayCircle);
    }

    @Test
    public void givenTwoRotatedEllipses_WhenFindingIntersectionPoints_ShouldHaveFourSolutions() {
        // http://www.wolframalpha.com/input/?i=%28x+*+cos%28p%29+%2B+y+*+sin%28p%29%29%5E2+%2F+2%5E2+%2B+%28x+*+sin%28p%29+-+y+*+cos%28p%29%29%5E2+%2F+3%5E2+%3D+1+and+%28x+*+cos%28q%29+%2B+y+*+sin%28q%29%29%5E2+%2F+2%5E2+%2B+%28x+*+sin%28q%29+-+y+*+cos%28q%29%29%5E2+%2F+3%5E2+%3D+1+where+p+%3D+1.57+and+q+%3D+0.78
        Ellipse anEllipseRotated = Ellipse.withRadiuses(2, 3, Point.ORIGIN, ANGLE_90);
        Ellipse anotherEllipseRotated = Ellipse.withRadiuses(2, 3, Point.ORIGIN, ANGLE_45);

        List<Point> intersections = anEllipseRotated.findAllIntersectionPointsWith(anotherEllipseRotated);

        Point solution1 = Point.fromCartesian(-2.54273, 1.06247);
        Point solution2 = Point.fromCartesian(0.804904, 1.92631);
        Point solution3 = Point.fromCartesian(-0.804904, -1.92631);
        Point solution4 = Point.fromCartesian(2.54273, -1.06247);
        assertThat(intersections).containsExactly(solution1, solution2, solution3, solution4);
    }

    @Test
    public void givenARotatedEllipseAndACircleCrossingItByFourPoints_WhenFindingIntersectionPoints_ShouldHaveFourSolutions() {
        // http://www.wolframalpha.com/input/?i=%28%28x+-+h%29*cos%28p%29+%2B+%28y+-+k%29*sin%28p%29%29%5E2+%2F+60%5E2+%2B+%28%28x+-+h%29*sin%28p%29+-+%28y+-+k%29*cos%28p%29%29%5E2+%2F+33%5E2+%3D+1+and+%28x+-+175%29%5E2+%2F+47%5E2+%2B+%28-%28y%2B263%29%29%5E2+%2F+47%5E2+%3D+1+where+p+%3D+-0.1428+and+h%3D170+and+k%3D-261
        Ellipse anEllipseRotated = Ellipse.withRadiuses(60, 33, Point.fromCartesian(170, -261), Angle.fromRadians(-0.1428));
        Circle circle = Circle.fromRadius(47, Point.fromCartesian(175, -263));

        List<Point> intersections = anEllipseRotated.findAllIntersectionPointsWith(circle);

        Point solution1 = Point.fromCartesian(142.858, -228.709);
        Point solution2 = Point.fromCartesian(219.276, -247.232);
        Point solution3 = Point.fromCartesian(132.512, -283.094);
        Point solution4 = Point.fromCartesian(215.376, -287.058);
        assertThat(intersections).containsExactly(solution1, solution2, solution3, solution4);
    }

    @Test
    public void givenARotatedEllipseAndACircleCrossingItByTwoPoints_WhenFindingIntersectionPoints_ShouldHaveTwoSolutions() {
        // http://www.wolframalpha.com/input/?i=%28%28x+-+h%29*cos%28p%29+%2B+%28y+-+k%29*sin%28p%29%29%5E2+%2F+25%5E2+%2B+%28%28x+-+h%29*sin%28p%29+-+%28y+-+k%29*cos%28p%29%29%5E2+%2F+33%5E2+%3D+1+and+%28x+-+175.2924%29%5E2+%2F+47%5E2+%2B+%28-%28y%2B263.2225%29%29%5E2+%2F+47%5E2+%3D+1+where+p+%3D+-0.1428+and+h%3D120.7346+and+k%3D-260.3766
        Ellipse anEllipseRotated = Ellipse.withRadiuses(25, 33, Point.fromCartesian(120.735, -260.3766), Angle.fromRadians(-0.1428));
        Circle circle = Circle.fromRadius(47, Point.fromCartesian(175.2924, -263.2225));

        List<Point> intersections = anEllipseRotated.findAllIntersectionPointsWith(circle);
        Point solution1 = Point.fromCartesian(137.945, -234.688);
        Point solution2 = Point.fromCartesian(134.434, -286.451);
        assertThat(intersections).containsExactly(solution1, solution2);
    }

    @Test
    public void givenATangentalCircleByTheOutside_WhenFindingIntersectionPoints_ShouldHaveOneSolution() {
        // http://www.wolframalpha.com/input/?i=%28x*cos%280%29+%2B+y*sin%280%29%29%C2%B2+%2F+4%C2%B2+%2B+%28x*sin%280%29+-+y*cos%280%29%29%C2%B2+%2F+2%C2%B2+%3D+1+and+%28x+-+8%29%C2%B2+%2B+%28y+-+0%29%C2%B2+%3D+4%C2%B2
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        Point circleCenter = Point.fromCartesian(ellipse.getCenter().getX() + ellipse.getWidth(), ellipse.getCenter().getY());
        Circle tangentalCircle = Circle.fromRadius(ellipse.getRadiusX(), circleCenter);

        List<Point> intersections = ellipse.findAllIntersectionPointsWith(tangentalCircle);

        Point solution1 = Point.fromCartesian(4.0, 0.0);
        assertThat(intersections).containsExactly(solution1);
    }

    @Test
    public void givenATangentalCircleByTheInside_WhenFindingIntersectionPoints_ShouldHaveThreeSolutions() {
        // http://www.wolframalpha.com/input/?i=%28x*cos%280%29+%2B+y*sin%280%29%29%C2%B2+%2F+2%C2%B2+%2B+%28x*sin%280%29+-+y*cos%280%29%29%C2%B2+%2F+4%C2%B2+%3D+1+and+%28x+-+2%29%C2%B2+%2B+%28y+-+0%29%C2%B2+%3D+4%C2%B2
        Ellipse ellipse = Ellipse.withRadiuses(2, 4, Point.ORIGIN);
        Circle tangentalCircle = Circle.fromRadius(4, Point.fromCartesian(2, 0));

        List<Point> intersections = ellipse.findAllIntersectionPointsWith(tangentalCircle);

        Point solution1 = Point.fromCartesian(2.0 / 3.0, 8 * Math.sqrt(2) / 3.0);
        Point solution2 = Point.fromCartesian(2.0 / 3.0, -(8 * Math.sqrt(2)) / 3.0);
        Point solution3 = Point.fromCartesian(-2.0, 0.0);
        assertThat(intersections).containsExactly(solution1, solution2, solution3);
    }

    @Test
    public void givenTwoEllipsesConsideredAsCircles_WhenFindingIntersectionPoints_ShouldHaveTwoSolutions() {
        // http://www.wolframalpha.com/input/?i=%28%28x+-+0%29*cos%280%29+%2B+%28y+-+0%29*sin%280%29%29%C2%B2+%2F+50%C2%B2+%2B+%28%28x+-+0%29*sin%280%29+-+%28y+-+0%29*cos%280%29%29%C2%B2+%2F+50%C2%B2+%3D+1+and+%28%28x+-+40%29*cos%280%29+%2B+%28y+-+40%29*sin%280%29%29%C2%B2+%2F+10%C2%B2+%2B+%28%28x+-+40%29*sin%280%29+-+%28y+-+40%29*cos%280%29%29%C2%B2+%2F+10%C2%B2+%3D+1
        Ellipse anEllipse = Ellipse.fromCircle(Circle.fromRadius(50, Point.ORIGIN));
        Ellipse anOtherEllipse = Ellipse.fromCircle(Circle.fromRadius(10, Point.fromCartesian(40, 40)));

        List<Point> intersections = anEllipse.findAllIntersectionPointsWith(anOtherEllipse);

        Point solution1 = Point.fromCartesian(40, 30);
        Point solution2 = Point.fromCartesian(30, 40);
        assertThat(intersections).containsExactly(solution1, solution2);
    }

    //    @Test
    //    public void givenXCoordinateAtRightVertexWhenFindingYValuesShouldReturnOnlyOneValue() {
    //        double xValue = ellipse.findRightVertex().getX();
    //
    //        assertThat(ellipse.findY(xValue)).hasLength(1);
    //    }

    //    @Test
    //    public void givenXCoordinateAtRightVertexWhenFindingYValuesShouldReturnYCoordinateOfRightVextex() {
    //        double xValue = ellipse.findRightVertex().getX();
    //
    //        double[] actualY = ellipse.findY(xValue);
    //        double expectedY = ellipse.findRightVertex().getY();
    //
    //        assertThat(actualY[0]).isWithin(DELTA).of(expectedY);
    //    }

    @Test
    public void givenXCoordinateAtCenterPoint_WhenFindingYValues_ShouldReturnTwoValues() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        double xValue = ellipse.getCenter().getX();

        assertThat(ellipse.findY(xValue)).hasLength(2);
    }

    //    @Test
    //    public void givenXCoordinateAtCenterPoint_WhenFindingYValues_ShouldReturnCoordinateOfTopAndBottomVertex() {
    //        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
    //        double xValue = ellipse.getCenter().getX();
    //
    //        double[] actualYValues = ellipse.findY(xValue);
    //        double expectedTopY = ellipse.findAboveVertex().getY();
    //        double expectedBottomY = ellipse.findBelowVertex().getY();
    //
    //        assertThat(actualYValues[0]).isWithin(DELTA).of(expectedTopY);
    //        assertThat(actualYValues[1]).isWithin(DELTA).of(expectedBottomY);
    //    }

    @Test
    public void givenXCoordinateTooFar_WhenFindingYValues_ShouldReturnNoValues() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        double xValue = FAR_AWAY_CENTER.getX();

        assertThat(ellipse.findY(xValue)).hasLength(0);
    }

    //    @Test
    //    public void givenYCoordinateAtTopVertexWhenFindingXValuesShouldReturnOnlyOneValue() {
    //        double yValue = ellipse.findAboveVertex().getY();
    //
    //        assertThat(ellipse.findX(yValue)).hasLength(1);
    //    }

    //    @Test
    //    public void givenYCoordinateAtTopVertexWhenFindingXValuesShouldReturnXCoordinateOfTopVertex() {
    //        double yValue = ellipse.findAboveVertex().getY();
    //
    //        double[] actualXValues = ellipse.findX(yValue);
    //        double expectedX = ellipse.findAboveVertex().getX();
    //
    //        assertThat(actualXValues[0]).isWithin(DELTA).of(expectedX);
    //    }

    @Test
    public void givenYCoordinateAtCenterPoint_WhenFindingXValues_ShouldReturnTwoValues() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
        double yValue = ellipse.getCenter().getY();

        assertThat(ellipse.findX(yValue)).hasLength(2);
    }

    //    @Test
    //    public void givenYCoordinateAtCenterPoint_WhenFindingXValues_ShouldReturnCoordinateOfLeftAndRightVertex() {
    //        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);
    //        double yValue = ellipse.getCenter().getY();
    //
    //        double[] actualXValues = ellipse.findX(yValue);
    //        double expectedLeftX = ellipse.findLeftVertex().getX();
    //        double expectedRightX = ellipse.findRightVertex().getX();
    //
    //        assertThat(actualXValues[0]).isWithin(DELTA).of(expectedLeftX);
    //        assertThat(actualXValues[1]).isWithin(DELTA).of(expectedRightX);
    //    }

    @Test
    public void givenYCoordinateTooFar_WhenFindingXValues_ShouldReturnNoValues() {
        Ellipse ellipse = Ellipse.withRadiuses(A_RADIUS, A_RADIUS, Point.ORIGIN);

        double yValue = FAR_AWAY_CENTER.getY();

        assertThat(ellipse.findX(yValue)).hasLength(0);
    }

    @Test
    public void givenNoRotation_ShouldNotBeRotated() {
        Ellipse ellipse = Ellipse.withRadiuses(SEMI_MAJOR_AXIS, SEMI_MINOR_AXIS, Point.ORIGIN);

        assertThat(ellipse.isRotated()).isFalse();
    }

    @Test
    public void givenARotation_ShouldBeRotated() {
        Ellipse rotatedEllipse = Ellipse.withRadiuses(SEMI_MAJOR_AXIS, SEMI_MINOR_AXIS, Point.ORIGIN, ANGLE_90);

        assertThat(rotatedEllipse.isRotated()).isTrue();
    }

    @Test
    public void givenAVerySmallRotation_ShouldBeRotated() {
        Ellipse rotatedEllipse = Ellipse.withRadiuses(SEMI_MAJOR_AXIS, SEMI_MINOR_AXIS, Point.ORIGIN, Angle.fromDegrees(0.01));

        assertThat(rotatedEllipse.isRotated()).isTrue();
    }
}
