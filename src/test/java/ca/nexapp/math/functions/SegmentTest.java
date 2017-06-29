package ca.nexapp.math.functions;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

import ca.nexapp.math.units.Point;

public class SegmentTest {

    private static final double DELTA = 0.0001;

    @Test
    public void canCalculateTheLength() {
        Segment segment = new Segment(Point.ORIGIN, Point.fromCartesian(10, 10));

        double distance = segment.getLength();

        assertThat(distance).isWithin(DELTA).of(14.1421);
    }

    @Test
    public void canFindTheCenterPoint() {
        Segment segment = new Segment(Point.ORIGIN, Point.fromCartesian(10, 10));

        Point centerPoint = segment.getCenterPoint();

        assertThat(centerPoint).isEqualTo(Point.fromCartesian(5, 5));
    }

    @Test
    public void givenAPointOnTheSegment_ShouldContainsThePoint() {
        Segment segment = new Segment(Point.ORIGIN, Point.fromCartesian(10, 10));

        Point onTheLine = Point.fromCartesian(5, 5);
        assertThat(segment.isPassingOn(onTheLine)).isTrue();
    }

    @Test
    public void givenAPointNotOnTheSegment_ShouldNotContainsThePoint() {
        Segment segment = new Segment(Point.ORIGIN, Point.fromCartesian(10, 10));

        Point onTheLine = Point.fromCartesian(5, 6);
        assertThat(segment.isPassingOn(onTheLine)).isFalse();
    }

    @Test
    public void givenAPointOnTheSameSlopeOfSegmentButOutsideTheRange_ShouldNotContainsThePoint() {
        Segment segment = new Segment(Point.ORIGIN, Point.fromCartesian(10, 10));

        Point sameSlopeButNotInRange = Point.fromCartesian(20, 20);
        assertThat(segment.isPassingOn(sameSlopeButNotInRange)).isFalse();
    }
}
