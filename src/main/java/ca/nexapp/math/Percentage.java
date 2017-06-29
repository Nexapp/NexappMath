package ca.nexapp.math;

import java.util.Objects;

public class Percentage {

    private static final double ZERO = 0.000000;
    private static final double ONE_HUNDRED = 100.000000;

    public static final Percentage ZERO_PERCENT = Percentage.fromRatio(ZERO);
    public static final Percentage ONE_HUNDRED_PERCENT = Percentage.fromRatio(ONE_HUNDRED);

    private final double percentageAsFraction;

    private Percentage(double percentageAsFraction) {
        this.percentageAsFraction = percentageAsFraction;
    }

    public double toRatio() {
        return percentageAsFraction * ONE_HUNDRED;
    }

    public double toFraction() {
        return percentageAsFraction;
    }

    public static Percentage fromRatio(double ratio) {
        return new Percentage(ratio / ONE_HUNDRED);
    }

    public static Percentage fromFraction(double fraction) {
        return new Percentage(fraction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(percentageAsFraction);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Percentage)) {
            return false;
        }

        Percentage other = (Percentage) obj;
        return Objects.equals(percentageAsFraction, other.percentageAsFraction);
    }

    @Override
    public String toString() {
        return toFraction() + " (" + toRatio() + "%)";
    }
}