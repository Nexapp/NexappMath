package ca.nexapp.math.units;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class LengthTest {

    private static final double TOLERANCE = 0.00001;

    @Test
    public void canAddTwoLengthsTogether() {
	Length oneMeter = Length.fromMeters(1);
	Length tenMeters = Length.fromMeters(10);

	Length result = oneMeter.add(tenMeters);

	assertThat(result).isEqualTo(Length.fromMeters(11));
    }

    @Test
    public void canSubstractTwoLengthsTogether() {
	Length fiftyCentimeters = Length.fromCentimeters(50);
	Length thirtyCentimers = Length.fromCentimeters(30);

	Length result = fiftyCentimeters.substract(thirtyCentimers);

	assertThat(result).isEqualTo(Length.fromCentimeters(20));
    }

    @Test
    public void convertingMetersToMeters_ShouldReturnTheSameValue() {
	Length length = Length.fromMeters(15);

	assertThat(length.toMeters()).isWithin(TOLERANCE).of(15);
    }

    @Test
    public void canConvertMetersToKilometers() {
	Length length = Length.fromMeters(2500);

	assertThat(length.toKilometers()).isWithin(TOLERANCE).of(2.5);
    }

    @Test
    public void convertingKilometersToKilometers_ShouldReturnTheSameValue() {
	Length length = Length.fromKilometers(3);

	assertThat(length.toKilometers()).isWithin(TOLERANCE).of(3);
    }

    @Test
    public void canConvertMetersToDecimeters() {
	Length length = Length.fromMeters(10);

	assertThat(length.toDecimeters()).isWithin(TOLERANCE).of(100);
    }

    @Test
    public void convertingDecimetersToDecimeters_ShouldReturnTheSameValue() {
	Length length = Length.fromDecimeters(85);

	assertThat(length.toDecimeters()).isWithin(TOLERANCE).of(85);
    }

    @Test
    public void canConvertDecimetersToCentimeters() {
	Length length = Length.fromDecimeters(25);

	assertThat(length.toCentimeters()).isWithin(TOLERANCE).of(250);
    }

    @Test
    public void convertingCentimetersToCentimeters_ShouldReturnTheSameValue() {
	Length length = Length.fromCentimeters(45);

	assertThat(length.toCentimeters()).isWithin(TOLERANCE).of(45);
    }

    @Test
    public void canConvertCentimetersToMillimeters() {
	Length length = Length.fromCentimeters(34);

	assertThat(length.toMillimeters()).isWithin(TOLERANCE).of(340);
    }

    @Test
    public void convertingMillimetersToMillimeters_ShouldReturnTheSameValue() {
	Length length = Length.fromMillimeters(73);

	assertThat(length.toMillimeters()).isWithin(TOLERANCE).of(73);
    }

    @Test
    public void canConvertKilometersToMiles() {
	Length length = Length.fromKilometers(100);

	assertThat(length.toMiles()).isWithin(TOLERANCE).of(62.137273);
    }

    @Test
    public void convertingMilesToMiles_ShouldReturnTheSameValue() {
	Length length = Length.fromMiles(87);

	assertThat(length.toMiles()).isWithin(TOLERANCE).of(87);
    }

    @Test
    public void canConvertMetersToYards() {
	Length length = Length.fromMeters(100);

	assertThat(length.toYards()).isWithin(TOLERANCE).of(109.36132);
    }

    @Test
    public void convertingYardsToYards_ShouldReturnTheSameValue() {
	Length length = Length.fromYards(54);

	assertThat(length.toYards()).isWithin(TOLERANCE).of(54);
    }

    @Test
    public void canConvertMetersToInches() {
	Length length = Length.fromMeters(1.65);

	assertThat(length.toInches()).isWithin(TOLERANCE).of(64.96063);
    }

    @Test
    public void convertingInchesToInches_ShouldReturnTheSameValue() {
	Length length = Length.fromInches(900);

	assertThat(length.toInches()).isWithin(TOLERANCE).of(900);
    }

    @Test
    public void canConvertMetersToFoot() {
	Length length = Length.fromMeters(2.07);

	assertThat(length.toFoot()).isWithin(TOLERANCE).of(6.791339);
    }

    @Test
    public void convertingFootToFoot_ShouldReturnTheSameValue() {
	Length length = Length.fromFoot(42);

	assertThat(length.toFoot()).isWithin(TOLERANCE).of(42);
    }

    @Test
    public void aSmallerLength_ShouldBeLesser() {
	Length aSmallLength = Length.fromMiles(16);
	Length aLongLength = Length.fromMiles(875);

	assertThat(aSmallLength).isLessThan(aLongLength);
    }

    @Test
    public void theSameLength_ShouldBeComparativelyEquals() {
	Length length = Length.fromCentimeters(43);

	assertThat(length).isEquivalentAccordingToCompareTo(length);
    }

    @Test
    public void aLongerLength_ShouldBeGreater() {
	Length aSmallLength = Length.fromMiles(16);
	Length aLongLength = Length.fromMiles(875);

	assertThat(aLongLength).isGreaterThan(aSmallLength);
    }

}
