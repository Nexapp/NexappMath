package ca.nexapp.math.functions;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import ca.nexapp.math.units.Angle;
import ca.nexapp.math.units.Point;

public class LineTest {

    private static final double A_SLOPE = 1.0;
    private static final double AN_INTERCEPT = 5.0;

    private static final double TOLERANCE = 0.00001;

    @Test
    public void canCreateALineWithTheSlopeAndIntercept() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        assertThat(line.getSlope()).isWithin(TOLERANCE).of(A_SLOPE);
        assertThat(line.getIntercept()).isWithin(TOLERANCE).of(AN_INTERCEPT);
    }

    @Test
    public void canCreateALineWithTwoPoints() {
        Line line = new Line(Point.ORIGIN, Point.fromCartesian(1, 1));

        assertThat(line.getSlope()).isWithin(TOLERANCE).of(1);
        assertThat(line.getIntercept()).isWithin(TOLERANCE).of(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateALineWithTheSameTwoPoints() {
        new Line(Point.ORIGIN, Point.ORIGIN);
    }

    @Test
    public void canCreateALineWithTheSlopeAndAPoint() {
        Line line = new Line(2.0, Point.ORIGIN);

        assertThat(line.getSlope()).isWithin(TOLERANCE).of(2);
        assertThat(line.getIntercept()).isWithin(TOLERANCE).of(0);
    }

    @Test
    public void canCreateALineWithAnAngleAndAPoint() {
        Line line = new Line(Angle.fromDegrees(45.00), Point.fromCartesian(0, 5));

        assertThat(line.getSlope()).isWithin(TOLERANCE).of(1);
        assertThat(line.getIntercept()).isWithin(TOLERANCE).of(5);
    }

    @Test
    public void givenAParallelLineOfTheXAxis_ShouldFindNoRealRoot() {
        Line parallelLine = Line.X_AXIS.findParallelLinePassingThrough(Point.fromCartesian(50, 50));

        double[] roots = parallelLine.findRealRoots();

        assertThat(roots).isEmpty();
    }

    @Test
    public void givenTheXAxis_ShouldFindNoRealRoot() {
        double[] roots = Line.X_AXIS.findRealRoots();

        assertThat(roots).isEmpty();
    }

    @Test
    public void givenASlope_ShouldFindTheSingleRealRoot() {
        Line line = new Line(1, 0);

        double[] roots = line.findRealRoots();

        double[] expected = { 0 };
        assertThat(roots).hasValuesWithin(TOLERANCE).of(expected);
    }

    @Test
    public void givenAHorizontalLine_ShouldHaveASlopeOfZero() {
        assertThat(Line.X_AXIS.getSlope()).isWithin(TOLERANCE).of(0);
    }

    @Test
    public void givenAVerticalLine_ShouldHaveAnInfiniteSlope() {
        double slope = Line.Y_AXIS.getSlope();

        assertThat(Double.isInfinite(slope)).isTrue();
    }

    @Test
    public void givenASlopeHigherThanZero_ShouldHaveAPositiveSlope() {
        Line line = new Line(5.0, Point.ORIGIN);

        assertThat(line.hasAPositiveSlope()).isTrue();
    }

    @Test
    public void givenASlopeOfZero_ShouldHaveAPositiveSlope() {
        Line line = new Line(0, Point.ORIGIN);

        assertThat(line.hasAPositiveSlope()).isTrue();
    }

    @Test
    public void givenASlopeLowerThanZero_ShouldNotHaveAPositiveSlope() {
        Line line = new Line(-1, Point.ORIGIN);

        assertThat(line.hasAPositiveSlope()).isFalse();
    }

    @Test
    public void givenASlopeHigherThanZero_ShouldNotHaveANegativeSlope() {
        Line line = new Line(5.0, Point.ORIGIN);

        assertThat(line.hasANegativeSlope()).isFalse();
    }

    @Test
    public void givenASlopeOfZero_ShouldNotHaveANegativeSlope() {
        Line line = new Line(0, Point.ORIGIN);

        assertThat(line.hasANegativeSlope()).isFalse();
    }

    @Test
    public void givenASlopeLowerThanZero_ShouldHaveANegativeSlope() {
        Line line = new Line(-1, Point.ORIGIN);

        assertThat(line.hasANegativeSlope()).isTrue();
    }

    @Test
    public void canRetrieveTheNegativeReciprocalOfASlope() {
        Line line = new Line(1, Point.ORIGIN);

        assertThat(line.getNegativeReciprocal()).isWithin(TOLERANCE).of(-1);
    }

    @Test
    public void canRetrieveTheNegativeReciprocalOfAFractionalSlope() {
        Line line = new Line(0.5, Point.ORIGIN);

        assertThat(line.getNegativeReciprocal()).isWithin(TOLERANCE).of(-2);
    }

    @Test
    public void givenAVerticalLine_ThenTheNegativeReciprocalShouldBeZero() {
        assertThat(Line.Y_AXIS.getNegativeReciprocal()).isWithin(TOLERANCE).of(0);
    }

    @Test
    public void givenAHorizontalLine_ThenTheNegativeReciprocalShouldBeInfinite() {
        double negativeReciprocal = Line.X_AXIS.getNegativeReciprocal();

        assertThat(Double.isInfinite(negativeReciprocal)).isTrue();
    }

    @Test
    public void givenAPositiveInfiniteSlope_ShouldBeVertical() {
        Line line = new Line(Double.POSITIVE_INFINITY, AN_INTERCEPT);

        assertThat(line.isVertical()).isTrue();
    }

    @Test
    public void givenANegativeInfiniteSlope_ShouldBeVertical() {
        Line line = new Line(Double.NEGATIVE_INFINITY, AN_INTERCEPT);

        assertThat(line.isVertical()).isTrue();
    }

    @Test
    public void givenANonVerticalLine_ShouldNotBeVertical() {
        assertThat(Line.X_AXIS.isVertical()).isFalse();
    }

    @Test
    public void givenASlopeOfZero_ShouldBeHorizontal() {
        Line line = new Line(0.0, AN_INTERCEPT);

        assertThat(line.isHorizontal()).isTrue();
    }

    @Test
    public void givenASlopeOfNegativeZero_ShouldBeHorizontal() {
        Line line = new Line(-0.0, AN_INTERCEPT);

        assertThat(line.isHorizontal()).isTrue();
    }

    @Test
    public void givenANonZeroSlope_ShouldNotBeHorizontal() {
        assertThat(Line.Y_AXIS.isHorizontal()).isFalse();
    }

    @Test
    public void givenTwoLinesWithTheSameSlope_ShouldBeParallel() {
        Line slopeOfZeroLine = new Line(0.00, AN_INTERCEPT);

        boolean isParallel = slopeOfZeroLine.isParallel(Line.X_AXIS);

        assertThat(isParallel).isTrue();
    }

    @Test
    public void givenTwoLinesWithDifferentSlope_ShouldNotBeParallel() {
        Line slopeOfOneLine = new Line(1.0, AN_INTERCEPT);
        Line slopeOfThreeLine = new Line(3.0, AN_INTERCEPT);

        boolean isParallel = slopeOfOneLine.isParallel(slopeOfThreeLine);

        assertThat(isParallel).isFalse();
    }

    @Test
    public void givenTwoLinesWithin90Degrees_ShouldBePerpendicular() {
        boolean isPerpendicular = Line.X_AXIS.isPerpendicular(Line.Y_AXIS);

        assertThat(isPerpendicular).isTrue();
    }

    @Test
    public void givenTwoLinesWithAnAngleDifferentOf90Degrees_ShouldNotBePerpendicular() {
        Line slopeOfOneLine = new Line(1.0, AN_INTERCEPT);

        boolean isPerpendicular = slopeOfOneLine.isPerpendicular(Line.X_AXIS);

        assertThat(isPerpendicular).isFalse();
    }

    @Test
    public void givenAPositiveSlope_CanRetrieveTheAngleWithTheXAxis() {
        Line slopeOfOneLine = new Line(1.0, AN_INTERCEPT);

        Angle angle = slopeOfOneLine.getAngleWithXAxis();

        assertThat(angle).isEqualTo(Angle.fromDegrees(45.00));
    }

    @Test
    public void givenANegativeSlope_CanRetrieveTheAngleWithTheXAxis() {
        Line slopeOfMinusTwoLine = new Line(-1.0, AN_INTERCEPT);

        Angle angle = slopeOfMinusTwoLine.getAngleWithXAxis();

        assertThat(angle).isEqualTo(Angle.fromDegrees(-45.00));
    }

    @Test
    public void givenXAxis_ShouldHaveAnAngleOfZeroWithTheXAxis() {
        assertThat(Line.X_AXIS.getAngleWithXAxis()).isEqualTo(Angle.ZERO_DEGREES);
    }

    @Test
    public void givenYAxis_ShouldHaveAnAngleOf90DegreesWithTheXAxis() {
        assertThat(Line.Y_AXIS.getAngleWithXAxis()).isEqualTo(Angle.DEGREES_90);
    }

    @Test
    public void givenAPointOnTheLine_ShouldHaveADistanceOfZero() {
        Line line = new Line(A_SLOPE, Point.fromCartesian(5, 5));

        double distanceToPoint = line.getDistanceTo(Point.fromCartesian(5, 5));

        assertThat(distanceToPoint).isWithin(TOLERANCE).of(0);
    }

    @Test
    public void givenAPointNotOnTheLine_ShouldReturnTheCorrespondingDistance() {
        Line line = new Line(1, Point.ORIGIN);

        double distanceToPoint = line.getDistanceTo(Point.fromCartesian(0, 10));

        assertThat(distanceToPoint).isWithin(TOLERANCE).of(7.07106);
    }

    @Test
    public void canGetTheDistanceBetweenAPointAndTheXAxis() {
        double distanceToPoint = Line.X_AXIS.getDistanceTo(Point.fromCartesian(0, -16));

        assertThat(distanceToPoint).isWithin(TOLERANCE).of(16);
    }

    @Test
    public void canGetTheDistanceBetweenAPointAndTheYAxis() {
        double distanceToPoint = Line.Y_AXIS.getDistanceTo(Point.fromCartesian(24, 0));

        assertThat(distanceToPoint).isWithin(TOLERANCE).of(24);
    }

    @Test
    public void aPointOnTheLine_ShouldBePassingOnIt() {
        Line line = new Line(1, Point.ORIGIN);

        boolean isOnTheLine = line.isPassingOn(Point.ORIGIN);

        assertThat(isOnTheLine).isTrue();
    }

    @Test
    public void aPointNotOnTheLine_ShouldNotBePassingOnIt() {
        Line line = new Line(1, Point.ORIGIN);

        boolean isOnTheLine = line.isPassingOn(Point.fromCartesian(1, 2));

        assertThat(isOnTheLine).isFalse();
    }

    @Test
    public void givenAPointAboveTheLine_ShouldBeAboveTheLine() {
        Line line = new Line(1, Point.ORIGIN);

        boolean isAbove = line.isPointAbove(Point.fromCartesian(10, 100));

        assertThat(isAbove).isTrue();
    }

    @Test
    public void givenAPointOnTheLine_ShouldNotBeAboveTheLine() {
        Line line = new Line(1, Point.ORIGIN);

        boolean isAbove = line.isPointAbove(Point.ORIGIN);

        assertThat(isAbove).isFalse();
    }

    @Test
    public void givenAPointBelowTheLine_ShouldNotBeAboveTheLine() {
        Line line = new Line(1, Point.ORIGIN);

        boolean isAbove = line.isPointAbove(Point.fromCartesian(50, -9999));

        assertThat(isAbove).isFalse();
    }

    @Test
    public void givenAVerticalLine_ThePointShouldNotBeAboveTheLine() {
        boolean isAbove = Line.Y_AXIS.isPointAbove(Point.ORIGIN);

        assertThat(isAbove).isFalse();
    }

    @Test
    public void givenAPointAboveTheLine_ShouldNotBeBelowTheLine() {
        Line line = new Line(1, Point.ORIGIN);

        boolean isBelow = line.isPointBelow(Point.fromCartesian(10, 100));

        assertThat(isBelow).isFalse();
    }

    @Test
    public void givenAPointOnTheLine_ShouldNotBeBelowTheLine() {
        Line line = new Line(1, Point.ORIGIN);

        boolean isBelow = line.isPointBelow(Point.ORIGIN);

        assertThat(isBelow).isFalse();
    }

    @Test
    public void givenAPointBelowTheLine_ShouldBeBelowTheLine() {
        Line line = new Line(1, Point.ORIGIN);

        boolean isBelow = line.isPointBelow(Point.fromCartesian(50, -9999));

        assertThat(isBelow).isTrue();
    }

    @Test
    public void givenAVerticalLine_ThePointShouldNotBeBelowTheLine() {
        boolean isBelow = Line.Y_AXIS.isPointBelow(Point.ORIGIN);

        assertThat(isBelow).isFalse();
    }

    @Test
    public void givenX_CanFindAPointOnTheLine() {
        Line line = new Line(1, Point.ORIGIN);

        Point point = line.findPointGivenX(5);

        assertThat(point.getX()).isWithin(TOLERANCE).of(5);
        assertThat(point.getY()).isWithin(TOLERANCE).of(5);
    }

    @Test(expected = IllegalStateException.class)
    public void givenAVerticalLine_CannotFindAPointOnTheLine() {
        Line.Y_AXIS.findPointGivenX(43);
    }

    @Test
    public void givenY_CanFindAPointOnTheLine() {
        Line line = new Line(1, Point.ORIGIN);

        Point point = line.findPointGivenY(5);

        assertThat(point.getX()).isWithin(TOLERANCE).of(5);
        assertThat(point.getY()).isWithin(TOLERANCE).of(5);
    }

    @Test(expected = IllegalStateException.class)
    public void givenAHorizontalLine_CannotFindAPointOnTheLine() {
        Line.X_AXIS.findPointGivenY(43);
    }

    @Test
    public void canRotateALineClockwise() {
        Line rotatedLine = Line.X_AXIS.rotateClockwiseOn(Point.ORIGIN, Angle.fromDegrees(45));

        Line expectedLine = new Line(Angle.fromDegrees(-45), Point.ORIGIN);
        assertThat(rotatedLine).isEqualTo(expectedLine);
    }

    @Test
    public void canRotateALineCounterClockwise() {
        Line rotatedLine = Line.X_AXIS.rotateCounterClockwiseOn(Point.ORIGIN, Angle.fromDegrees(45));

        Line expectedLine = new Line(Angle.fromDegrees(45), Point.ORIGIN);
        assertThat(rotatedLine).isEqualTo(expectedLine);
    }

    @Test
    public void canRotateALineBackAndForth() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);
        Point pivot = Point.ORIGIN;
        Angle rotationAngle = Angle.DEGREES_270;

        Line firstRotation = line.rotateClockwiseOn(pivot, rotationAngle);
        Line secondRotation = firstRotation.rotateCounterClockwiseOn(pivot, rotationAngle);

        assertThat(secondRotation).isEqualTo(line);
    }

    @Test
    public void rotatingTheXAxisBy90DegreesClockwise_ShouldResultAsTheYAxis() {
        Line rotatedLine = Line.X_AXIS.rotateClockwiseOn(Point.ORIGIN, Angle.DEGREES_90);

        assertThat(rotatedLine).isEqualTo(Line.Y_AXIS);
    }

    @Test
    public void rotatingTheXAxisBy90DegreesCounterClockwise_ShouldResultAsTheYAxis() {
        Line rotatedLine = Line.X_AXIS.rotateCounterClockwiseOn(Point.ORIGIN, Angle.DEGREES_90);

        assertThat(rotatedLine).isEqualTo(Line.Y_AXIS);
    }

    @Test
    public void rotatingTheYAxisBy90DegreesClockwise_ShouldResultAsTheXAxis() {
        Line rotatedLine = Line.Y_AXIS.rotateClockwiseOn(Point.ORIGIN, Angle.DEGREES_90);

        assertThat(rotatedLine).isEqualTo(Line.X_AXIS);
    }

    @Test
    public void rotatingTheYAxisBy90DegreesCounterClockwise_ShouldResultAsTheXAxis() {
        Line rotatedLine = Line.Y_AXIS.rotateCounterClockwiseOn(Point.ORIGIN, Angle.DEGREES_90);

        assertThat(rotatedLine).isEqualTo(Line.X_AXIS);
    }

    @Test
    public void translatingALine_ShouldKeepTheSameSlope() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line translatedLine = line.translate(5.0);

        assertThat(translatedLine.getSlope()).isWithin(TOLERANCE).of(A_SLOPE);
    }

    @Test
    public void translatingALineWithAPositiveUnit_ShouldAscendTheIntercept() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line translatedLine = line.translate(7.0);

        assertThat(translatedLine.getIntercept()).isWithin(TOLERANCE).of(AN_INTERCEPT + 7);
    }

    @Test
    public void translatingALineWithANegativeUnit_ShouldDescendTheIntercept() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line translatedLine = line.translate(-5.0);

        assertThat(translatedLine.getIntercept()).isWithin(TOLERANCE).of(AN_INTERCEPT - 5);
    }

    @Test
    public void givenAPositiveSlope_CanFindAPointUpward() {
        Line line = new Line(1, Point.ORIGIN);

        Point pointUpward = line.findOffsettedPointUpward(1.4142, Point.ORIGIN);

        assertThat(pointUpward).isEqualTo(Point.fromCartesian(1, 1));
    }

    @Test
    public void givenANegativeSlope_CanFindAPointUpward() {
        Line line = new Line(-1, Point.ORIGIN);

        Point pointUpward = line.findOffsettedPointUpward(1.4142, Point.ORIGIN);

        assertThat(pointUpward).isEqualTo(Point.fromCartesian(-1, 1));
    }

    @Test
    public void givenAVerticalLine_CanFindAPointUpward() {
        Point pointUpward = Line.Y_AXIS.findOffsettedPointUpward(1, Point.ORIGIN);

        assertThat(pointUpward).isEqualTo(Point.fromCartesian(0, 1));
    }

    @Test
    public void givenAHorizontalLine_CanFindAPointUpward() {
        Point pointUpward = Line.X_AXIS.findOffsettedPointUpward(1, Point.ORIGIN);

        assertThat(pointUpward).isEqualTo(Point.fromCartesian(1, 0));
    }

    @Test
    public void givenAPositiveSlope_CanFindAPointDownward() {
        Line line = new Line(1, Point.ORIGIN);

        Point pointDownward = line.findOffsettedPointDownward(1.4142, Point.ORIGIN);

        assertThat(pointDownward).isEqualTo(Point.fromCartesian(-1.0, -1.0));
    }

    @Test
    public void givenANegativeSlope_CanFindAPointDownward() {
        Line line = new Line(-1, Point.ORIGIN);

        Point pointDownward = line.findOffsettedPointDownward(1.4142, Point.ORIGIN);

        assertThat(pointDownward).isEqualTo(Point.fromCartesian(1, -1));
    }

    @Test
    public void givenAVerticalLine_CanFindAPointDownward() {
        Point pointDownward = Line.Y_AXIS.findOffsettedPointDownward(1, Point.ORIGIN);

        assertThat(pointDownward).isEqualTo(Point.fromCartesian(0, -1));
    }

    @Test
    public void givenAHorizontalLine_CanFindAPointDownward() {
        Point pointDownward = Line.X_AXIS.findOffsettedPointDownward(1, Point.ORIGIN);

        assertThat(pointDownward).isEqualTo(Point.fromCartesian(-1, 0));
    }

    @Test
    public void givenAPositiveSlope_CanFindAPointRightward() {
        Line line = new Line(1, Point.ORIGIN);

        Point pointRightward = line.findOffsettedPointRightward(1.4142, Point.ORIGIN);

        assertThat(pointRightward).isEqualTo(Point.fromCartesian(1.0, 1.0));
    }

    @Test
    public void givenANegativeSlope_CanFindAPointRightward() {
        Line line = new Line(-1, Point.ORIGIN);

        Point pointRightward = line.findOffsettedPointRightward(1.4142, Point.ORIGIN);

        assertThat(pointRightward).isEqualTo(Point.fromCartesian(1, -1));
    }

    @Test
    public void givenAVerticalLine_CanFindAPointRightward() {
        Point pointRightward = Line.Y_AXIS.findOffsettedPointRightward(1, Point.ORIGIN);

        assertThat(pointRightward).isEqualTo(Point.fromCartesian(0, 1));
    }

    @Test
    public void givenAHorizontalLine_CanFindAPointRightward() {
        Point pointRightward = Line.X_AXIS.findOffsettedPointRightward(1, Point.ORIGIN);

        assertThat(pointRightward).isEqualTo(Point.fromCartesian(1, 0));
    }

    @Test
    public void givenAPositiveSlope_CanFindAPointLeftward() {
        Line line = new Line(1, Point.ORIGIN);

        Point pointLeftward = line.findOffsettedPointLeftward(1.4142, Point.ORIGIN);

        assertThat(pointLeftward).isEqualTo(Point.fromCartesian(-1.0, -1.0));
    }

    @Test
    public void givenANegativeSlope_CanFindAPointLeftward() {
        Line line = new Line(-1, Point.ORIGIN);

        Point pointLeftward = line.findOffsettedPointLeftward(1.4142, Point.ORIGIN);

        assertThat(pointLeftward).isEqualTo(Point.fromCartesian(-1, 1));
    }

    @Test
    public void givenAVerticalLine_CanFindAPointLeftward() {
        Point pointLeftward = Line.Y_AXIS.findOffsettedPointLeftward(1, Point.ORIGIN);

        assertThat(pointLeftward).isEqualTo(Point.fromCartesian(0, -1));
    }

    @Test
    public void givenAHorizontalLine_CanFindAPointLeftward() {
        Point pointLeftward = Line.X_AXIS.findOffsettedPointLeftward(1, Point.ORIGIN);

        assertThat(pointLeftward).isEqualTo(Point.fromCartesian(-1, 0));
    }

    @Test
    public void whenFindingAPerpendicularLine_ShouldBePerpendicularWithTheOriginalLine() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line perpendicularLine = line.findPerpendicularLinePassingThrough(Point.fromCartesian(2, 3));

        assertThat(line.isPerpendicular(perpendicularLine)).isTrue();
    }

    @Test
    public void whenFindingAPerpendicularLine_ShouldPassOnTheProvidedPoint() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);
        Point point = Point.fromCartesian(2, 3);

        Line newLine = line.findPerpendicularLinePassingThrough(point);

        assertThat(newLine.isPassingOn(point)).isTrue();
    }

    @Test
    public void thePerpendicularLineOfXAxisPassingThroughOrigin_ShouldResultAsTheYAxis() {
        Line line = Line.X_AXIS.findPerpendicularLinePassingThrough(Point.ORIGIN);

        assertThat(line).isEqualTo(Line.Y_AXIS);
    }

    @Test
    public void thePerpendicularLineOfYAxisPassingThroughOrigin_ShouldResultAsTheXAxis() {
        Line line = Line.Y_AXIS.findPerpendicularLinePassingThrough(Point.ORIGIN);

        assertThat(line).isEqualTo(Line.X_AXIS);
    }

    @Test
    public void whenFindingAParallelLine_ShouldBeParallelWithTheOriginalLine() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line parallelLine = line.findParallelLinePassingThrough(Point.fromCartesian(2, 3));

        assertThat(line.isParallel(parallelLine)).isTrue();
    }

    @Test
    public void whenFindingAParallelLine_ShouldPassOnTheProvidedPoint() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);
        Point point = Point.fromCartesian(2, 3);

        Line newLine = line.findParallelLinePassingThrough(point);

        assertThat(newLine.isPassingOn(point)).isTrue();
    }

    @Test
    public void whenFindingTheAboveParallelLine_ShouldBeParallel() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line parallelLine = line.findTheAboveParallelLineOf(10.0);

        assertThat(line.isParallel(parallelLine)).isTrue();
    }

    @Test
    public void whenFindingTheAboveParallelLine_ItShouldBeAboveTheOriginalLine() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line parallelLine = line.findTheAboveParallelLineOf(10.0);
        Point aPointOnTheLine = parallelLine.findPointGivenX(20);

        assertThat(line.isPointAbove(aPointOnTheLine)).isTrue();
    }

    @Test
    public void whenFindingTheBelowParallelLine_ShouldBeParallel() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line parallelLine = line.findTheBelowParallelLineOf(10.0);

        assertThat(line.isParallel(parallelLine)).isTrue();
    }

    @Test
    public void whenFindingTheBelowParallelLine_ItShouldBeBelowTheOriginalLine() {
        Line line = new Line(A_SLOPE, AN_INTERCEPT);

        Line parallelLine = line.findTheBelowParallelLineOf(10.0);
        Point aPointOnTheLine = parallelLine.findPointGivenX(20);

        assertThat(line.isPointBelow(aPointOnTheLine)).isTrue();
    }

}
