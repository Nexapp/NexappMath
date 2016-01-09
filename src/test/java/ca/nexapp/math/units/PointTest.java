package ca.nexapp.math.units;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PointTest {

    private static final double X = 500.5;
    private static final double Y = 100.0;

    private static final double DISTANCE = 400.0;
    private static final Angle ANGLE = Angle.DEGREES_270;

    private static final double TOLERANCE = 0.0001;

    @Test
    public void givenX_ShouldRetrieveTheSameX() {
        assertThat(Point.fromCartesian(X, Y).getX()).isWithin(TOLERANCE).of(X);
    }

    @Test
    public void givenY_ShouldRetrieveTheSameY() {
        assertThat(Point.fromCartesian(X, Y).getY()).isWithin(TOLERANCE).of(Y);
    }

    @Test
    public void givenAPolarDistance_ShouldRetrieveTheSamePolarDistance() {
        assertThat(Point.fromPolar(ANGLE, DISTANCE).getPolarDistance()).isWithin(TOLERANCE).of(DISTANCE);
    }

    @Test
    public void givenAPolarAngle_ShouldRetrieveTheSamePolarAngle() {
        assertThat(Point.fromPolar(ANGLE, DISTANCE).getPolarAngle()).isEqualTo(ANGLE);
    }

    @Test
    public void givenCartesianPointInFirstQuadrant_ShouldReturnTheEquivalentPolarPoint() {
        Point cartesianPoint = Point.fromCartesian(1.0, 1.0);
        Point polarPoint = Point.fromPolar(Angle.fromDegrees(45), 1.4142);

        assertThat(cartesianPoint).isEqualTo(polarPoint);
    }

    @Test
    public void givenCartesianPointInSecondQuadrant_ShouldReturnTheEquivalentPolarPoint() {
        Point cartesianPoint = Point.fromCartesian(-3.0, 10.0);
        Point polarPoint = Point.fromPolar(Angle.fromDegrees(106.70), 10.4403);

        assertThat(cartesianPoint).isEqualTo(polarPoint);
    }

    @Test
    public void givenCartesianPointInThirdQuadrant_ShouldReturnTheEquivalentPolarPoint() {
        Point cartesianPoint = Point.fromCartesian(-10.0, -15.0);
        Point polarPoint = Point.fromPolar(Angle.fromDegrees(236.3099), 18.0277);

        assertThat(cartesianPoint).isEqualTo(polarPoint);
    }

    @Test
    public void givenCartesianPointInFourthQuadrant_ShouldReturnTheEquivalentPolarPoint() {
        Point cartesianPoint = Point.fromCartesian(5.0, -8.0);
        Point polarPoint = Point.fromPolar(Angle.fromDegrees(302.005), 9.43398);

        assertThat(cartesianPoint).isEqualTo(polarPoint);
    }

    @Test
    public void givenOrigin_ShouldReturnAPolarAngleOfZero() {
        assertThat(Point.ORIGIN.getPolarAngle()).isEqualTo(Angle.ZERO_DEGREES);
    }

    @Test
    public void givenCartesianPointAt1_1_ShouldReturnAPolarAngleOf45Degrees() {
        assertThat(Point.fromCartesian(1.0, 1.0).getPolarAngle()).isEqualTo(Angle.fromDegrees(45.00));
    }

    @Test
    public void givenCartesianPointAt1_Minus1_ShouldReturnAPolarAngleOf315Degrees() {
        assertThat(Point.fromCartesian(1.0, -1.0).getPolarAngle()).isEqualTo(Angle.fromDegrees(315.00));
    }

    @Test
    public void givenCartesianPointAtMinus5_0_ShouldReturnAPolarAngleOf180Degrees() {
        assertThat(Point.fromCartesian(-5.0, 0).getPolarAngle()).isEqualTo(Angle.DEGREES_180);
    }

    @Test
    public void givenCartesianPointAt0_6_ShouldReturnAPolarAngleOf90Degrees() {
        assertThat(Point.fromCartesian(0, 6.0).getPolarAngle()).isEqualTo(Angle.DEGREES_90);
    }

    @Test
    public void givenCartesianPointAt0_Minus7_ShouldReturnAPolarAngleOf270Degrees() {
        assertThat(Point.fromCartesian(0, -7.0).getPolarAngle()).isEqualTo(Angle.DEGREES_270);
    }

    @Test
    public void canFindTheDistanceBetweenTwoPoints() {
        double actualDistance = Point.ORIGIN.getDistanceTo(Point.fromCartesian(1, 1));

        assertThat(actualDistance).isWithin(TOLERANCE).of(1.4142);
    }

    @Test
    public void givenSameX_ShouldNotBeAtLeftOfTheOtherPoint() {
        assertThat(Point.ORIGIN.isAtLeftOf(Point.ORIGIN)).isFalse();
    }

    @Test
    public void givenSameX_ShouldNotBeAtRightOfTheOtherPoint() {
        assertThat(Point.ORIGIN.isAtRightOf(Point.ORIGIN)).isFalse();
    }

    @Test
    public void givenXAtLeft_ThenOtherPointShouldBeToTheLeft() {
        Point otherPoint = Point.fromCartesian(1000, 0);

        assertThat(Point.ORIGIN.isAtLeftOf(otherPoint)).isTrue();
    }

    @Test
    public void givenXAtRight_ThenOtherPointShouldNotBeToTheLeft() {
        Point otherPoint = Point.fromCartesian(1000, 0);

        assertThat(otherPoint.isAtLeftOf(Point.ORIGIN)).isFalse();
    }

    @Test
    public void givenXAtRight_ThenOtherPointShouldBeToTheRight() {
        Point otherPoint = Point.fromCartesian(1000, 0);

        assertThat(otherPoint.isAtRightOf(Point.ORIGIN)).isTrue();
    }

    @Test
    public void givenXAtLeft_ThenOtherPointShouldNotBeToTheRight() {
        Point otherPoint = Point.fromCartesian(1000, 0);

        assertThat(Point.ORIGIN.isAtRightOf(otherPoint)).isFalse();
    }

    @Test
    public void givenSameY_ShouldNotBeAboveOfTheOtherPoint() {
        assertThat(Point.ORIGIN.isAboveOf(Point.ORIGIN)).isFalse();
    }

    @Test
    public void givenSameY_ShouldNotBeBelowOfTheOtherPoint() {
        assertThat(Point.ORIGIN.isBelowOf(Point.ORIGIN)).isFalse();
    }

    @Test
    public void givenAboveY_ThenOtherPointShouldBeAbove() {
        Point otherPoint = Point.fromCartesian(0, 1000);

        assertThat(otherPoint.isAboveOf(Point.ORIGIN)).isTrue();
    }

    @Test
    public void givenBelowY_ShouldNotBeAboveOfTheOtherPoint() {
        Point otherPoint = Point.fromCartesian(0, 1000);

        assertThat(Point.ORIGIN.isAboveOf(otherPoint)).isFalse();
    }

    @Test
    public void givenBelowY_ThenOtherPointShouldBeBelow() {
        Point otherPoint = Point.fromCartesian(0, 1000);

        assertThat(Point.ORIGIN.isBelowOf(otherPoint)).isTrue();
    }

    @Test
    public void givenAboveY_ThenOtherPointShouldNotBeBelow() {
        Point otherPoint = Point.fromCartesian(0, 1000);

        assertThat(otherPoint.isBelowOf(Point.ORIGIN)).isFalse();
    }

    @Test
    public void canPerformAClockwiseRotation() {
        Point point = Point.fromCartesian(2, 4);
        Point pivot = Point.fromCartesian(1, 2);

        Point rotatedPoint = point.rotateClockwise(pivot, Angle.DEGREES_90);

        assertThat(rotatedPoint).isEqualTo(Point.fromCartesian(3, 1));
    }

    @Test
    public void canPerformACounterClockwiseRotation() {
        Point point = Point.fromCartesian(2, 0);
        Point pivot = Point.fromCartesian(2, -2);

        Point rotatedPoint = point.rotateCounterClockwise(pivot, Angle.DEGREES_90);

        assertThat(rotatedPoint).isEqualTo(Point.fromCartesian(0, -2));
    }
}
