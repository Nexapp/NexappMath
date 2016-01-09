package ca.nexapp.math.units;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class TemperatureTest {

    private static final double TOLERANCE = 0.001;

    @Test
    public void convertingCelsiusToCelsius_ShouldReturnTheSameTemperature() {
        Temperature temperature = Temperature.fromCelsius(35);

        assertThat(temperature.toCelsius()).isWithin(TOLERANCE).of(35);
    }

    @Test
    public void canConvertCelsiusToFahrenheit() {
        Temperature temperature = Temperature.fromCelsius(30);

        assertThat(temperature.toFahrenheit()).isWithin(TOLERANCE).of(86);
    }

    @Test
    public void canConvertCelsiusToKelvin() {
        Temperature temperature = Temperature.fromCelsius(100);

        assertThat(temperature.toKelvin()).isWithin(TOLERANCE).of(373.15);
    }

    @Test
    public void convertingFahrenheitToFahrenheit_ShouldReturnTheSameTemperature() {
        Temperature temperature = Temperature.fromFahrenheit(80);

        assertThat(temperature.toFahrenheit()).isWithin(TOLERANCE).of(80);
    }

    @Test
    public void canConvertFahrenheitToCelsius() {
        Temperature temperature = Temperature.fromFahrenheit(77);

        assertThat(temperature.toCelsius()).isWithin(TOLERANCE).of(25);
    }

    @Test
    public void canConvertFahrenheitToKelvin() {
        Temperature temperature = Temperature.fromFahrenheit(81);

        assertThat(temperature.toKelvin()).isWithin(TOLERANCE).of(300.372);
    }

    @Test
    public void convertingKelvinToKelvin_ShouldReturnTheSameTemperature() {
        Temperature temperature = Temperature.fromKelvin(400);

        assertThat(temperature.toKelvin()).isWithin(TOLERANCE).of(400);
    }

    @Test
    public void canConvertKelvinToCelsius() {
        Temperature temperature = Temperature.fromKelvin(292);

        assertThat(temperature.toCelsius()).isWithin(TOLERANCE).of(18.85);
    }

    @Test
    public void canConvertKelvinToFahrenheit() {
        Temperature temperature = Temperature.fromKelvin(350);

        assertThat(temperature.toFahrenheit()).isWithin(TOLERANCE).of(170.33);
    }

    @Test
    public void aLowerTemperature_ShouldBeLower() {
        Temperature lowTemperature = Temperature.fromCelsius(-25);
        Temperature highTemperature = Temperature.fromCelsius(40);

        assertThat(lowTemperature).isLessThan(highTemperature);
    }

    @Test
    public void theSameTemperature_ShouldBeEqualsComparatively() {
        Temperature temperature = Temperature.fromCelsius(10);

        assertThat(temperature).isEquivalentAccordingToCompareTo(temperature);
    }

    @Test
    public void aHigherTemperature_ShouldBeGreater() {
        Temperature lowTemperature = Temperature.fromCelsius(-25);
        Temperature highTemperature = Temperature.fromCelsius(40);

        assertThat(highTemperature).isGreaterThan(lowTemperature);
    }
}
