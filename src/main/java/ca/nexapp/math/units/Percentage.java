package ca.nexapp.math.units;

import java.util.Arrays;
import java.util.Objects;

public class Percentage implements Comparable<Percentage> {

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

    public Percentage average(Percentage... percentages) {
        double sum = Arrays.stream(percentages).mapToDouble(Percentage::toRatio).sum();
        double sumPlusSelf = sum + toRatio();
        double actualCount = percentages.length + 1;
        return fromFraction(sumPlusSelf, ONE_HUNDRED * actualCount);
    }

    public Percentage difference(Percentage other) {
        double difference = Math.abs(toRatio() - other.toRatio());
        return fromRatio(difference);
    }

    public static Percentage fromRatio(double ratio) {
        return new Percentage(ratio / ONE_HUNDRED);
    }

    public static Percentage fromFraction(double fraction) {
        return new Percentage(fraction);
    }

    public static Percentage fromFraction(double numerator, double denominator) {
        return fromFraction(numerator / denominator);
    }

    @Override
    public int compareTo(Percentage other) {
        return Double.compare(percentageAsFraction, other.percentageAsFraction);
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
