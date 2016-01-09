package ca.nexapp.math.units;

import java.util.Objects;

public class Temperature implements Comparable<Temperature> {

    private final double celsius;

    private Temperature(double celsius) {
	this.celsius = celsius;
    }

    public double toCelsius() {
	return celsius;
    }

    public double toFahrenheit() {
	return celsius * 9.0 / 5.0 + 32.0;
    }

    public double toKelvin() {
	return celsius + 273.15;
    }

    public static Temperature fromCelsius(double celsius) {
	return new Temperature(celsius);
    }

    public static Temperature fromFahrenheit(double fahrenheit) {
	double celsius = (fahrenheit - 32.0) * (5.0 / 9.0);
	return fromCelsius(celsius);
    }

    public static Temperature fromKelvin(double kelvin) {
	double celsius = kelvin - 273.15;
	return fromCelsius(celsius);
    }

    @Override
    public int compareTo(Temperature other) {
	return Double.compare(celsius, other.celsius);
    }

    @Override
    public int hashCode() {
	return Objects.hash(celsius);
    }

    @Override
    public boolean equals(Object obj) {
	if (!(obj instanceof Temperature)) {
	    return false;
	}

	Temperature other = (Temperature) obj;
	return Objects.equals(celsius, other.celsius);
    }

    @Override
    public String toString() {
	return celsius + "°";
    }

}
