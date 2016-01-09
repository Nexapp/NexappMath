package ca.nexapp.math.units;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

public class MassTest {

    private static final double TOLERANCE = 0.0001;

    @Test
    public void canAddTwoMassesTogether() {
        Mass oneGram = Mass.grams(1);
        Mass tenGrams = Mass.grams(10);

        Mass result = oneGram.add(tenGrams);

        assertThat(result).isEqualTo(Mass.grams(11));
    }

    @Test
    public void canSubtractTwoMassesTogether() {
        Mass twoGrams = Mass.grams(2);
        Mass tenGrams = Mass.grams(10);

        Mass result = tenGrams.subtract(twoGrams);

        assertThat(result).isEqualTo(Mass.grams(8));
    }

    @Test
    public void convertingTonnesToTonnes_ShouldReturnTheSameValue() {
        Mass mass = Mass.tonnes(5);

        assertThat(mass.toTonnes()).isWithin(TOLERANCE).of(5);
    }

    @Test
    public void canConvertTonnesToKilograms() {
        Mass mass = Mass.tonnes(2);

        assertThat(mass.toKilograms()).isWithin(TOLERANCE).of(2000);
    }

    @Test
    public void convertingKilogramsToKilograms_ShouldReturnTheSameValue() {
        Mass mass = Mass.kilograms(1.8);

        assertThat(mass.toKilograms()).isWithin(TOLERANCE).of(1.8);
    }

    @Test
    public void canConvertKilogramsToGrams() {
        Mass mass = Mass.kilograms(5);

        assertThat(mass.toGrams()).isWithin(TOLERANCE).of(5000);
    }

    @Test
    public void convertingGramsToGrams_ShouldReturnTheSameValue() {
        Mass mass = Mass.grams(50);

        assertThat(mass.toGrams()).isWithin(TOLERANCE).of(50);
    }

    @Test
    public void canConvertGramsToPounds() {
        Mass mass = Mass.grams(100);

        assertThat(mass.toPounds()).isWithin(TOLERANCE).of(0.2204);
    }

    @Test
    public void convertingPoundsToPounds_ShouldReturnTheSameValue() {
        Mass mass = Mass.pounds(45);

        assertThat(mass.toPounds()).isWithin(TOLERANCE).of(45);
    }

    @Test
    public void canConvertPoundsToGrams() {
        Mass mass = Mass.pounds(1);

        assertThat(mass.toGrams()).isWithin(TOLERANCE).of(453.592);
    }

    @Test
    public void convertingOuncesToOunces_ShouldReturnTheSameValue() {
        Mass mass = Mass.ounces(34);

        assertThat(mass.toOunces()).isWithin(TOLERANCE).of(34);
    }

    @Test
    public void aSmallMass_ShouldBeLesser() {
        Mass smallMass = Mass.grams(20);
        Mass highMass = Mass.grams(900);

        assertThat(smallMass).isLessThan(highMass);
    }

    @Test
    public void theSameMass_ShouldBeEqualsComparatively() {
        Percentage percentage = Percentage.fromRatio(43);

        assertThat(percentage).isEquivalentAccordingToCompareTo(percentage);
    }

    @Test
    public void aHighMass_ShouldBeGreater() {
        Mass smallMass = Mass.grams(20);
        Mass highMass = Mass.grams(900);

        assertThat(highMass).isGreaterThan(smallMass);
    }

}
