package ca.nexapp.math.units;

import java.util.Objects;

public class Mass implements Comparable<Mass> {

    private static final double GRAMS_IN_AN_OUNCE = 28.3495;
    private static final double GRAMS_IN_A_POUND = 453.592;
    private static final double GRAMS_IN_A_KILOGRAM = 1000;
    private static final double GRAMS_IN_A_TONNE = GRAMS_IN_A_KILOGRAM * 1000;

    private double grams;

    private Mass(double grams) {
        this.grams = grams;
    }

    public Mass add(Mass augend) {
        return new Mass(grams + augend.grams);
    }

    public Mass subtract(Mass substrahend) {
        return new Mass(grams - substrahend.grams);
    }

    public double toOunces() {
        return grams / GRAMS_IN_AN_OUNCE;
    }

    public double toPounds() {
        return grams / GRAMS_IN_A_POUND;
    }

    public double toGrams() {
        return grams;
    }

    public double toKilograms() {
        return grams / GRAMS_IN_A_KILOGRAM;
    }

    public double toTonnes() {
        return grams / GRAMS_IN_A_TONNE;
    }

    public static Mass ounces(double ounces) {
        return new Mass(ounces * GRAMS_IN_AN_OUNCE);
    }

    public static Mass pounds(double pounds) {
        return new Mass(pounds * GRAMS_IN_A_POUND);
    }

    public static Mass grams(double grams) {
        return new Mass(grams);
    }

    public static Mass kilograms(double kilograms) {
        return new Mass(kilograms * GRAMS_IN_A_KILOGRAM);
    }

    public static Mass tonnes(double tonnes) {
        return new Mass(tonnes * GRAMS_IN_A_TONNE);
    }

    @Override
    public int compareTo(Mass other) {
        return Double.compare(grams, other.grams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grams);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Mass)) {
            return false;
        }

        Mass other = (Mass) obj;
        return Objects.equals(grams, other.grams);
    }

    @Override
    public String toString() {
        return grams + "g";
    }

}
