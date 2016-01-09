package ca.nexapp.math.units;

import java.util.Objects;

public class Length implements Comparable<Length> {

    private static final double METERS_IN_A_MILLIMETER = 0.001;
    private static final double METERS_IN_A_CENTIMETER = 0.01;
    private static final double METERS_IN_A_DECIMETER = 0.10;
    private static final double METERS_IN_A_KILOMETER = 1000;
    private static final double METERS_IN_A_MILE = 1609.34;
    private static final double METERS_IN_A_YARD = 0.9144;
    private static final double METERS_IN_A_FOOT = 0.3048;
    private static final double METERS_IN_AN_INCH = 0.0254;

    private final double meters;

    private Length(double meters) {
	this.meters = meters;
    }

    public Length add(Length augend) {
	return new Length(meters + augend.meters);
    }

    public Length subtract(Length subtrahend) {
	return new Length(meters - subtrahend.meters);
    }

    public double toMillimeters() {
	return meters / METERS_IN_A_MILLIMETER;
    }

    public double toCentimeters() {
	return meters / METERS_IN_A_CENTIMETER;
    }

    public double toDecimeters() {
	return meters / METERS_IN_A_DECIMETER;
    }

    public double toMeters() {
	return meters;
    }

    public double toKilometers() {
	return meters / METERS_IN_A_KILOMETER;
    }

    public double toMiles() {
	return meters / METERS_IN_A_MILE;
    }

    public double toYards() {
	return meters / METERS_IN_A_YARD;
    }

    public double toInches() {
	return meters / METERS_IN_AN_INCH;
    }

    public double toFoot() {
	return meters / METERS_IN_A_FOOT;
    }

    public static Length fromMillimeters(double millimeters) {
	return new Length(millimeters * METERS_IN_A_MILLIMETER);
    }

    public static Length fromCentimeters(double centimeters) {
	return new Length(centimeters * METERS_IN_A_CENTIMETER);
    }

    public static Length fromDecimeters(double decimeters) {
	return new Length(decimeters * METERS_IN_A_DECIMETER);
    }

    public static Length fromMeters(double meters) {
	return new Length(meters);
    }

    public static Length fromKilometers(double kilometers) {
	return new Length(kilometers * METERS_IN_A_KILOMETER);
    }

    public static Length fromMiles(double miles) {
	return new Length(miles * METERS_IN_A_MILE);
    }

    public static Length fromYards(double yards) {
	return new Length(yards * METERS_IN_A_YARD);
    }

    public static Length fromInches(double inches) {
	return new Length(inches * METERS_IN_AN_INCH);
    }

    public static Length fromFoot(double foot) {
	return new Length(foot * METERS_IN_A_FOOT);
    }

    @Override
    public int compareTo(Length other) {
	return Double.compare(meters, other.meters);
    }

    @Override
    public int hashCode() {
	return Objects.hash(meters);
    }

    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof Length)) {
	    return false;
	}

	Length other = (Length) obj;
	return Objects.equals(meters, other.meters);
    }

    @Override
    public String toString() {
	return meters + "m";
    }

}
