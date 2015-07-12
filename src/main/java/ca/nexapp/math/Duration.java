package ca.nexapp.math;

import java.util.Objects;

public class Duration {

    private static final double WEEKS_IN_A_MONTH = 4.34812;

    private static final double NANOS_IN_A_MICROSECOND = 1000.0;
    private static final double NANOS_IN_A_MILLISECOND = 1000.0 * NANOS_IN_A_MICROSECOND;

    private static final double NANOS_IN_A_SECOND = 1000.0 * NANOS_IN_A_MILLISECOND;
    private static final double NANOS_IN_A_MINUTE = 60.0 * NANOS_IN_A_SECOND;
    private static final double NANOS_IN_AN_HOUR = 60.0 * NANOS_IN_A_MINUTE;

    private static final double NANOS_IN_A_DAY = 24.0 * NANOS_IN_AN_HOUR;
    private static final double NANOS_IN_A_WEEK = 7.0 * NANOS_IN_A_DAY;
    private static final double NANOS_IN_A_MONTH = WEEKS_IN_A_MONTH * NANOS_IN_A_WEEK;
    private static final double NANOS_IN_A_YEAR = 12.0 * NANOS_IN_A_MONTH;

    private static final double NANOS_IN_A_DECADE = 10.0 * NANOS_IN_A_YEAR;
    private static final double NANOS_IN_A_CENTURY = 10.0 * NANOS_IN_A_DECADE;

    private final double nanoseconds;

    private Duration(double nanoseconds) {
        this.nanoseconds = nanoseconds;
    }

    public Duration add(Duration augend) {
        return new Duration(nanoseconds + augend.nanoseconds);
    }

    public Duration substract(Duration subtrahend) {
        return new Duration(nanoseconds - subtrahend.nanoseconds);
    }

    public long toNanoseconds() {
        return (long) nanoseconds;
    }

    public long toMicroseconds() {
        return (long) (nanoseconds / NANOS_IN_A_MICROSECOND);
    }

    public long toMilliseconds() {
        return (long) (nanoseconds / NANOS_IN_A_MILLISECOND);
    }

    public long toSeconds() {
        return (long) (nanoseconds / NANOS_IN_A_SECOND);
    }

    public long toMinutes() {
        return (long) (nanoseconds / NANOS_IN_A_MINUTE);
    }

    public long toHours() {
        return (long) (nanoseconds / NANOS_IN_AN_HOUR);
    }

    public long toDays() {
        return (long) (nanoseconds / NANOS_IN_A_DAY);
    }

    public long toWeeks() {
        return (long) (nanoseconds / NANOS_IN_A_WEEK);
    }

    public long toMonths() {
        return (long) (nanoseconds / NANOS_IN_A_MONTH);
    }

    public long toYears() {
        return (long) (nanoseconds / NANOS_IN_A_YEAR);
    }

    public long toDecades() {
        return (long) (nanoseconds / NANOS_IN_A_DECADE);
    }

    public long toCenturies() {
        return (long) (nanoseconds / NANOS_IN_A_CENTURY);
    }

    public static Duration nanoseconds(long numberOfNanoseconds) {
        return new Duration(numberOfNanoseconds);
    }

    public static Duration microseconds(long numberOfMicroseconds) {
        return new Duration(numberOfMicroseconds * NANOS_IN_A_MICROSECOND);
    }

    public static Duration milliseconds(long numberOfMilliseconds) {
        return new Duration(numberOfMilliseconds * NANOS_IN_A_MILLISECOND);
    }

    public static Duration seconds(long numberOfSeconds) {
        return new Duration(numberOfSeconds * NANOS_IN_A_SECOND);
    }

    public static Duration minutes(long numberOfMinutes) {
        return new Duration(numberOfMinutes * NANOS_IN_A_MINUTE);
    }

    public static Duration hours(long numberOfHours) {
        return new Duration(numberOfHours * NANOS_IN_AN_HOUR);
    }

    public static Duration days(long numberOfDays) {
        return new Duration(numberOfDays * NANOS_IN_A_DAY);
    }

    public static Duration weeks(long numberOfWeeks) {
        return new Duration(numberOfWeeks * NANOS_IN_A_WEEK);
    }

    public static Duration months(long numberOfMonths) {
        return new Duration(numberOfMonths * NANOS_IN_A_MONTH);
    }

    public static Duration years(long numberOfYears) {
        return new Duration(numberOfYears * NANOS_IN_A_YEAR);
    }

    public static Duration decades(long numberOfDecades) {
        return new Duration(numberOfDecades * NANOS_IN_A_DECADE);
    }

    public static Duration centuries(long numberOfCenturies) {
        return new Duration(numberOfCenturies * NANOS_IN_A_CENTURY);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nanoseconds);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Duration)) {
            return false;
        }

        Duration other = (Duration) obj;
        return Objects.equals(nanoseconds, other.nanoseconds);
    }
}
