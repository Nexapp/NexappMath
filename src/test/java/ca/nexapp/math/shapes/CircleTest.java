package ca.nexapp.math.shapes;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import ca.nexapp.math.functions.Line;
import ca.nexapp.math.units.Point;

public class CircleTest {

    private static final double A_RADIUS = 50.0;
    private static final double A_RADIUS_OF_ONE = 1;
    private static final double A_RADIUS_OF_TWO = 2;
    private static final double A_RADIUS_OF_THREE = 3;

    private static final Point A_CENTER = Point.ORIGIN;
    private static final Point POINT_AT_4_0 = Point.fromCartesian(4, 0);
    private static final Point POINT_AT_2_0 = Point.fromCartesian(2, 0);

    private static final double A_SMALLER_RADIUS = 10.0;

    private static final Point POINT_AT_40_40 = Point.fromCartesian(40, 40);
    private static final Point POINT_FAR_AWAY = Point.fromCartesian(1000, 1000);

    private static final double DELTA = 0.01;

    @Test
    public void canCreateACircleFromCenterAndAPoint() {
        Circle circle = Circle.fromCenterAndAPoint(Point.fromCartesian(1, 0), Point.ORIGIN);

        Circle expectedCircle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        assertThat(circle).isEqualTo(expectedCircle);
    }

    @Test
    public void canCreateACircleFromThreePoints() {
        Circle circleWith3Points = Circle.fromThreePoints(Point.ORIGIN, Point.fromCartesian(3, 4), Point.fromCartesian(3, 0));

        assertThat(circleWith3Points.getCenter()).isEqualTo(Point.fromCartesian(1.5, 2));
        assertThat(circleWith3Points.getRadius()).isWithin(DELTA).of(2.5);
    }

    @Test
    public void givenARadius_ThenDiameterShouldBeTwoTimesLonger() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        assertThat(circle.getDiameter()).isWithin(DELTA).of(A_RADIUS * 2.0);
    }

    @Test
    public void givenACircleFarAway_ShouldNotBeIntersecting() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle circleFarAway = Circle.fromRadius(A_SMALLER_RADIUS, POINT_FAR_AWAY);

        assertThat(circle.isInside(circleFarAway)).isFalse();
    }

    @Test
    public void givenACircleInAnotherOne_ShouldNotBeIntersecting() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle insideSmallerCircle = Circle.fromRadius(A_SMALLER_RADIUS, circle.getCenter());

        assertThat(circle.isInside(insideSmallerCircle)).isFalse();
    }

    @Test
    public void givenAnIntersectingCircle_ShouldBeIntersecting() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle intersectingCircle = Circle.fromRadius(A_SMALLER_RADIUS, POINT_AT_40_40);

        assertThat(circle.isIntersecting(intersectingCircle)).isTrue();
    }

    @Test
    public void givenTwoIdenticalCircles_ShouldBeIntersecting() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        assertThat(circle.isIntersecting(circle)).isTrue();
    }

    @Test
    public void givenATangentalCircleOnLeft_ShouldBeIntersecting() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Point center = Point.fromCartesian(circle.getCenter().getX() - circle.getRadius() * 2, circle.getCenter().getY());
        Circle tangentalCircle = Circle.fromRadius(circle.getRadius(), center);

        assertThat(circle.isIntersecting(tangentalCircle)).isTrue();
    }

    @Test
    public void givenATangentalCircleOnRight_ShouldBeIntersecting() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Point center = Point.fromCartesian(circle.getCenter().getX() + circle.getRadius() * 2, circle.getCenter().getY());
        Circle tangentalCircle = Circle.fromRadius(circle.getRadius(), center);

        assertThat(circle.isIntersecting(tangentalCircle)).isTrue();
    }

    @Test
    public void givenATangentalCircleOnTop_ShouldBeIntersecting() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Point center = Point.fromCartesian(circle.getCenter().getX(), circle.getCenter().getY() + circle.getRadius() * 2);
        Circle tangentalCircle = Circle.fromRadius(circle.getRadius(), center);

        assertThat(circle.isIntersecting(tangentalCircle)).isTrue();
    }

    @Test
    public void givenATangentalCircleOnBottomShouldBeIntersecting() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Point center = Point.fromCartesian(circle.getCenter().getX(), circle.getCenter().getY() - circle.getRadius() * 2);
        Circle tangentalCircle = Circle.fromRadius(circle.getRadius(), center);

        assertThat(circle.isIntersecting(tangentalCircle)).isTrue();
    }

    @Test
    public void canFindTheIntersectingPointsBetweenTwoCircles() {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/CircleCircleIntersection.htm
        Circle circle1 = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);
        Circle circle2 = Circle.fromRadius(3, Point.fromCartesian(4, 0));

        Point[] points = circle1.findIntersectionPointsWith(circle2);
        Point expectedPoint1 = Point.fromCartesian(1.375, 1.452);
        Point expectedPoint2 = Point.fromCartesian(1.375, -1.452);

        assertThat(points).asList().containsAllOf(expectedPoint1, expectedPoint2);
        //        assertAbout(PointsSubject.POINTS).that(Arrays.asList(points)).containsPointWithinDelta(expectedPoint1, DELTA);
        //        assertAbout(PointsSubject.POINTS).that(Arrays.asList(points)).containsPointWithinDelta(expectedPoint2, DELTA);
    }

    @Test
    public void givenTwoTangentalCircles_WhenGettingIntersectionPoints_ShouldReturnOnlyOnePoint() {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/CircleCircleIntersection.htm
        Circle circle1 = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);
        Circle circle2 = Circle.fromRadius(A_RADIUS_OF_TWO, Point.fromCartesian(4, 0));

        Point[] points = circle1.findIntersectionPointsWith(circle2);

        Point expectedPoint = Point.fromCartesian(2, 0);
        assertThat(points).asList().containsExactly(expectedPoint);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenTwoNonIntersectingCircles_WhenGettingIntersectionPoints_ShouldThrowAnException() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle circleFarAway = Circle.fromRadius(A_SMALLER_RADIUS, POINT_FAR_AWAY);

        circle.findIntersectionPointsWith(circleFarAway);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenTwoIdenticalCircles_WhenGettingIntersectionPoints_ShouldThrowAnException() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        circle.findIntersectionPointsWith(circle);
    }

    @Test
    public void givenACircleCenteredAtOrigin_ShouldContainsTheOrigin() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, A_CENTER);

        assertThat(circle.contains(Point.ORIGIN)).isTrue();
    }

    @Test
    public void givenAPointNearOutsideOfCircleContour_ThenTheCircleDoesNotContainThatPoint() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point nearOutsideContour = Point.fromCartesian(circle.getCenter().getX(), circle.getCenter().getY() + circle.getRadius() + 1);

        assertThat(circle.contains(nearOutsideContour)).isFalse();
    }

    @Test
    public void givenAPointNearInsideOfCircleContour_ThenTheCircleContainsThatPoint() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point nearInsideContour = Point.fromCartesian(circle.getCenter().getX(), circle.getCenter().getY() + circle.getRadius() - 1);

        assertThat(circle.contains(nearInsideContour)).isTrue();
    }

    @Test
    public void givenAPointOnContour_ThenTheCircleContainsThatPoint() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour = Point.fromCartesian(0, circle.getCenter().getY() + circle.getRadius());

        assertThat(circle.contains(pointOnContour)).isTrue();
    }

    @Test
    public void givenAPointFarAway_ThenTheCircleDoesNotContainThatPoint() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);

        assertThat(circle.contains(POINT_FAR_AWAY)).isFalse();
    }

    @Test
    public void theCenterPointIsNotOnTheContour() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        assertThat(circle.isOnContour(A_CENTER)).isFalse();
    }

    @Test
    public void theCenterPointIsInsideTheCircle() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        assertThat(circle.isPointInside(A_CENTER)).isTrue();
    }

    @Test
    public void givenAPointOnContourOfCircle_ThePointIsInsideTheCircle() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour = Point.fromCartesian(0, circle.getCenter().getY() + circle.getRadius());

        assertThat(circle.isPointInside(pointOnContour)).isFalse();
    }

    @Test
    public void givenAPointOutsideOfCircle_ThePointIsNotInsideTheCircle() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        assertThat(circle.isPointInside(POINT_FAR_AWAY)).isFalse();
    }

    @Test
    public void givenACircleInsideAnother_TheCircleShouldBeInside() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);
        Circle insideCircle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);

        assertThat(insideCircle.isInside(circle)).isTrue();
    }

    @Test
    public void givenACircleNotInsideAnother_TheOutsideCircleShouldNotBeInside() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_TWO, POINT_AT_4_0);
        Circle notInsideCircle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);

        assertThat(notInsideCircle.isInside(circle)).isFalse();
    }

    @Test
    public void givenASuperposedCircle_ItShouldBeSuperposed() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);
        Circle superposedLittleCircle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);

        assertThat(circle.isSuperposed(superposedLittleCircle)).isTrue();
    }

    @Test
    public void givenANotSuperposedCircle_ThenItIsNotSuperposed() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_TWO, POINT_AT_4_0);
        Circle notSuperposedLittleCircle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);

        assertThat(circle.isSuperposed(notSuperposedLittleCircle)).isFalse();
    }

    @Test
    public void givenAPointOnTheTour_CanRetrieveTheTangentLineOfThisPoint() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour = Point.fromCartesian(0, 1);

        Line line = circle.getTangentLineTo(pointOnContour);

        Line expectedLine = new Line(pointOnContour, Point.fromCartesian(1, 1));
        assertThat(line).isEqualTo(expectedLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAPointNotOnContour_WhenGettingTangentLine_ShouldThrowException() {
        Circle circle = Circle.fromRadius(A_RADIUS, Point.ORIGIN);

        circle.getTangentLineTo(POINT_FAR_AWAY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAnInsidePointOfCircle_WhenGettingTangentalLinesPassingByPoint_ShouldThrowAnException() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        circle.getTangentalLinesPassingBy(A_CENTER);
    }

    @Test
    public void givenAPointOnContour_CanRetrieveTangentalLinesPassingByThatPoint() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour = Point.fromCartesian(0, 1);

        Line[] lines = circle.getTangentalLinesPassingBy(pointOnContour);

        Line expectedLine = new Line(pointOnContour, Point.fromCartesian(1, 1));
        assertThat(lines).asList().containsExactly(expectedLine);
    }

    @Test
    public void givenAnExternalPoint_CanRetrieveTangentalLinesPassingByThatPoint() {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/CirclePoint/CirclePointDistance.htm
        Circle circle = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);

        Line[] lines = circle.getTangentalLinesPassingBy(POINT_AT_40_40);

        Line expectedLine1 = new Line(1.07, -2.93);
        Line expectedLine2 = new Line(0.93, 2.73);
        assertThat(lines).asList().containsExactly(expectedLine1, expectedLine2);
    }

    @Test
    public void givenAnExternalPoint_CanRetrievenTheLowerTangentalLinePassingByThatPoint() {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/CirclePoint/CirclePointDistance.htm
        Circle circle = Circle.fromRadius(3, Point.fromCartesian(2, -5));
        Point externalPoint = Point.fromCartesian(7, 1);

        Line actualLine = circle.getLowerTangentalLinePassingBy(externalPoint);

        Line expectedLine = new Line(3.23, -21.59);
        assertThat(actualLine).isEqualTo(expectedLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAPointInside_WhenGettingLowerTangentalLinePassingByPoint_ShouldThrowAnException() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        circle.getLowerTangentalLinePassingBy(A_CENTER);
    }

    @Test
    public void givenAPointOnContour_CanRetrieveTheLowerTangentalLinePassingByThatPoint() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour = Point.fromCartesian(0, 1);

        Line line = circle.getLowerTangentalLinePassingBy(pointOnContour);

        Line expectedLine = new Line(pointOnContour, Point.fromCartesian(1, 1));
        assertThat(line).isEqualTo(expectedLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAPointInside_WhenGettingTangentalPointsOfLinePassingByPoint_ShouldThrowAnException() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        circle.findTangentPointsOfLinePassingBy(A_CENTER);
    }

    @Test
    public void givenAPointOnContour_CanRetrieveTheTangentalPointsOfLinePassingByThatPoint() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour = Point.fromCartesian(0, 1);

        Point[] points = circle.findTangentPointsOfLinePassingBy(pointOnContour);

        assertThat(points).asList().containsExactly(pointOnContour);
    }

    @Test
    public void givenAnExternalPoint_CanRetrieveTheTangentalPointsOfLinePassingByThatPoint() {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/CirclePoint/CirclePointDistance.htm
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);

        Point[] points = circle.findTangentPointsOfLinePassingBy(POINT_AT_4_0);

        Point expectedPoint1 = Point.fromCartesian(0.25, 0.97);
        Point expectedPoint2 = Point.fromCartesian(0.25, -0.97);
        assertThat(points).asList().containsExactly(expectedPoint1, expectedPoint2);
    }

    @Test
    public void givenTwoNonIntersectingCirclesOfSameSizes_CanRetrieveTheLowerTangentalLineWithCircle() {
        // http://www.ambrsoft.com/TrigoCalc/Circles2/Circles2Tangent.htm
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Circle otherCircle = Circle.fromRadius(A_RADIUS_OF_ONE, POINT_AT_4_0);

        Line actualLine = circle.getLowerTangentalLineWith(otherCircle);

        Line expectedLine = new Line(0, -1);
        assertThat(actualLine).isEqualTo(expectedLine);
    }

    @Test
    public void givenTwoNonIntersectingCirclesOfDifferentSizes_CanRetrieveTheLowerTangentalLineWithCircle() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Circle otherCircle = Circle.fromRadius(A_RADIUS_OF_TWO, POINT_AT_4_0);

        Line actualLine = circle.getLowerTangentalLineWith(otherCircle);

        Line expectedLine = new Line(-0.26, -1.03);
        assertThat(actualLine).isEqualTo(expectedLine);
    }

    @Test
    public void givenTwoIntersectingCirclesOfSameSizes_CanRetrieveTheLowerTangentalLineWithCircle() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);
        Circle otherCircle = Circle.fromRadius(A_RADIUS_OF_TWO, POINT_AT_2_0);

        Line actualLine = circle.getLowerTangentalLineWith(otherCircle);

        Line expectedLine = new Line(0, -2);
        assertThat(actualLine).isEqualTo(expectedLine);
    }

    @Test
    public void givenTwoIntersectingCirclesOfDifferentSizes_CanRetrieveTheLowerTangentalLineWithCircle() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Circle otherCircle = Circle.fromRadius(A_RADIUS_OF_TWO, POINT_AT_2_0);

        Line actualLine = circle.getLowerTangentalLineWith(otherCircle);

        Line expectedLine = new Line(-0.58, -1.15);
        assertThat(actualLine).isEqualTo(expectedLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenTwoSuperposedCircles_WhenGettingLowerTangentalLineWithCircle_ShouldThrowException() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Circle otherCircle = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);

        circle.getLowerTangentalLineWith(otherCircle);
    }

    @Test
    public void canIncreaseTheRadiusOfACircle() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        Circle increasedCircle = circle.increaseRadiusBy(5);

        assertThat(increasedCircle.getRadius()).isWithin(DELTA).of(A_RADIUS + 5);
    }

    @Test
    public void canDecreaseTheRadiusOfACircle() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        Circle decreasedCircle = circle.decreaseRadiusBy(2);

        assertThat(decreasedCircle.getRadius()).isWithin(DELTA).of(A_RADIUS - 2);
    }

    @Test
    public void givenTwoCirclesIdentical_ShouldBeTangent() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        assertThat(circle.isTangentTo(circle)).isTrue();
    }

    @Test
    public void givenTheSameCenterButWithASmallerSize_ShouldNotBeTangent() {
        Circle circle = Circle.fromRadius(50, A_CENTER);
        Circle smallerCircle = Circle.fromRadius(15, A_CENTER);

        assertThat(circle.isTangentTo(smallerCircle)).isFalse();
    }

    @Test
    public void givenACircleFarAway_ShouldNotBeTangent() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle circleFarAway = Circle.fromRadius(A_RADIUS, Point.fromCartesian(1000.0, 1000.0));

        assertThat(circle.isTangentTo(circleFarAway)).isFalse();
    }

    @Test
    public void givenAnIntersectingCircle_ShouldNotBeTangent() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle circleFarAway = Circle.fromRadius(50, Point.fromCartesian(25, 25));

        assertThat(circle.isTangentTo(circleFarAway)).isFalse();
    }

    @Test
    public void givenACircleExternallyTangentInUpperRightCorner_ShouldBeTangent() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle circleTangent = Circle.fromRadius(50, Point.fromCartesian(70.71067811865474, 70.71067811865474));

        assertThat(circle.isTangentTo(circleTangent)).isTrue();
    }

    @Test
    public void givenACircleInternallyTangentInUpperRightCorner_ShouldBeTangent() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle circleTangent = Circle.fromRadius(15, Point.fromCartesian(24.74873734152916, 24.74873734152916));

        assertThat(circle.isTangentTo(circleTangent)).isTrue();
    }

    @Test
    public void givenACircleExternallyTangentAtLeft_ShouldBeTangent() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle circleTangent = Circle.fromRadius(15, Point.fromCartesian(-65.0, 0.0));

        assertThat(circle.isTangentTo(circleTangent)).isTrue();
    }

    @Test
    public void givenACircleInternallyTangentAtLeft_ShouldBeTangent() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);
        Circle circleTangent = Circle.fromRadius(15, Point.fromCartesian(-35.0, 0.0));

        assertThat(circle.isTangentTo(circleTangent)).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenAPointInsideAndAPointOutside_WhenGettingTangentalCirclesPassingByPoints_ShouldThrowAnException() {
        Circle circle = Circle.fromRadius(A_RADIUS, A_CENTER);

        circle.getTangentalCirclesPassingBy(A_CENTER, POINT_FAR_AWAY);
    }

    @Test
    public void givenTwoPointsOnContour_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour1 = Point.fromCartesian(-1, 0);
        Point pointOnContour2 = Point.fromCartesian(0, 1);

        Circle[] circles = circle.getTangentalCirclesPassingBy(pointOnContour1, pointOnContour2);

        assertThat(circles).asList().containsExactly(circle);
    }

    @Test
    public void givenAPointOnContourAndAnExternalPoint_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour = Point.fromCartesian(0, 1);
        Point anExternalPoint = Point.fromCartesian(0, 3);

        Circle[] circles = circle.getTangentalCirclesPassingBy(pointOnContour, anExternalPoint);

        Circle expectedCircle = Circle.fromCenterAndAPoint(pointOnContour, Point.fromCartesian(0, 2));
        assertThat(circles).asList().containsExactly(expectedCircle);
    }

    @Test
    public void givenAPointOnContourAndAnInternalPoint_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point pointOnContour = Point.fromCartesian(0, 1);
        Point anInternalPoint = Point.fromCartesian(0, 0.5);

        Circle[] circles = circle.getTangentalCirclesPassingBy(pointOnContour, anInternalPoint);

        Circle expectedCircle = Circle.fromCenterAndAPoint(pointOnContour, Point.fromCartesian(0, 0.75));
        assertThat(circles).asList().containsExactly(expectedCircle);
    }

    @Test
    public void givenTwoExternalPointsOnEachSideOfCircleAlignWithCenter_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point externalPoint1 = Point.fromCartesian(-2, 0);
        Point externalPoint2 = Point.fromCartesian(2, 0);

        Circle[] circles = circle.getTangentalCirclesPassingBy(externalPoint1, externalPoint2);

        Circle expectedCircle1 = Circle.fromThreePoints(externalPoint1, externalPoint2, Point.fromCartesian(0, -1));
        Circle expectedCircle2 = Circle.fromThreePoints(externalPoint1, externalPoint2, Point.fromCartesian(0, 1));
        assertThat(circles).asList().containsExactly(expectedCircle1, expectedCircle2);
    }

    @Test
    public void givenTwoExternalPointOnEachSideOfCircle_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point externalPoint1 = Point.fromCartesian(-2, 1);
        Point externalPoint2 = Point.fromCartesian(4, -3);

        Circle[] circles = circle.getTangentalCirclesPassingBy(externalPoint1, externalPoint2);

        Circle expectedCircle1 = Circle.fromThreePoints(externalPoint1, externalPoint2, Point.fromCartesian(0.23906, 0.97101));
        Circle expectedCircle2 = Circle.fromThreePoints(externalPoint1, externalPoint2, Point.fromCartesian(-0.7063, -0.70791));
        assertThat(circles).asList().containsExactly(expectedCircle1, expectedCircle2);
    }

    @Test
    public void givenTwoExternalPointsOnTheSameSideAlignWithCenter_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point externalPoint1 = Point.fromCartesian(2, 0);
        Point externalPoint2 = Point.fromCartesian(3, 0);

        Circle[] circles = circle.getTangentalCirclesPassingBy(externalPoint1, externalPoint2);

        Circle expectedCircle1 = Circle.fromThreePoints(externalPoint1, externalPoint2, Point.fromCartesian(0.707, 0.707));
        Circle expectedCircle2 = Circle.fromThreePoints(externalPoint1, externalPoint2, Point.fromCartesian(0.707, -0.707));
        assertThat(circles).asList().containsExactly(expectedCircle1, expectedCircle2);
    }

    @Test
    public void givenTwoExternalPointsOnTheSameSide_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_ONE, Point.ORIGIN);
        Point externalPoint1 = Point.fromCartesian(2, 4);
        Point externalPoint2 = Point.fromCartesian(5, -3);

        Circle[] circles = circle.getTangentalCirclesPassingBy(externalPoint1, externalPoint2);

        Circle expectedCircle1 = Circle.fromThreePoints(externalPoint1, externalPoint2, Point.fromCartesian(0.97, 0.22));
        Circle expectedCircle2 = Circle.fromThreePoints(externalPoint1, externalPoint2, Point.fromCartesian(-1, -0.08));
        assertThat(circles).asList().containsExactly(expectedCircle1, expectedCircle2);
    }

    @Test
    public void givenTwoInternalPointsOnEachSideOfCenterAndAlignWithIt_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);
        Point internalPoint1 = Point.fromCartesian(1, 0);
        Point internalPoint2 = Point.fromCartesian(-1, 0);

        Circle[] circles = circle.getTangentalCirclesPassingBy(internalPoint1, internalPoint2);

        Circle expectedCircle1 = Circle.fromThreePoints(internalPoint1, internalPoint2, Point.fromCartesian(0, 2));
        Circle expectedCircle2 = Circle.fromThreePoints(internalPoint1, internalPoint2, Point.fromCartesian(0, -2));
        assertThat(circles).asList().containsExactly(expectedCircle1, expectedCircle2);
    }

    @Test
    public void givenTwoInternalPointsOnEachSideOfCenter_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_TWO, Point.ORIGIN);
        Point internalPoint1 = Point.fromCartesian(1.75, 0.5);
        Point internalPoint2 = Point.fromCartesian(-0.66, -1);

        Circle[] circles = circle.getTangentalCirclesPassingBy(internalPoint1, internalPoint2);

        Circle expectedCircle1 = Circle.fromThreePoints(internalPoint1, internalPoint2, Point.fromCartesian(1.84, -0.77));
        Circle expectedCircle2 = Circle.fromThreePoints(internalPoint1, internalPoint2, Point.fromCartesian(0.81, 1.83));
        assertThat(circles).asList().containsExactly(expectedCircle1, expectedCircle2);
    }

    @Test
    public void givenTwoInternalPointsOnTheSameSideRegardingCenterAndAlignedWithIt_CanRetrieveTheTangentalCirclesPassingByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_THREE, Point.ORIGIN);
        Point internalPoint1 = Point.fromCartesian(1, 0);
        Point internalPoint2 = Point.fromCartesian(2, 0);

        Circle[] circles = circle.getTangentalCirclesPassingBy(internalPoint1, internalPoint2);

        Circle expectedCircle1 = Circle.fromThreePoints(internalPoint1, internalPoint2, Point.fromCartesian(2.45, 1.74));
        Circle expectedCircle2 = Circle.fromThreePoints(internalPoint1, internalPoint2, Point.fromCartesian(2.45, -1.74));
        assertThat(circles).asList().containsExactly(expectedCircle1, expectedCircle2);
    }

    @Test
    public void givenTwoInternalPointsOnTheSameSideRegardingCenter_CanRetrieveTheTangentalCirclesPassingByByThesePoints() {
        Circle circle = Circle.fromRadius(A_RADIUS_OF_THREE, Point.ORIGIN);
        Point internalPoint1 = Point.fromCartesian(1.55, 2);
        Point internalPoint2 = Point.fromCartesian(2.24, -1.36);

        Circle[] circles = circle.getTangentalCirclesPassingBy(internalPoint1, internalPoint2);

        Circle expectedCircle1 = Circle.fromThreePoints(internalPoint1, internalPoint2, Point.fromCartesian(-2.66, -1.39));
        Circle expectedCircle2 = Circle.fromThreePoints(internalPoint1, internalPoint2, Point.fromCartesian(2.97, 0.42));
        assertThat(circles).asList().containsExactly(expectedCircle1, expectedCircle2);
    }
}
