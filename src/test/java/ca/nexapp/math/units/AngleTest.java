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
    public void given359Degrees_ShouldNotBeLocatedInFirstQuadrantOrAxes() {
	Angle angle359 = Angle.fromDegrees(359.99);

	assertThat(angle359.isInFirstQuadrantOrAxes()).isFalse();
    }

    @Test
    public void givenZeroDegrees_ShouldBeLocatedInFirstQuadrantOrAxes() {
	assertThat(Angle.ZERO_DEGREES.isInFirstQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given45Degrees_ShouldBeLocatedInFirstQuadrantOrAxes() {
	Angle angle45 = Angle.fromDegrees(45.00);

	assertThat(angle45.isInFirstQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given90Degrees_ShouldBeLocatedInFirstQuadrantOrAxes() {
	assertThat(Angle.DEGREES_90.isInFirstQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given91Degrees_ShouldNotLocatedInFirstQuadrantOrAxes() {
	Angle angle91 = Angle.fromDegrees(91.00);

	assertThat(angle91.isInFirstQuadrantOrAxes()).isFalse();
    }

    @Test
    public void given89Degrees_ShouldNotBeLocatedInSecondQuadrantOrAxes() {
	Angle angle89 = Angle.fromDegrees(89.00);

	assertThat(angle89.isInSecondQuadrantOrAxes()).isFalse();
    }

    @Test
    public void given90Degrees_ShouldBeLocatedInSecondQuadrantOrAxes() {
	assertThat(Angle.DEGREES_90.isInSecondQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given125Degrees_ShouldBeLocatedInSecondQuadrantOrAxes() {
	Angle angle125 = Angle.fromDegrees(125.00);

	assertThat(angle125.isInSecondQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given180Degrees_ShouldBeLocatedInSecondQuadrantOrAxes() {
	assertThat(Angle.DEGREES_180.isInSecondQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given181Degrees_ShouldNotBeLocatedInSecondQuadrantOrAxes() {
	Angle angle181 = Angle.fromDegrees(181.00);

	assertThat(angle181.isInSecondQuadrantOrAxes()).isFalse();
    }

    @Test
    public void given179Degrees_ShouldNotBeLocatedInThirdQuadrantOrAxes() {
	Angle angle179 = Angle.fromDegrees(179.00);

	assertThat(angle179.isInThirdQuadrantOrAxes()).isFalse();
    }

    @Test
    public void given180Degrees_ShouldBeLocatedInThirdQuadrantOrAxes() {
	assertThat(Angle.DEGREES_180.isInThirdQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given250Degrees_ShouldBeLocatedInThirdQuadrantOrAxes() {
	Angle angle250 = Angle.fromDegrees(250.00);

	assertThat(angle250.isInThirdQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given270Degrees_ShouldBeLocatedInThirdQuadrantOrAxes() {
	assertThat(Angle.DEGREES_270.isInThirdQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given271Degrees_ShouldNotBeLocatedInThirdQuadrantOrAxes() {
	Angle angle271 = Angle.fromDegrees(271.00);

	assertThat(angle271.isInThirdQuadrantOrAxes()).isFalse();
    }

    @Test
    public void given269Degrees_ShouldNotBeLocatedInFourthQuadrantOrAxes() {
	Angle angle269 = Angle.fromDegrees(269.00);

	assertThat(angle269.isInFourthQuadrantOrAxes()).isFalse();
    }

    @Test
    public void given270Degrees_ShouldBeLocatedInFourthQuadrant() {
	assertThat(Angle.DEGREES_270.isInFourthQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given300Degrees_ShouldBeLocatedInFourthQuadrantOrAxes() {
	Angle angle300 = Angle.fromDegrees(300.00);

	assertThat(angle300.isInFourthQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given360Degrees_ShouldBeLocatedInFourthQuadrantOrAxes() {
	assertThat(Angle.DEGREES_360.isInFourthQuadrantOrAxes()).isTrue();
    }

    @Test
    public void givenZeroDegrees_ShouldBeLocatedInFourthQuadrantOrAxes() {
	assertThat(Angle.ZERO_DEGREES.isInFourthQuadrantOrAxes()).isTrue();
    }

    @Test
    public void given1Degrees_ShouldNotBeLocatedInFourthQuadrantOrAxes() {
	Angle angle1 = Angle.fromDegrees(1.00);

	assertThat(angle1.isInFourthQuadrantOrAxes()).isFalse();
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
