package ca.nexapp.math.units;

import java.util.Objects;

public class Angle implements Comparable<Angle> {

    public static final Angle ZERO_DEGREES = Angle.fromDegrees(0.00);
    public static final Angle DEGREES_90 = Angle.fromDegrees(90.00);
    public static final Angle DEGREES_180 = Angle.fromDegrees(180.00);
    public static final Angle DEGREES_270 = Angle.fromDegrees(270.00);
    public static final Angle DEGREES_360 = Angle.fromDegrees(360.00);

    private final double angleInDegrees;

    private Angle(double angleInDegrees) {
	this.angleInDegrees = angleInDegrees;
    }

    public Angle add(Angle augend) {
        return new Angle(angleInDegrees + augend.angleInDegrees);
    }

    public Angle subtract(Angle subtrahend) {
        return new Angle(angleInDegrees - subtrahend.angleInDegrees);
    }

    public double toDegrees() {
	return angleInDegrees;
    }

    public double toRadians() {
	return Math.toRadians(angleInDegrees);
    }

    public Angle invert() {
	return new Angle(-angleInDegrees);
    }

    public Angle normalize() {
	double c = angleInDegrees % 360;
	double normalizedAngleInDegrees = c >= 0 ? c : 360.0 + c;
	return new Angle(normalizedAngleInDegrees);
    }

    public boolean isInFirstQuadrant() {
	double normalizedAngleInDegrees = normalize().toDegrees();
	return 0 < normalizedAngleInDegrees && normalizedAngleInDegrees < 90;
    }

    public boolean isInFirstQuadrantOrAxes() {
	double normalizedAngleInDegrees = normalize().toDegrees();
	return 0 <= normalizedAngleInDegrees && normalizedAngleInDegrees <= 90;
    }

    public boolean isInSecondQuadrant() {
	double normalizedAngleInDegrees = normalize().toDegrees();
	return 90 < normalizedAngleInDegrees && normalizedAngleInDegrees < 180;
    }

    public boolean isInSecondQuadrantOrAxes() {
	double normalizedAngleInDegrees = normalize().toDegrees();
	return 90 <= normalizedAngleInDegrees && normalizedAngleInDegrees <= 180;
    }

    public boolean isInThirdQuadrant() {
	double normalizedAngleInDegrees = normalize().toDegrees();
	return 180 < normalizedAngleInDegrees && normalizedAngleInDegrees < 270;
    }

    public boolean isInThirdQuadrantOrAxes() {
	double normalizedAngleInDegrees = normalize().toDegrees();
	return 180 <= normalizedAngleInDegrees && normalizedAngleInDegrees <= 270;
    }

    public boolean isInFourthQuadrant() {
	double normalizedAngleInDegrees = normalize().toDegrees();
	return 270 < normalizedAngleInDegrees && normalizedAngleInDegrees < 360;
    }

    public boolean isInFourthQuadrantOrAxes() {
	double normalizedAngleInDegrees = normalize().toDegrees();
	return 270 <= normalizedAngleInDegrees && normalizedAngleInDegrees <= 360 || normalizedAngleInDegrees == 0.0;
    }

    public static Angle fromRadians(double angleInRadians) {
	return new Angle(Math.toDegrees(angleInRadians));
    }

    public static Angle fromDegrees(double angleInDegrees) {
	return new Angle(angleInDegrees);
    }

    @Override
    public int compareTo(Angle other) {
	return Double.compare(angleInDegrees, other.angleInDegrees);
    }

    @Override
    public int hashCode() {
	return Objects.hash(angleInDegrees);
    }

    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof Angle)) {
	    return false;
	}

	Angle other = (Angle) obj;
	return Objects.equals(angleInDegrees, other.angleInDegrees);
    }

    @Override
    public String toString() {
	return "[Degrees: " + toDegrees() + ", Radians: " + toRadians() + "]";
    }
}
