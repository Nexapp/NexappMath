package ca.nexapp.math.units;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class AngleTest {

    private static final double AN_ANGLE_IN_DEGREES = 90;
    private static final double THE_ANGLE_IN_RADIANS = Math.toRadians(AN_ANGLE_IN_DEGREES);

    private static final double A_NEGATIVE_ANGLE_IN_DEGREES = -90;

    private static final double DELTA = 0.01;

    @Test
    public void canAddTwoAngles() {
        Angle sevenDegrees = Angle.fromDegrees(7);
        Angle twentyDegrees = Angle.fromDegrees(20);

        Angle result = sevenDegrees.add(twentyDegrees);

        assertThat(result).isEqualTo(Angle.fromDegrees(27));
    }

    @Test
    public void canSubtractTwoAngles() {
        Angle twoRadians = Angle.fromRadians(2);
        Angle oneRadians = Angle.fromRadians(1);

        Angle result = twoRadians.subtract(oneRadians);

        assertThat(result).isEqualTo(Angle.fromRadians(1));
    }

    @Test
    public void canConvertAnAngleInDegreesToRadians() {
        Angle angle = Angle.fromDegrees(AN_ANGLE_IN_DEGREES);

        assertThat(angle.toRadians()).isWithin(DELTA).of(THE_ANGLE_IN_RADIANS);
    }

    @Test
    public void convertingAnAngleInDegreesToDegrees_ShouldReturnTheSameAngle() {
        Angle angle = Angle.fromDegrees(AN_ANGLE_IN_DEGREES);

        assertThat(angle.toDegrees()).isWithin(DELTA).of(AN_ANGLE_IN_DEGREES);
    }

    @Test
    public void canConvertAnAngleInRadiansToDegrees() {
        Angle angle = Angle.fromRadians(THE_ANGLE_IN_RADIANS);

        assertThat(angle.toDegrees()).isWithin(DELTA).of(AN_ANGLE_IN_DEGREES);
    }

    @Test
    public void convertingAnAngleInRadiansToRadians_ShouldReturnTheSameAngle() {
        Angle angle = Angle.fromRadians(THE_ANGLE_IN_RADIANS);

        assertThat(angle.toRadians()).isWithin(DELTA).of(THE_ANGLE_IN_RADIANS);
    }

    @Test
    public void whenInverting_ShouldReturnTheOpposite() {
        Angle angle = Angle.fromRadians(THE_ANGLE_IN_RADIANS);

        Angle opposite = angle.invert();

        Angle expectedOpposite = Angle.fromRadians(-THE_ANGLE_IN_RADIANS);
        assertThat(opposite).isEqualTo(expectedOpposite);
    }

    @Test
    public void givenAPositiveAngleLowerThan360Degrees_WhenNormalizing_ShouldReturnTheSameAngle() {
        Angle angle = Angle.fromDegrees(AN_ANGLE_IN_DEGREES);

        assertThat(angle.normalize()).isEqualTo(angle);
    }

    @Test
    public void givenANegativeAngleInDegrees_WhenNormalizing_ShouldReturnTheAdditionOf360WithThatAngle() {
        Angle angle = Angle.fromDegrees(A_NEGATIVE_ANGLE_IN_DEGREES);

        Angle normalizedAngle = angle.normalize();

        Angle expectedAngle = Angle.fromDegrees(360 + A_NEGATIVE_ANGLE_IN_DEGREES);
        assertThat(normalizedAngle).isEqualTo(expectedAngle);
    }

    @Test
    public void givenA360DegreesAngle_WhenNormalizing_ShouldReturnZeroDegrees() {
        assertThat(Angle.DEGREES_360.normalize()).isEqualTo(Angle.ZERO_DEGREES);
    }

    @Test
    public void givenA360Plus180DegreesAngle_WhenNormalizing_ShouldReturn180Degrees() {
        Angle angle = Angle.fromDegrees(360 + 180);

        assertThat(angle.normalize()).isEqualTo(Angle.DEGREES_180);
    }

    @Test
    public void givenA720Plus90DegreesAngle_WhenNormalizing_ShouldReturn90Degrees() {
        Angle angle = Angle.fromDegrees(720 + 90);

        assertThat(angle.normalize()).isEqualTo(Angle.DEGREES_90);
    }

    @Test
    public void givenZeroDegrees_ShouldNotBeLocatedInFirstQuadrant() {
        assertThat(Angle.ZERO_DEGREES.isInFirstQuadrant()).isFalse();
    }

    @Test
    public void given45Degrees_ShouldBeLocatedInFirstQuadrant() {
        Angle angle45 = Angle.fromDegrees(45.00);

        assertThat(angle45.isInFirstQuadrant()).isTrue();
    }

    @Test
    public void given90Degrees_ShouldNotBeLocatedInFirstQuadrant() {
        assertThat(Angle.DEGREES_90.isInFirstQuadrant()).isFalse();
    }

    @Test
    public void given90Degrees_ShouldNotBeLocatedInSecondQuadrant() {
        assertThat(Angle.DEGREES_90.isInSecondQuadrant()).isFalse();
    }

    @Test
    public void given125Degrees_ShouldBeLocatedInSecondQuadrant() {
        Angle angle125 = Angle.fromDegrees(125.00);

        assertThat(angle125.isInSecondQuadrant()).isTrue();
    }

    @Test
    public void given180Degrees_ShouldNotBeLocatedInSecondQuadrant() {
        assertThat(Angle.DEGREES_180.isInSecondQuadrant()).isFalse();
    }

    @Test
    public void given180Degrees_ShouldNotBeLocatedInThirdQuadrant() {
        assertThat(Angle.DEGREES_180.isInThirdQuadrant()).isFalse();
    }

    @Test
    public void given250Degrees_ShouldBeLocatedInThirdQuadrant() {
        Angle angle250 = Angle.fromDegrees(250.00);

        assertThat(angle250.isInThirdQuadrant()).isTrue();
    }

    @Test
    public void given270Degrees_ShouldNotBeLocatedInThirdQuadrant() {
        assertThat(Angle.DEGREES_270.isInThirdQuadrant()).isFalse();
    }

    @Test
    public void given270Degrees_ShouldNotBeLocatedInFourthQuadrant() {
        assertThat(Angle.DEGREES_270.isInFourthQuadrant()).isFalse();
    }

    @Test
    public void given300Degrees_ShouldBeLocatedInFourthQuadrant() {
        Angle angle300 = Angle.fromDegrees(300.00);

        assertThat(angle300.isInFourthQuadrant()).isTrue();
    }

    @Test
    public void given360Degrees_ShouldNotBeLocatedInFourthQuadrant() {
        assertThat(Angle.DEGREES_360.isInFourthQuadrant()).isFalse();
    }

    @Test
    public void given360Degrees_ShouldNotBeLocatedInFirstQuadrant() {
        assertThat(Angle.DEGREES_360.isInFirstQuadrant()).isFalse();
    }

    @Test
    public void givenZeroDegrees_ShouldBeOnXAxis() {
        assertThat(Angle.ZERO_DEGREES.isOnXAxis()).isTrue();
    }

    @Test
    public void given180Degrees_ShouldBeOnXAxis() {
        assertThat(Angle.DEGREES_180.isOnXAxis()).isTrue();
    }

    @Test
    public void givenANormalizedZeroDegrees_ShouldBeOnXAxis() {
        Angle angle = Angle.fromDegrees(0 + 720);

        assertThat(angle.isOnXAxis()).isTrue();
    }

    @Test
    public void givenANormalized180Degrees_ShouldBeOnXAxis() {
        Angle angle = Angle.fromDegrees(180 + 720);

        assertThat(angle.isOnXAxis()).isTrue();
    }

    @Test
    public void given360Degrees_ShouldBeOnXAxis() {
        assertThat(Angle.DEGREES_360.isOnXAxis()).isTrue();
    }

    @Test
    public void givenAnAngleInAQuadrant_ShouldNotBeOnXAxis() {
        Angle angle = Angle.fromDegrees(45);

        assertThat(angle.isOnXAxis()).isFalse();
    }

    @Test
    public void given90Degrees_ShouldBeOnYAxis() {
        assertThat(Angle.DEGREES_90.isOnYAxis()).isTrue();
    }

    @Test
    public void given270Degrees_ShouldBeOnYAxis() {
        assertThat(Angle.DEGREES_270.isOnYAxis()).isTrue();
    }

    @Test
    public void givenANormalized90Degrees_ShouldBeOnYAxis() {
        Angle angle = Angle.fromDegrees(90 + 720);

        assertThat(angle.isOnYAxis()).isTrue();
    }

    @Test
    public void givenANormalized270Degrees_ShouldBeOnYAxis() {
        Angle angle = Angle.fromDegrees(270 + 720);

        assertThat(angle.isOnYAxis()).isTrue();
    }

    @Test
    public void givenAnAngleInAQuadrant_ShouldNotBeOnYAxis() {
        Angle angle = Angle.fromDegrees(290);

        assertThat(angle.isOnYAxis()).isFalse();
    }

    @Test
    public void aSmallAngle_ShouldBeLesser() {
        Angle smallAngle = Angle.fromDegrees(17);
        Angle highAngle = Angle.fromDegrees(276);

        assertThat(smallAngle).isLessThan(highAngle);
    }

    @Test
    public void theSameAngle_ShouldBeEqualsComparatively() {
        Angle angle = Angle.fromDegrees(56);

        assertThat(angle).isEquivalentAccordingToCompareTo(angle);
    }

    @Test
    public void aHighAngle_ShouldBeGreater() {
        Angle smallAngle = Angle.fromDegrees(17);
        Angle highAngle = Angle.fromDegrees(276);

        assertThat(highAngle).isGreaterThan(smallAngle);
    }
}
